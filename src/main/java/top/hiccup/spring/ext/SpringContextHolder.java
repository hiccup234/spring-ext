package top.hiccup.spring.ext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 以静态变量保存Spring上下文对象实例
 *
 * @author wenhy
 * @date 2018/1/26
 */
@Component(value="springContextHolder")
public final class SpringContextHolder implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringContextHolder.class);

    private static ApplicationContext applicationContext = null;

    public SpringContextHolder() { }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 在类的内部可以直接访问当前类的private属性
        SpringContextHolder.applicationContext = applicationContext;
        LOGGER.warn("ApplicationContext injected.");
    }

    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    private static void checkApplicationContext() {
        if(null == applicationContext) {
            LOGGER.error("ApplicationContext has not injected.");
            throw new IllegalStateException("ApplicationContext has not injected.");
        }
    }

    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

}
