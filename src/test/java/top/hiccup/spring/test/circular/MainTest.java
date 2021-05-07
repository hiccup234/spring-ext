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
        // 构造器（Spring无法解决构造器循环依赖的问题）
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/test/circular/root.xml");
        StudentA a = (StudentA) applicationContext.getBean("a");
        System.out.println(a);
    }

    @Test
    public void root2() {
        // 单例setter方式，Spring通过提前暴露刚完成构造器注入但未完成其他步骤（如setter注入）的bena来完成的
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/test/circular/root2.xml");
        StudentA a = (StudentA) applicationContext.getBean("a");
        System.out.println(a);
    }

    @Test
    public void root3() {
        // scope为prototype的bean也无法解决循环依赖的问题（JVM是怎么处理循环依赖的呢？）
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/test/circular/root3.xml");
        StudentA a = (StudentA) applicationContext.getBean("a");
        System.out.println(a);
    }
}
