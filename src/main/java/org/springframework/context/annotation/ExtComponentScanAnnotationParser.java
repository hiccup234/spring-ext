/*
 * Copyright 2002-2014 the original author or authors.
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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.*;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Parser for the @{@link ComponentScan} annotation.
 *
 * @author Chris Beams
 * @author Juergen Hoeller
 * @since 3.1
 * @see ClassPathBeanDefinitionScanner#scan(String...)
 * @see ComponentScanBeanDefinitionParser
 */
class ExtComponentScanAnnotationParser {

    private final ResourceLoader resourceLoader;

    private final Environment environment;

    private final BeanDefinitionRegistry registry;

    private final BeanNameGenerator beanNameGenerator;


    public ExtComponentScanAnnotationParser(ResourceLoader resourceLoader, Environment environment,
                                            BeanNameGenerator beanNameGenerator, BeanDefinitionRegistry registry) {

        this.resourceLoader = resourceLoader;
        this.environment = environment;
        this.beanNameGenerator = beanNameGenerator;
        this.registry = registry;
    }


    public Set<BeanDefinitionHolder> parse(AnnotationAttributes componentScan, final String declaringClass) {

        ExtClassPathBeanDefinitionScanner scanner =
                new ExtClassPathBeanDefinitionScanner(this.registry, componentScan.getBoolean("useDefaultFilters"));

        Assert.notNull(this.environment, "Environment must not be null");
        scanner.setEnvironment(this.environment);

        Assert.notNull(this.resourceLoader, "ResourceLoader must not be null");
        scanner.setResourceLoader(this.resourceLoader);

        Class<? extends BeanNameGenerator> generatorClass = componentScan.getClass("nameGenerator");
        boolean useInheritedGenerator = BeanNameGenerator.class.equals(generatorClass);
        scanner.setBeanNameGenerator(useInheritedGenerator ? this.beanNameGenerator :
                BeanUtils.instantiateClass(generatorClass));

        ScopedProxyMode scopedProxyMode = componentScan.getEnum("scopedProxy");
        if (scopedProxyMode != ScopedProxyMode.DEFAULT) {
            scanner.setScopedProxyMode(scopedProxyMode);
        }
        else {
            Class<? extends ScopeMetadataResolver> resolverClass = componentScan.getClass("scopeResolver");
            scanner.setScopeMetadataResolver(BeanUtils.instantiateClass(resolverClass));
        }

        scanner.setResourcePattern(componentScan.getString("resourcePattern"));

        for (AnnotationAttributes filter : componentScan.getAnnotationArray("includeFilters")) {
            for (TypeFilter typeFilter : typeFiltersFor(filter)) {
                scanner.addIncludeFilter(typeFilter);
            }
        }
        for (AnnotationAttributes filter : componentScan.getAnnotationArray("excludeFilters")) {
            for (TypeFilter typeFilter : typeFiltersFor(filter)) {
                scanner.addExcludeFilter(typeFilter);
            }
        }

        boolean lazyInit = componentScan.getBoolean("lazyInit");
        if (lazyInit) {
            scanner.getBeanDefinitionDefaults().setLazyInit(true);
        }

        List<String> basePackages = new ArrayList<String>();
        //Set<String> basePackages = new LinkedHashSet<String>();
        Set<String> specifiedPackages = new LinkedHashSet<String>();
        specifiedPackages.addAll(Arrays.asList(componentScan.getStringArray("value")));
        specifiedPackages.addAll(Arrays.asList(componentScan.getStringArray("basePackages")));

        for (String pkg : specifiedPackages) {
            String[] tokenized = StringUtils.tokenizeToStringArray(this.environment.resolvePlaceholders(pkg),
                    ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            basePackages.addAll(Arrays.asList(tokenized));
        }
        for (Class<?> clazz : componentScan.getClassArray("basePackageClasses")) {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }
        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(declaringClass));
        }

        scanner.addExcludeFilter(new AbstractTypeHierarchyTraversingFilter(false, false) {
            @Override
            protected boolean matchClassName(String className) {
                return declaringClass.equals(className);
            }
        });

        //sort
        Collections.sort(basePackages,new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if(o2.indexOf("local")!=-1){
                    return -1;
                }else if(o1.indexOf("local")!=-1){
                    return 1;
                }
                return 0;
            }
        });

        return scanner.doScan(StringUtils.toStringArray(basePackages));
    }

    private List<TypeFilter> typeFiltersFor(AnnotationAttributes filterAttributes) {
        List<TypeFilter> typeFilters = new ArrayList<TypeFilter>();
        FilterType filterType = filterAttributes.getEnum("type");

        for (Class<?> filterClass : filterAttributes.getClassArray("value")) {
            switch (filterType) {
                case ANNOTATION:
                    Assert.isAssignable(Annotation.class, filterClass,
                            "An error occured while processing a @ComponentScan ANNOTATION type filter: ");
                    @SuppressWarnings("unchecked")
                    Class<Annotation> annotationType = (Class<Annotation>) filterClass;
                    typeFilters.add(new AnnotationTypeFilter(annotationType));
                    break;
                case ASSIGNABLE_TYPE:
                    typeFilters.add(new AssignableTypeFilter(filterClass));
                    break;
                case CUSTOM:
                    Assert.isAssignable(TypeFilter.class, filterClass,
                            "An error occured while processing a @ComponentScan CUSTOM type filter: ");
                    typeFilters.add(BeanUtils.instantiateClass(filterClass, TypeFilter.class));
                    break;
                default:
                    throw new IllegalArgumentException("Filter type not supported with Class value: " + filterType);
            }
        }

        for (String expression : filterAttributes.getStringArray("pattern")) {
            switch (filterType) {
                case ASPECTJ:
                    typeFilters.add(new AspectJTypeFilter(expression, this.resourceLoader.getClassLoader()));
                    break;
                case REGEX:
                    typeFilters.add(new RegexPatternTypeFilter(Pattern.compile(expression)));
                    break;
                default:
                    throw new IllegalArgumentException("Filter type not supported with String pattern: " + filterType);
            }
        }

        return typeFilters;
    }
}