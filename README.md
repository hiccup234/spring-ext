# spring-ext
学习 spring framework 时编写的测试程序以及一些有趣的扩展开发

## Spring自定义标签扩展
    1、创建xsd文件
    2、实现BeanDefinitionParse接口，用来解析xsd对应名称空间的配置
    3、创建NamespaceHandlerSupport扩展，将组件注册到Spring容器
    4、编写spring.handlers和spring.schemas文件

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
@Replace("testBean")
public class SubClass extends BaseClass {
    @Override
    public String getName(){
        return "SubClass";
    }
}

## Maven中央仓库坐标 
对应Spring的5.2.2
```
<dependency>
    <groupId>top.hiccup</groupId>
    <artifactId>spring-ext</artifactId>
    <version>5.2.2.0-SNAPSHOT</version>
</dependency>
```