/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context.annotation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.parsing.ProblemReporter;
import org.springframework.beans.factory.parsing.SourceExtractor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import top.hiccup.util.ReflectionUtils;

import static org.springframework.context.annotation.AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR;

/**
 *
 * 扩展ConfigurationClassPostProcessor
 *
 * <p>This post processor is {@link Ordered#HIGHEST_PRECEDENCE} as it is important
 * that any {@link Bean} methods declared in Configuration classes have their
 * respective bean definitions registered before any other BeanFactoryPostProcessor
 * executes.
 *
 * 这应该是在不修改Spring源码的前提下，ApplicationContext可扩展的最早时机
 *
 * @author wenhy
 * @date 2018/8/8
 */
public class ExtConfigurationClassPostProcessor extends ConfigurationClassPostProcessor {

    private static final Log logger = LogFactory.getLog(ExtConfigurationClassPostProcessor.class);
    /**
     * 反射获取父类的相关属性
     * 这里有没有更好的实现方式呢？（感觉Spring的这个类扩展起来很麻烦）
     */
    private static final String IMPORT_REGISTRY_BEAN_NAME
            = (String)ReflectionUtils.getFieldValue(ConfigurationClassPostProcessor.class, null, "IMPORT_REGISTRY_BEAN_NAME");

    private MetadataReaderFactory metadataReaderFactory = (MetadataReaderFactory) ReflectionUtils.getFieldValue(ConfigurationClassPostProcessor.class, this, "metadataReaderFactory");

    private boolean localBeanNameGeneratorSet = (Boolean) ReflectionUtils.getFieldValue(ConfigurationClassPostProcessor.class, this, "localBeanNameGeneratorSet");

    private BeanNameGenerator componentScanBeanNameGenerator = (BeanNameGenerator) ReflectionUtils.getFieldValue(ConfigurationClassPostProcessor.class, this, "componentScanBeanNameGenerator");

    private BeanNameGenerator importBeanNameGenerator = (BeanNameGenerator) ReflectionUtils.getFieldValue(ConfigurationClassPostProcessor.class, this, "importBeanNameGenerator");

    private ProblemReporter problemReporter = (ProblemReporter) ReflectionUtils.getFieldValue(ConfigurationClassPostProcessor.class, this, "problemReporter");

    private Environment environment = (Environment) ReflectionUtils.getFieldValue(ConfigurationClassPostProcessor.class, this, "environment");

    private ResourceLoader resourceLoader = (ResourceLoader) ReflectionUtils.getFieldValue(ConfigurationClassPostProcessor.class, this, "resourceLoader");

    private ConfigurationClassBeanDefinitionReader reader = (ConfigurationClassBeanDefinitionReader) ReflectionUtils.getFieldValue(ConfigurationClassPostProcessor.class, this, "reader");

    private SourceExtractor sourceExtractor = (SourceExtractor) ReflectionUtils.getFieldValue(ConfigurationClassPostProcessor.class, this, "sourceExtractor");

    /**
     * 重写ConfigurationClassPostProcessor.processConfigBeanDefinitions方法，将parser更换成扩展后的类
     * @param registry
     */
    @Override
    public void processConfigBeanDefinitions(BeanDefinitionRegistry registry) {
        Set<BeanDefinitionHolder> configCandidates = new LinkedHashSet<BeanDefinitionHolder>();
        String[] candidateNames = registry.getBeanDefinitionNames();

        for (String beanName : candidateNames) {
            BeanDefinition beanDef = registry.getBeanDefinition(beanName);
            if (beanDef.getAttribute(ConfigurationClassUtils.CONFIGURATION_CLASS_ATTRIBUTE) != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Bean definition has already been processed as a configuration class: " + beanDef);
                }
            }
            else if (ConfigurationClassUtils.checkConfigurationClassCandidate(beanDef, this.metadataReaderFactory)) {
                configCandidates.add(new BeanDefinitionHolder(beanDef, beanName));
            }
        }

