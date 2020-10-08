package top.hiccup.spring.test;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * 测试
 *
 * @author wenhy
 * @date 2018/7/29
 */
public class MainTest {

    public static void main(String[] args) {
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(new ClassPathResource("spring/test/root.xml"));
        System.out.println(xmlBeanFactory.getBean("user"));

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/test/root.xml");
        User user = (User) applicationContext.getBean("user");
        System.out.println(user);
    }
}
