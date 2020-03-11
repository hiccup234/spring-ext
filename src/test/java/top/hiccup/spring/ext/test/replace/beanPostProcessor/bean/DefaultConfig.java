package top.hiccup.spring.ext.test.replace.beanPostProcessor.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * DefaultConfig
 *
 * @author wenhy
 * @date 2020/3/11
 */
@Service("defaultConfig")
public class DefaultConfig {

    @Autowired
    private Test test;

    public String getName() {
        return "DefaultConfig" + test;
    }
}
