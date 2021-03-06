package top.hiccup.spring.ext.test.replace.boot.dy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import top.hiccup.spring.ext.test.replace.boot.bean.DefaultConfig;

import java.net.URL;
import java.util.Enumeration;

/**
 * 集成Spring Boot
 *
 * @author wenhy
 * @date 2020/3/20
 */
@Import(DynamicReg.class)
@SpringBootApplication(scanBasePackageClasses = BootTest.class)
public class BootTest {

    public static void main(String[] args) {
//        ConfigurableApplicationContext ctx = ExtSpringApplication.run(BootTest.class, args);

        ConfigurableApplicationContext ctx = SpringApplication.run(BootTest.class, args);

        DefaultConfig defaultConfig = ctx.getBean("defaultConfig", DefaultConfig.class);
        System.out.println(defaultConfig);


        // getResources测试
        BootTest bootTest = new BootTest();
        try {
            // 这里会加载classpath下所有jar包中的资源
            Enumeration<URL> urls = bootTest.getClass().getClassLoader().getResources("META-INF/spring.factories");
            System.out.println("urls:" + urls);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                System.out.println("urlItem:" + url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
