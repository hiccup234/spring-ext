package top.hiccup.spring.ext.test.replace.beanPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * MyBeanPostProcessor
 *
 * 优点：
 * * 利用Spring原生的扩展，可以平滑升级
 * * 实现简单易操作好理解，对于仅仅需要替换一两个Bean的情况下推荐这种方式
 * 缺点：
 * * beanName硬编码在代码里，虽然可以把替换关系配置在properties里，但是在多版本部署，替换Bean较多时，将是一种负担
 * * 仅仅是替换了Bean对象，对于容器中元数据如BeanDefinition等等均是原对象的，存在一定局限性
 *
 * @author wenhy
 * @date 2020/3/11
 */
@Component
//@Order(0)
public class MyBeanPostProcessor implements ApplicationContextAware, BeanPostProcessor {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("defaultConfig")) {
            // 如果遇到需要替换的Bean，我们直接换成自己实现的bean
            // 这里的myConfig要继承自defaultConfig，否则引用的地方会报错
            return applicationContext.getBean("myConfig");
        }
        return bean;
    }
}
