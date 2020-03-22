package top.hiccup.spring.ext.test.replace.boot.bean;

import org.springframework.stereotype.Service;

@Service("defaultConfig")
public class DefaultConfig {

    /**
     * 字段属性不具备多态特性
     */
    public String name = "defaultConfig";

    public String getName() {
        return "defaultConfig";
    }
}
