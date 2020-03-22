package top.hiccup.spring.ext.test.replace.dynamic.bean;

import org.springframework.stereotype.Component;

@Component
public class DefaultConfig {

    /**
     * 字段属性不具备多态特性
     */
    public String name = "defaultConfig";

    public String getName() {
        return name;
    }
}
