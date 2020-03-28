package top.hiccup.spring.ext.test.replace.scan.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 使用Bean
 *
 * @author wenhy
 * @date 2020/3/22
 */
@Component
public class UseBean {

    private DefaultConfig config;

    @Autowired
    public void setConfig(DefaultConfig config) {
        System.out.println("IOC 注入Bean组件：" + config);
        this.config = config;
    }

    public void printName() {
        System.out.println("我在使用DefaultConfig：" + config.getName());
    }
}
