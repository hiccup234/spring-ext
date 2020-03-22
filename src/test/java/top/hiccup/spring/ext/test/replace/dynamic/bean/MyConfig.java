package top.hiccup.spring.ext.test.replace.dynamic.bean;

public class MyConfig extends DefaultConfig {

    public String name = "myConfig";

    public void setName(String s) {
        name = s;
    }

    @Override
    public String getName() {
        return "MyConfig = " + name;
    }
}
