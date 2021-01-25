//package top.hiccup.spring.ext;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.RootBeanDefinition;
//import org.springframework.context.annotation.AnnotationConfigUtils;
//import org.springframework.context.annotation.ExtConfigurationClassPostProcessor;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
///**
// * F
// *
// * @author wenhy
// * @date 2020/3/15
// */
//@Order(0)
//@Component
//public class ReplaceBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        if (beanFactory instanceof BeanDefinitionRegistry) {
//            BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
//            beanDefinitionRegistry.registerBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME,
//                    new RootBeanDefinition(ExtConfigurationClassPostProcessor.class));
//        } else {
//            throw new RuntimeException("Can`t use spring-ext.jar");
//        }
//    }
//}
