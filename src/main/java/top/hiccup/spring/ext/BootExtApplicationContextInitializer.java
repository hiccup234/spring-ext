package top.hiccup.spring.ext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ExtConfigurationClassPostProcessor;

/**
 * 通过Spring的“SPI”机制替换ConfigurationClassPostProcessor
 *
 * @author wenhy
 * @date 2021/1/23
 */
public class BootExtApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Log logger = LogFactory.getLog(BootExtApplicationContextInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        if (context instanceof BeanDefinitionRegistry) {
            BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) context;
            beanDefinitionRegistry.registerBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, new RootBeanDefinition(ExtConfigurationClassPostProcessor.class));
        } else {
            logger.error("Can`t use spring-ext.jar");
        }
    }
}
