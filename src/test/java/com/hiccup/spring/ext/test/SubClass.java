package com.hiccup.spring.ext.test;

import org.springframework.context.annotation.Replace;
import org.springframework.stereotype.Service;

@Service("testBean")
@Replace
public class SubClass extends BaseClass {

    private String name = "subClass";

    public String getName(){
        return name;
    }
}
