package org.springframework.context.annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;

public class ExtClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner
{
    protected static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
    protected final Log logger = LogFactory.getLog(getClass());

    protected ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    protected MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);

    protected String resourcePattern = "**/*.class";

    protected BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
    protected ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();

    public ExtClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry)
    {
        super(registry);
    }
    public ExtClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    private Replace getReplaceAnnotation(String beanClassName) throws ClassNotFoundException {
//        Class clazz = ClassUtils.getClass(beanClassName);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if(null == classLoader) {
            classLoader = this.getClass().getClassLoader();
        }
        Class clazz = Class.forName(beanClassName, true, classLoader);
        Replace replace = (Replace)AnnotationUtils.findAnnotation(clazz, Replace.class);
        return replace;
    }

    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition)
            throws IllegalStateException
    {
        if (!getRegistry().containsBeanDefinition(beanName)) {
            return true;
        }
        BeanDefinition existingDef = getRegistry().getBeanDefinition(beanName);
        BeanDefinition originatingDef = existingDef.getOriginatingBeanDefinition();
        if (originatingDef != null) {
            existingDef = originatingDef;
        }
        if (isCompatible(beanDefinition, existingDef)) {
            return false;
        }

        try
        {
            Replace replace = getReplaceAnnotation(beanDefinition.getBeanClassName());
            if (replace != null) {
                if (this.logger.isInfoEnabled()) {
                    this.logger.info("替换组件名称[" + beanName + "],新对象[" + beanDefinition
                            .getBeanClassName() + "],旧对象[" + existingDef.getBeanClassName() + "].");
                }
                return true;
            }

            replace = getReplaceAnnotation(existingDef.getBeanClassName());
            if (replace != null) {
                if (this.logger.isInfoEnabled()) {
                    this.logger.info("本地化组件名称[" + beanName + "]已加载[" + existingDef
                            .getBeanClassName() + "],忽略加载对象[" + beanDefinition.getBeanClassName() + "].");
                }
                return false;
            }

        }
        catch (ClassNotFoundException localClassNotFoundException)
        {
        }

        throw new IllegalStateException("Annotation-specified bean name '" + beanName + "' for bean class [" + beanDefinition
                .getBeanClassName() + "] conflicts with existing, " + "non-compatible bean definition of same name and class [" + existingDef
                .getBeanClassName() + "]");
    }
}