
Java EE = JSP + Servlet规范 + EJB

Spring是一个容器，用于解决具有依赖关系的对象创建问题，以及管理对象的生命周期。
Spring用到的设计模式有：工厂、抽象工厂、单例、命令、责任链、代理、观察者（事件机制）

## Spring框架特点
比EJB更轻量，控制反转(IOC,DI)，面向切面的编程(AOP)，容器(管理应用中对象的生命周期和配置)
MVC框架，事务管理，异常处理（把具体技术相关的异常（比如JDBC，Hibernate，JDO抛出的）转化为一致的unchecked异常

### IOC方式一般有2种
    * 构造器注入
    * Setter方法注入
Spring容器管理的是POJO，而EJB管理的是JavaBean。

Spring扫描class文件，将其解析成BeanDefinition，在BeanDefinition中描述类的信息，
例如:某个类是否是单例的，Bean的类型，是否是懒加载，依赖哪些类，自动装配的模型。
Spring创建对象时（反射），就是根据BeanDefinition中的信息来创建Bean。 Class.forName("").newInstance()

DefaultListableBeanFactory有几个非常重要的属性：
beanDefinitionMap存放bean所对应的BeanDefinition
beanDefinitionNames存放所有bean的name
singletonObjects是一个Map，用来存放所有创建好的单例Bean（多例BeanSpring不会管理生命周期）

### Spring中有很多后置处理器，一般分为两种：
BeanFactoryPostProcessor和BeanPostProcessor，前者是用来干预BeanFactory的创建过程，后者是用来干预Bean的创建过程。
后置处理器的作用十分重要，bean的创建以及AOP的实现全部依赖后置处理器。

### ApplicationContext与BeanFactory区别？ BeanFactory与FactoryBean区别？
基本可以理解为：ApplicationContext = BeanFactory + Resources
初始化Bean的时机不同，BeanFactory一般是等到需要用时才创建，而ApplicationContext是在容器创建时就初始化singleton的Bean。

BeanFactory需要手动注册BeanPostProcessor和BeanFactoryPostProcessor，而ApplicationContext是自动注册的。

### Annotation对比
@PostConstruct  ==  xml中的 init-method
@preDestory  ==  xml中的 destroy-method

@Autowired 默认是 byType 装配的，可以搭配 @Qualifier。（@Autowired可以搭配数组，List，Map等使用                     ）


=============================================================================================

refresh()方法很重要
FactoryBean 为工厂方法模式的工厂bean，通过factory-method属性指定


    
## Spring bean 的5种作用域：singleton、prototype、request、session、global-session
Spring只解决了singleton的循环依赖问题

常见的WebApplicationContext: 
GenericWebApplicationContext、AnnotationConfigWebApplicationContext


Spring容器初始化开始:
1. BeanFactoryPostProcessor 接口实现类的构造器
2. BeanFactoryPostProcessor 的 postProcessorBeanFactory 方法
3. BeanPostProcessor 接口实现类的构造器
4. InstantiationAwareBeanPostProcessorAdapter 构造器
5. InstantiationAwareBeanPostProcessorAdapter 的 postProcessBeforeInstantiation 方法(从这里开始初始化bean)
6. Bean 的构造器
7. InstantiationAwareBeanPostProcessorAdapter 的 postProcessAfterInstantiation
8. InstantiationAwareBeanPostProcessorAdapter 的 postProcessPropertyValues 方法
9. Bean 属性注入，setter方法
10. Bean 如果实现了各种XXXaware接口，依次调用各个setXXX(如BeanNameAware.setBeanName(),BeanFactoryAware.setBeanFactory())
11. BeanPostProcessor 的 postProcessBeforeInitialization 方法
12. InstantiationAwareBeanPostProcessorAdapter 的 postProcessBeforeInitialization 方法
13. Bean 自定义的 init-method s
14. Bean 如果实现了InitializingBean接口，此时会调用它的 afterPropertiesSet 方法
15. BeanPostProcessor 的 postProcessAfterInitialization 方法(此时bean初始化完成)
16. InstantiationAwareBeanPostProcessorAdapter 的 postProcessInitialization 方法(到这里容器初始化完成)
17.业务逻辑bean的使用

Bean的销毁过程:
1. DisposableBean 的destory方法
2. Bean 自定义的destory-method方法

说明:如果有多个bean需要初始化，会循环执行5--15。



首先理解scope:
1. Singleton(单例) 在整个应用中,只创建bean的一个实例
2. Propotype(原型) 每次注入或者通过Spring应用上下文获取的时候,都会创建一个新
的bean实例。
3. Request(请求) 在Web应用中,为每个请求创建一个bean实例。
4. Session(会话) 在Web应用中,为每个会话创建一个bean实例。

和其他scope相比,Spring并没有管理prototype实例完整的生命周期,在实例化,配置,组装对象交给应用后,spring不再管理。
只要bean本身不持有对另一个资源（如数据库连接或会话对象）的引用，只要删除了对该对象的所有引用或对象超出范围，就会立即收集垃圾。
 
 
 解析 applicationgContext.xml，将 xml 中定义的 bean(如 loginService 和 loginResource) 解析成 Spring 内部的 BeanDefinition，
 并以 beanName(如 loginService) 为 key，BeanDefinition(如 loginService 相应的 BeanDefinition) 为 value 存储到 DefaultListableBeanFactory 
 中的 beanDefinitionMap (其实就是一个 ConcurrentHashMap) 中，同时将 beanName 存入 beanDefinitionNames(List 类型) 中，
 然后遍历 beanDefinitionNames 中的 beanName，进行 bean 的实例化并填充属性，在实例化的过程中，如果有依赖没有被实例化将先实例化其依赖，
 然后实例化本身，实例化完成后将实例存入单例 bean 的缓存中，当调用 getBean 方法时，到单例 bean 的缓存中查找，
 如果找到并经过转换后返回这个实例 (如 LoginResource 的实例)，之后就可以直接使用了。
