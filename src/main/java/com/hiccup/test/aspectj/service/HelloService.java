package com.hiccup.test.aspectj.service;

import org.springframework.stereotype.Component;

/**
 * F
 *
 * @author wenhy
 * @date 2018/9/2
 */
@Component("helloService")
public class HelloService {

    public String sayHello(String name) {
        System.out.println("In method sayHello");
        return "Hello " + name;
    }

    public String sayGoodbye(String name) {
        return "Bye " + name;
    }
}
