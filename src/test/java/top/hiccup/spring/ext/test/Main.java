package top.hiccup.spring.ext.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/root-context.xml");
        BaseClass bean = (BaseClass) applicationContext.getBean("testBean");
        System.out.println(bean.name);

    }
}
