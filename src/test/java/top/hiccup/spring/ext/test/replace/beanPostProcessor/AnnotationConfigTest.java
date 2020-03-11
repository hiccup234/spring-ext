package top.hiccup.spring.ext.test.replace.beanPostProcessor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import top.hiccup.spring.ext.test.replace.beanPostProcessor.bean.DefaultConfig;

/**
 * @Replace 注解测试主类
 *
 * @author wenhy
 * @date 2018/7/29
 */
@Configuration
@ComponentScan(basePackages = "top.hiccup.spring.ext.test.replace.beanPostProcessor")
public class AnnotationConfigTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext gac = new AnnotationConfigApplicationContext(AnnotationConfigTest.class);
        DefaultConfig defaultConfig = (DefaultConfig) gac.getBean("defaultConfig");
        System.out.println(defaultConfig.getName());
    }
}