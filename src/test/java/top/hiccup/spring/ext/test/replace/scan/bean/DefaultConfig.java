package top.hiccup.spring.ext.test.replace.scan.bean;

import org.springframework.stereotype.Component;

@Component("defaultConfig")
public class DefaultConfig {

    /**
     * 字段属性不具备多态特性
     */
    public String name = "defaultConfig";

    public String getName() {
        return name;
    }
}
