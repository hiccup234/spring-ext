package top.hiccup.spring.ext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ExtConfigurationClassPostProcessor;

/**
 * 通过Spring的“SPI”机制
 * 1、替换ConfigurationClassPostProcessor
 *
 * @author wenhy
 * @date 2021/1/23
 */
public class BootExtApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//public class MyApplicationContextInitializer implements ApplicationContextInitializer<AbstractApplicationContext> {

    private static final Log logger = LogFactory.getLog(BootExtApplicationContextInitializer.class);

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
            // 这里其实不起作用，SpringApplication.prepareContext里先调用applyInitializers，然后又setAllowBeanDefinitionOverriding
            if (context.getBeanFactory() instanceof DefaultListableBeanFactory) {
                ((DefaultListableBeanFactory) context.getBeanFactory()).setAllowBeanDefinitionOverriding(true);
            }

        } else {
            logger.error("Can`t use spring-ext.jar");
        }

    }
}
