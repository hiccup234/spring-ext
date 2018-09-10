package top.hiccup.spring.ext.test.aspectj;

import top.hiccup.spring.ext.test.aspectj.service.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * AspectJ测试主类
 *
 * @author wenhy
 * @date 2018/9/2
 */
public class MainTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/aspectj-test.xml");
        HelloService helloService = (HelloService)context.getBean("helloService");
        System.out.println(helloService.sayHello("小海海"));

    }
}
