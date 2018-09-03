package com.hiccup.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 主测试类
 *
 * @author wenhy
 * @date 2018/7/29
 */
public class MainTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/root-context.xml");
        User user = (User)context.getBean("testUser");
        System.out.println(user);

    }
}
