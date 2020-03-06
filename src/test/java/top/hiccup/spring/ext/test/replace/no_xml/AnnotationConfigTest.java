package top.hiccup.spring.ext.test.replace.no_xml;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ExtConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
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
//        AnnotationConfigApplicationContext gac = new AnnotationConfigApplicationContext();
//        BeanDefinitionRegistry beanDefinitionRegistry = gac;
//        beanDefinitionRegistry.registerBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME,
//                new RootBeanDefinition(ExtConfigurationClassPostProcessor.class));
//        gac.register(AppConfig.class);
//        gac.refresh();

        AnnotationConfigApplicationContext gac = new AnnotationConfigApplicationContext(AppConfig.class);

        BaseClass bean = (BaseClass) gac.getBean(BaseClass.class);
        System.out.println(bean.getName());
        // 属性字段不具备多态，所以通过BaseClass访问的属性任然是父类的属性
        System.out.println(bean.name);
        System.out.println(((SubClass)bean).name);
        System.out.println(((BaseClass)bean).name);
    }
}