        // Return immediately if no @Configuration classes were found
        if (configCandidates.isEmpty()) {
            return;
        }

        // Detect any custom bean name generation strategy supplied through the enclosing application context
        SingletonBeanRegistry singletonRegistry = null;
        if (registry instanceof SingletonBeanRegistry) {
            singletonRegistry = (SingletonBeanRegistry) registry;
            if (!this.localBeanNameGeneratorSet && singletonRegistry.containsSingleton(CONFIGURATION_BEAN_NAME_GENERATOR)) {
                BeanNameGenerator generator = (BeanNameGenerator) singletonRegistry.getSingleton(CONFIGURATION_BEAN_NAME_GENERATOR);
                this.componentScanBeanNameGenerator = generator;
                this.importBeanNameGenerator = generator;
            }
        }

        if (environment == null) {
            environment = (Environment) ReflectionUtils.getFieldValue(ConfigurationClassPostProcessor.class, this, "environment");
        }

        // Parse each @Configuration class
        // 这里换成扩展的解析器
        ExtConfigurationClassParser parser = new ExtConfigurationClassParser(
                this.metadataReaderFactory, this.problemReporter, this.environment,
                this.resourceLoader, this.componentScanBeanNameGenerator, registry);

        Set<ConfigurationClass> alreadyParsed = new HashSet<ConfigurationClass>(configCandidates.size());
        do {
            parser.parse(configCandidates);
            parser.validate();

            Set<ConfigurationClass> configClasses = new LinkedHashSet<ConfigurationClass>(parser.getConfigurationClasses());
            configClasses.removeAll(alreadyParsed);

            // Read the model and create bean definitions based on its content

            if (this.reader == null) {
                // 4.1.4.RELEASE
//                this.reader = new ConfigurationClassBeanDefinitionReader(registry, this.sourceExtractor,
//                        this.problemReporter, this.metadataReaderFactory,
//                        this.resourceLoader, this.environment,
//                        this.importBeanNameGenerator, parser.getImportRegistry());
                // 4.3.10.RELEASE
                this.reader = new ConfigurationClassBeanDefinitionReader(registry, this.sourceExtractor,
                        this.resourceLoader, this.environment,
                        this.importBeanNameGenerator, parser.getImportRegistry());
            }
            this.reader.loadBeanDefinitions(configClasses);
            alreadyParsed.addAll(configClasses);

            configCandidates.clear();
            if (registry.getBeanDefinitionCount() > candidateNames.length) {
                String[] newCandidateNames = registry.getBeanDefinitionNames();
                Set<String> oldCandidateNames = new HashSet<String>(Arrays.asList(candidateNames));
                Set<String> alreadyParsedClasses = new HashSet<String>();
                for (ConfigurationClass configurationClass : alreadyParsed) {
                    alreadyParsedClasses.add(configurationClass.getMetadata().getClassName());
                }
                for (String candidateName : newCandidateNames) {
                    if (!oldCandidateNames.contains(candidateName)) {
                        BeanDefinition beanDef = registry.getBeanDefinition(candidateName);
                        if (ConfigurationClassUtils.checkConfigurationClassCandidate(beanDef, this.metadataReaderFactory) &&
                                !alreadyParsedClasses.contains(beanDef.getBeanClassName())) {
                            configCandidates.add(new BeanDefinitionHolder(beanDef, candidateName));
                        }
                    }
                }
                candidateNames = newCandidateNames;
            }
        }
        while (!configCandidates.isEmpty());

        // Register the ImportRegistry as a bean in order to support ImportAware @Configuration classes
        if (singletonRegistry != null) {
            if (!singletonRegistry.containsSingleton(IMPORT_REGISTRY_BEAN_NAME)) {
                singletonRegistry.registerSingleton(IMPORT_REGISTRY_BEAN_NAME, parser.getImportRegistry());
            }
        }

        if (this.metadataReaderFactory instanceof CachingMetadataReaderFactory) {
            ((CachingMetadataReaderFactory) this.metadataReaderFactory).clearCache();
        }
    }

}