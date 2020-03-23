package top.hiccup.spring.ext.test.replace.bean;

import org.springframework.context.annotation.Replace;

@Replace("testBean")
public class SubClass extends BaseClass {

    public String name = "sub";

    @Override
    public String getName(){
        return "SubClass";
    }
}
