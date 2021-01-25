package top.hiccup.spring.ext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;

/**
 * spring boot 启动的run方法加载SPI的执行顺序：
 *
 * @author wenhy
 * @date 2021/1/25
 */
public class BootExtEventPublishingRunListener implements SpringApplicationRunListener {

    private final SpringApplication application;

    private final String[] args;

    public BootExtEventPublishingRunListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void starting() {
        application.setAllowBeanDefinitionOverriding(true);
    }

//    @Override
//    public void environmentPrepared(ConfigurableEnvironment environment) {
//        Map<String, Object> source = new HashMap(4);
//        source.put("spring.main.allow-bean-definition-overriding", Boolean.FALSE);
//        PropertySource propertySource = new MapPropertySource("replace-properties", source);
//        environment.getPropertySources().addLast(propertySource);
//    }
}
