package top.hiccup.spring.ext.test.replace.bean;

import org.springframework.stereotype.Service;

@Service("testBean")
public class BaseClass {

    /**
     * 字段属性不具备多态特性
     */
    public String name = "base";

    public String getName() {
        return "BaseClass";
    }
}
