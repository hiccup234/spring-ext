package top.hiccup.spring.ext.test.replace.bean;

import org.springframework.stereotype.Service;

@Service("testBean")
public class BaseClass {

    public String getName() {
        return "BaseClass";
    }
}
