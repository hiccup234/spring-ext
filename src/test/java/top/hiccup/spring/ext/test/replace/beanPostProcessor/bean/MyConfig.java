package top.hiccup.spring.ext.test.replace.beanPostProcessor.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MyConfig
 *
 * @author wenhy
 * @date 2020/3/11
 */
@Service("myConfig")
public class MyConfig extends DefaultConfig {

    @Autowired
    private Test test;

    @Override
    public String getName() {
        System.out.println(super.getName());
        return "MyConfig";
    }
}
