
Spring是一个容器，用于解决具有依赖关系的对象创建问题，以及管理对象的生命周期。

## Spring框架特点
轻量，控制反转(IOC,DI)，面向切面的编程(AOP)，容器(管理应用中对象的生命周期和配置)
MVC框架，事务管理，异常处理（把具体技术相关的异常（比如JDBC，Hibernate，JDO抛出的）转化为一致的unchecked异常

Spring扫描class文件，将其解析成BeanDefinition，在BeanDefinition中会描述类的信息，
例如:某个类是否是单例的，Bean的类型，是否是懒加载，依赖哪些类，自动装配的模型。
Spring创建对象时，就是根据BeanDefinition中的信息来创建Bean。

DefaultListableBeanFactory有几个非常重要的属性：
beanDefinitionMap存放bean所对应的BeanDefinition
beanDefinitionNames存放所有bean的name
singletonObjects是一个Map，用来存放所有创建好的单例Bean（多例BeanSpring不会管理生命周期）

Spring中有很多后置处理器，一般分为两种：
BeanFactoryPostProcessor和BeanPostProcessor
前者是用来干预BeanFactory的创建过程，
后者是用来干预Bean的创建过程。

后置处理器的作用十分重要，bean的创建以及AOP的实现全部依赖后置处理器。

refresh()方法很重要

## ApplicationContext与BeanFactory区别？ BeanFactory与FactoryBean区别？
基本可以理解为：ApplicationContext = BeanFactory + Resources
FactoryBean 为工厂方法模式的工厂bean，通过factory-method属性指定

## IOC方式一般有2种
   构造器注入
   Setter方法注入
    
## Spring bean的5种作用域：singleton prototype request session  global-session


常见的WebApplicationContext: 
GenericWebApplicationContext、AnnotationConfigWebApplicationContext


 * Spring容器初始化开始:
 * 1. BeanFactoryPostProcessor 接口实现类的构造器
 * 2. BeanFactoryPostProcessor 的postProcessorBeanFactory方法
 * 3. BeanPostProcessor 接口实现类的构造器
 * 4. InstantiationAwareBeanPostProcessorAdapter 构造器
 * 5. InstantiationAwareBeanPostProcessorAdapter 的postProcessBeforeInstantiation方法(从这里开始初始化bean)
 * 6. Bean 的构造器
 * 7. InstantiationAwareBeanPostProcessorAdapter 的postProcessAfterInstantiation
 * 8. InstantiationAwareBeanPostProcessorAdapter 的postProcessPropertyValues方法
 * 9. Bean 属性注入，setter方法
 * 10. Bean 如果实现了各种XXXaware接口，依次调用各个setXXX(如BeanNameAware.setBeanName(),BeanFactoryAware.setBeanFactory())
 * 11. BeanPostProcessor 的postProcessBeforeInitialization方法
 * 12. InstantiationAwareBeanPostProcessorAdapter 的postProcessBeforeInitialization方法
 * 13. Bean 自定义的init-method
 * 14. Bean 如果实现了InitializingBean接口，此时会调用它的afterPropertiesSet方法
 * 15. BeanPostProcessor 的postProcessAfterInitialization方法(此时bean初始化完成)
 * 16. InstantiationAwareBeanPostProcessorAdapter 的postProcessInitialization方法(到这里容器初始化完成)
 * 17.业务逻辑bean的使用
 *
 * Bean的销毁过程:
 * 1. DisposableBean 的destory方法
 * 2. Bean 自定义的destory-method方法
 *
 * 说明:如果有多个bean需要初始化，会循环执行5--15。
 *
 *
 *
 * 首先理解scope:
 * 1. Singleton(单例) 在整个应用中,只创建bean的一个实例
 * 2. Propotype(原型) 每次注入或者通过Spring应用上下文获取的时候,都会创建一个新
 * 的bean实例。
 * 3. Request(请求) 在Web应用中,为每个请求创建一个bean实例。
 * 4. Session(会话) 在Web应用中,为每个会话创建一个bean实例。
 *
 * 和其他scope相比,Spring并没有管理prototype实例完整的生命周期,在实例化,配置,组装对象交给应用后,spring不再管理。
 * 只要bean本身不持有对另一个资源（如数据库连接或会话对象）的引用，只要删除了对该对象的所有引用或对象超出范围，就会立即收集垃圾。