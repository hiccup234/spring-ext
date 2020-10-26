package top.hiccup.spring.test.circular;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 循环依赖测试
 *
 * @author wenhy
 * @date 2018/7/29
 */
public class MainTest {

    @Test
    public void root() {
        // 构造器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/test/circular/root.xml");
        StudentA a = (StudentA) applicationContext.getBean("a");
        System.out.println(a);
    }

    @Test
    public void root2() {
        // 单例setter方式
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/test/circular/root2.xml");
        StudentA a = (StudentA) applicationContext.getBean("a");
        System.out.println(a);
    }

    @Test
    public void root3() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/test/circular/root3.xml");
        StudentA a = (StudentA) applicationContext.getBean("a");
        System.out.println(a);
    }
}
