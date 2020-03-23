package top.hiccup.spring.ext.test.replace.dynamic;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import top.hiccup.spring.ext.test.replace.dynamic.bean.DefaultConfig;
import top.hiccup.spring.ext.test.replace.dynamic.bean.MyConfig;
import top.hiccup.spring.ext.test.replace.dynamic.bean.UseBean;

/**
 * 动态注册Bean
 *
 * @author wenhy
 * @date 2020/3/22
 */
@SpringBootApplication(scanBasePackages = "top.hiccup.spring.ext.test.replace.dynamic")
public class DynamicRegisteringBean {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DynamicRegisteringBean.class);
        springApplication.setAllowBeanDefinitionOverriding(true);
        ConfigurableApplicationContext ctx = springApplication.run(args);
        System.out.println(((DefaultConfig) ctx.getBean("defaultConfig")).getName());
        ctx.getBean(UseBean.class).printName();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(MyConfig.class);
        beanDefinitionBuilder.addPropertyValue("name", "this is a DynamicConfig");
        BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) ctx;
        // 动态注入同名Bean，会直接覆盖之前的Bean，并且容器中其他Bean对当前Bean的引用也会被更新（这是个好消息）
        beanDefinitionRegistry.registerBeanDefinition("defaultConfig", beanDefinitionBuilder.getBeanDefinition());
        System.out.println(((DefaultConfig) ctx.getBean("defaultConfig")).getName());
        ctx.getBean(UseBean.class).printName();
    }
}
