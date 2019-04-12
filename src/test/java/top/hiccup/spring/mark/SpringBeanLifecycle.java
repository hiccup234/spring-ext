package top.hiccup.spring.mark;

/**
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
 *
 * @author wenhy
 * @date 2019/4/12
 */
public class SpringBeanLifecycle {
}
