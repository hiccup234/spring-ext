package top.hiccup.spring.ext.test.replace.bean;

import org.springframework.context.annotation.Replace;
import org.springframework.stereotype.Service;

@Service("testBean")
@Replace
public class SubClass extends BaseClass {

    @Override
    public String getName(){
        return "SubClass";
    }
}
