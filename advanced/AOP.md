
## 面向切面编程（AOP）

### 切面（Aspect）
    横跨多个核心逻辑的功能，又称系统关注点，如：事务管理、日志记录和安全检查等
    
### 连接点（Joinpoint）
    定义在应用流程的何处插入切面的执行，如：调用一个方法、访问一个字段，或者特定的异常抛出

### 切入点（Pointcut）
    切入点就是一组连接点的集合，如：可以用正则表达式(.*query.*)来表示一组连接点

### 通知（Advice）又称为 增强
    在切面的某个特定的连接点（Joinpoint）上执行的动作，通知有各种类型，其中包括“around”、“before”和“after”等
    
### Advisor
    Advisor包含了一个Advice，并且定义了如何将Advice织入到目标对象中

### 织入（Weaving）
    把切面（aspect）连接到其它的应用程序类型或者对象上，并创建一个被通知（advised）的对象。 
    这些可以在编译时（例如使用AspectJ编译器），类加载时和运行时完成。 
    Spring和其他纯Java AOP框架一样，在运行时完成织入。 


PROPAGATION_REQUIRED -- 支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。 
PROPAGATION_SUPPORTS -- 支持当前事务，如果当前没有事务，就以非事务方式执行。 
PROPAGATION_MANDATORY -- 支持当前事务，如果当前没有事务，就抛出异常。 
PROPAGATION_REQUIRES_NEW -- 新建事务，如果当前存在事务，把当前事务挂起。 
PROPAGATION_NOT_SUPPORTED -- 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。 
PROPAGATION_NEVER -- 以非事务方式执行，如果当前存在事务，则抛出异常。 
PROPAGATION_NESTED -- 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与PROPAGATION_REQUIRED类似的操作。 

PROPAGATION_REQUIRES_NEW 和 PROPAGATION_NESTED 的最大区别在于， PROPAGATION_REQUIRES_NEW 完全是一个新的事务，
而 PROPAGATION_NESTED 则是外部事务的子事务, 如果外部事务 commit， 潜套事务也会被 commit， 这个规则同样适用于 roll back。