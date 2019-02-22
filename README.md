# spring-ext
学习springframework时编写的测试程序以及一些有趣的扩展开发，程序里都会有详细的注释以及一些发散性思考

# Spring框架特点
轻量，控制反转(IOC,DI)，面向切面的编程(AOP)，容器(管理应用中对象的生命周期和配置)
MVC框架，事务管理，异常处理（把具体技术相关的异常（比如JDBC，Hibernate，JDO抛出的）转化为一致的unchecked异常


基本可以理解为：ApplicationContext = BeanFactory + Resources


常见的WebApplicationContext: 

GenericWebApplicationContext

AnnotationConfigWebApplicationContext