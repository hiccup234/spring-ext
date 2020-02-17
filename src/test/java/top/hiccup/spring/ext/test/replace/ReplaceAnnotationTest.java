package top.hiccup.spring.ext.test.replace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ExtConfigurationClassPostProcessor;
import org.springframework.web.context.support.GenericWebApplicationContext;
import top.hiccup.spring.ext.bean.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Replace 注解测试主类
 *
 * @author wenhy
 * @date 2018/7/29
 */
public class ReplaceAnnotationTest {

    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("spring/replace-annotation-test.xml");
//        User user = (User)context.getBean("testUser");
//        System.out.println(user);


        GenericWebApplicationContext gwac = new GenericWebApplicationContext();
        BeanDefinitionRegistry beanDefinitionRegistry = gwac;
        beanDefinitionRegistry.registerBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME,
                new RootBeanDefinition(ExtConfigurationClassPostProcessor.class));
        gwac.refresh();


    }
}