package top.hiccup.spring.ext.test.replace;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ExtConfigurationClassPostProcessor;
import org.springframework.web.context.support.GenericWebApplicationContext;
import top.hiccup.spring.ext.test.replace.bean.BaseClass;
import top.hiccup.spring.ext.test.replace.bean.SubClass;

/**
 * @Replace 注解测试主类
 *
 * @author wenhy
 * @date 2018/7/29
 */
public class AnnotationConfigTest {

    public static void main(String[] args) {
        GenericWebApplicationContext gwac = new GenericWebApplicationContext();
        BeanDefinitionRegistry beanDefinitionRegistry = gwac;
        beanDefinitionRegistry.registerBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME,
                new RootBeanDefinition(ExtConfigurationClassPostProcessor.class));

        gwac.refresh();

        BaseClass bean = (BaseClass) gwac.getBean("testBean");
        System.out.println(bean.getName());
        // 属性字段不具备多态，所以通过BaseClass访问的属性任然是父类的属性
        System.out.println(bean.name);
        System.out.println(((SubClass)bean).name);
        System.out.println(((BaseClass)bean).name);
    }
}