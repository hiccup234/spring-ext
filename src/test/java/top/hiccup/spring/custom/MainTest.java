package top.hiccup.spring.custom;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import top.hiccup.spring.custom.domain.User;

/**
 * 自定义标签解析
 *
 * @author wenhy
 * @date 2020/11/8
 */
public class MainTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("custom/bean.xml");
        User c = (User) applicationContext.getBean("user");
        System.out.println(c);
    }
}
