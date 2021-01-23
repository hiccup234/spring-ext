package top.hiccup.spring.ext.test.replace.boot.bean;

import org.springframework.context.annotation.Replace;

@Replace("defaultConfig")
public class MyConfig extends DefaultConfig {

    public String name = "myConfig";

    @Override
    public String getName() {
        return name;
    }
}
