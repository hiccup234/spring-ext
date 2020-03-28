package top.hiccup.spring.ext.test.replace.scan.bean;

import org.springframework.beans.factory.annotation.Autowired;
import top.hiccup.spring.ext.test.replace.scan.RRR;

@RRR("defaultConfig")
public class MyConfig extends DefaultConfig {

    @Autowired
    private OtherConfig otherConfig;

    public String name = "myConfig";

    public void setName(String s) {
        name = s;
    }

    @Override
    public String getName() {
        return "MyConfig = " + name + " OtherConfig = " + otherConfig;
    }
}
