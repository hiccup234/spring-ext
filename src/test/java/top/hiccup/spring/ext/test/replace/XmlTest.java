package top.hiccup.spring.ext.test.replace;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import top.hiccup.spring.ext.test.replace.bean.BaseClass;
import top.hiccup.spring.ext.test.replace.bean.SubClass;

/**
 * @Replace 注解测试类
 *
 * <p>
 * 一般情况下，企业系统集成的开发框架和公用组件都非常多，这些组件通常都会由Spring容器来管理，且每个组件都会使用到自己的配置等，
 * 如果应用程序需要修改jar包中一些配置或者是逻辑的话就比较麻烦（通常完整的依赖链都是由Spring管理的，如果应用想要修改一部分
 * 逻辑就只有修改jar包并重新打jar，这样不利于基础组件的版本管理和维护）
 * 而Spring容器本身一般情况下只提供了getBean方法，却并没有直接提供一个set的API，
 * 如果应用需要替换容器中指定的Bean一般有一下几种方案：
 * <p>
 * 1.通过BeanPostProcessor来动态替换
 * （这样的实现非常不优雅，使组件跟Spring生命周期接口产生强耦合）
 * public class MyConfigImpl extends DefaultConfigImpl implements BeanPostProcessor {
 * public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
 * if (beanName.equals("defaultcfg")) {
 * return otherBean;
 * }
 * return bean;
 * }
 * }
 * <p>
 * 2.通过@Replace注解，扩展Spring原生的context名称空间，在加载Bean时直接替换
 * <p>
 * 3.通过获取器注入（lookup-method）返回子类bean，麻烦的是要去管理这种替换关系
 *
 * @author wenhy
 * @date 2018/7/29
 */
public class XmlTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/ext/root-context.xml");

        Thread t1 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println(((BaseClass) applicationContext.getBean("testBean")).getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println(((BaseClass) applicationContext.getBean("testBean")).getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2");

        Thread t3 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println(((BaseClass) applicationContext.getBean("testBean")).getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t3");

        t1.start();
        t2.start();
        t3.start();


        BaseClass bean = (BaseClass) applicationContext.getBean("testBean");
        System.out.println(bean.getName());
        // 属性字段不具备多态，所以通过BaseClass引用访问的属性任然是父类的属性
        System.out.println(bean.name);
        System.out.println(((SubClass) bean).name);
        System.out.println(((BaseClass) bean).name);
    }
}
