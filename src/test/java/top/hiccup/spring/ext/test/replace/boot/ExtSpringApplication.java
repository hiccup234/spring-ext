package top.hiccup.spring.ext.test.replace.boot;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ExtConfigurationClassPostProcessor;
import org.springframework.core.io.ResourceLoader;

/**
 * SpringBoot启动类扩展
 *
 * @author wenhy
 * @date 2020/3/5
 */
public class ExtSpringApplication extends SpringApplication {

    public ExtSpringApplication(Class<?>... primarySources) {
        super((ResourceLoader) null, primarySources);
    }

    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        return run(new Class[]{primarySource}, args);
    }

    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        return (new ExtSpringApplication(primarySources)).run(args);
    }

    @Override
    protected void postProcessApplicationContext(ConfigurableApplicationContext context) {
        super.postProcessApplicationContext(context);
        if (context instanceof BeanDefinitionRegistry) {
            BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) context;
            beanDefinitionRegistry.registerBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME,
                    new RootBeanDefinition(ExtConfigurationClassPostProcessor.class));
            setAllowBeanDefinitionOverriding(true);
        } else {
            throw new RuntimeException("Can`t use spring-ext.jar");
        }
    }
}
