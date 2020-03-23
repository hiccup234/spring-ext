package top.hiccup.spring.ext.test.replace.boot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import top.hiccup.spring.ext.test.replace.boot.bean.DefaultConfig;

/**
 * 集成Spring Boot
 *
 * @author wenhy
 * @date 2020/3/20
 */
@SpringBootApplication(scanBasePackageClasses = BootTest.class)
public class BootTest {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = ExtSpringApplication.run(BootTest.class, args);
        DefaultConfig defaultConfig = ctx.getBean("defaultConfig", DefaultConfig.class);
        System.out.println(defaultConfig);
    }
}
