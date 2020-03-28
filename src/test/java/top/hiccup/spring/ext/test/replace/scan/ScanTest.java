package top.hiccup.spring.ext.test.replace.scan;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import top.hiccup.spring.ext.test.replace.scan.bean.DefaultConfig;

import java.util.Map;
import java.util.Set;

/**
 * 通过ClassPathScanningCandidateComponentProvider的方式实现替换IOC组件
 *
 * @author wenhy
 * @date 2020/3/20
 */
@SpringBootApplication(scanBasePackages = "top.hiccup.spring.ext.test.replace.scan")
public class ScanTest {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ScanTest.class);
        springApplication.setAllowBeanDefinitionOverriding(true);
        ConfigurableApplicationContext ctx = springApplication.run(args);
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        // 扫描带有自定义注解的类
        provider.addIncludeFilter(new AnnotationTypeFilter(RRR.class));
        Set<BeanDefinition> scanList = provider.findCandidateComponents("top.hiccup.spring.ext.test.replace.scan.bean");
        BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) ctx;
        scanList.stream().forEach((s) -> {
            if (s instanceof AnnotatedBeanDefinition) {
                Map<String, Object> annotationAttributes = ((AnnotatedBeanDefinition) s).getMetadata().getAnnotationAttributes(RRR.class.getName());
                beanDefinitionRegistry.registerBeanDefinition(String.valueOf(annotationAttributes.get("value")), s);
            } else {
                System.out.println("替换失败");
            }
        });

        DefaultConfig defaultConfig = ctx.getBean("defaultConfig", DefaultConfig.class);
        System.out.println(defaultConfig);
    }
}
