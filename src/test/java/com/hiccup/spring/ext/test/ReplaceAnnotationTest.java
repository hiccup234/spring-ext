package com.hiccup.spring.ext.test;

import com.hiccup.spring.ext.bean.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Replace 注解测试主类
 *
 * =========================================================================
 * 目前企业系统集成的开发框架和公用组件都非常多，有的是公共开源的框架，有的是公司内部封装的包
 * 一般都会由Spring容器来管理，且每个组件都会使用到自己的配置等，如果应用程序需要修改jar包中
 * 一些配置或者是逻辑的话就比较麻烦（通常完整的依赖链都是由Spring管理的，如果应用想要修改一部分
 * 逻辑就只有修改jar包并重新打jar，这样不利于基础组件的版本管理和维护）
 * 而Spring容器本身一般情况下只提供了getBean方法，却并没有直接提供一个set的API
 * 如果应用需要替换容器中指定的Bean一般有一下几种方案：
 *
 * 1.通过BeanPostProcessor来动态替换
 * public class MyConfigImpl extends DefaultConfigImpl implements BeanPostProcessor {
 *
 *     @Override
 *     public Object postProcessAfterInitialization(Object bean, String beanName)
 *     throws BeansException {
 *         return bean;
 *     }
 *
 *     @Override
 *     public Object postProcessBeforeInitialization(Object bean, String beanName)
 *     throws BeansException {
 *         if (beanName.equals("defaultcfg")) {
 *             // do something
 *             return this;
 *         }
 *         return bean;
 *     }
 * }
 *
 * 2.通过@Replace注解，扩展Spring原生的context名称空间，在加载Bean时直接替换
 *
 * 3.通过@Replace注解结合Configuation实现组件替换功能
 *
 *
 * =========================================================================
 *
 * @author wenhy
 * @date 2018/7/29
 */
public class ReplaceAnnotationTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/replace-annotation-test.xml");
        User user = (User)context.getBean("testUser");
        System.out.println(user);

    }
}