package top.hiccup.spring.ext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.ExtConfigurationClassPostProcessor;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.AbstractRefreshableApplicationContext;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 替换ConfigurationClassPostProcessor
 *
 * @author wenhy
 * @date 2021/1/23
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//public class MyApplicationContextInitializer implements ApplicationContextInitializer<AbstractApplicationContext> {

    private static final Log logger = LogFactory.getLog(MyApplicationContextInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext context) {
//        if (context instanceof AbstractApplicationContext) {
//            try {
//                Constructor<?> constructor = ExtConfigurationClassPostProcessor.class.getDeclaredConstructor(new Class[0]);
//                List<BeanFactoryPostProcessor> beanFactoryPostProcessors = ((AbstractApplicationContext) context).getBeanFactoryPostProcessors();
//                List<BeanFactoryPostProcessor> ccppList = beanFactoryPostProcessors.stream().filter((b) -> b instanceof ConfigurationClassPostProcessor).collect(Collectors.toList());
//                beanFactoryPostProcessors.removeAll(ccppList);
//                ExtConfigurationClassPostProcessor instance = (ExtConfigurationClassPostProcessor) BeanUtils.instantiateClass(constructor, new Object[0]);
//                beanFactoryPostProcessors.add(instance);
//            } catch (NoSuchMethodException e) {
//                logger.error("ExtConfigurationClassPostProcessor NoSuchMethodException", e);
//            }
//        } else {
//            logger.warn("ConfigurationClassPostProcessor");
//        }

        if (context instanceof BeanDefinitionRegistry) {
            BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) context;
            beanDefinitionRegistry.registerBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME,
                    new RootBeanDefinition(ExtConfigurationClassPostProcessor.class));
            if (context.getBeanFactory() instanceof DefaultListableBeanFactory) {
                ((DefaultListableBeanFactory) context.getBeanFactory()).setAllowBeanDefinitionOverriding(true);
            }
        } else {
            throw new RuntimeException("Can`t use spring-ext.jar");
        }

    }
}
