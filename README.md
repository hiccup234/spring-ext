# spring-ext
学习 spring framework 时编写的测试程序以及一些有趣的扩展开发

## @Replace 注解
@Replace一般修饰在类上，主要用来快速替换Spring容器中的Bean组件

父类申明：
@Service("testBean")
public class BaseClass {
    public String getName() {
        return "BaseClass";
    }
}
子类申明：子类重写父类方法getName
@Service("testBean")
@Replace
public class SubClass extends BaseClass {
    @Override
    public String getName(){
        return "SubClass";
    }
}


-- Maven中央仓库坐标
<dependency>
    <groupId>top.hiccup</groupId>
    <artifactId>spring-ext</artifactId>
    <version>5.2.2.0-SNAPSHOT</version>
</dependency>