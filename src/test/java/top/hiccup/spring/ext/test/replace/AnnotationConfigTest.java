package top.hiccup.spring.ext.test.replace;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.*;
import top.hiccup.spring.ext.test.replace.bean.BaseClass;
import top.hiccup.spring.ext.test.replace.bean.SubClass;

/**
 * @author wenhy
 * @Replace 注解测试主类
 * @date 2018/7/29
 */
@Configuration
@ComponentScan(basePackages = " top.hiccup.spring.ext")
public class AnnotationConfigTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext gac = new AnnotationConfigApplicationContext();
        BeanDefinitionRegistry beanDefinitionRegistry = gac;
        beanDefinitionRegistry.registerBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME,
                new RootBeanDefinition(ExtConfigurationClassPostProcessor.class));
        gac.register(AnnotationConfigTest.class);
        gac.refresh();
        BaseClass bean = (BaseClass) gac.getBean(BaseClass.class);
        System.out.println(bean.getName());
        // 属性字段不具备多态，所以通过BaseClass访问的属性任然是父类的属性
        System.out.println(bean.name);
        System.out.println(((SubClass) bean).name);
        System.out.println(((BaseClass) bean).name);
    }
}