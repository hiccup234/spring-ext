package top.hiccup.spring.ext.test.replace.boot.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author wenhy
 * @Value 由AutowiredAnnotationBeanPostProcessor实现，所以如果在
 * @date 2021/1/30
 */
@Component
public class PropertyTest implements BeanPostProcessor, InitializingBean {

    @Value("${my.name}")
    private String name;

    static {
        System.out.println("fff");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof PropertyTest) {
            System.out.println("这里会执行吗？");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof PropertyTest) {
            System.out.println("这里会执行吗2？");
        }
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean：" + name);
    }
}
