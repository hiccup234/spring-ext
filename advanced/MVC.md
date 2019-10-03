
## SpringMVC流程

    Request --> DispatcherServlet --> HandlerMapping --> HandlerAdapter --> Controller

    --> ModelAndView --> ViewReslover --> View --> (Client)

    BeanNameUrlHandlerMapping：表示将请求的URL和Bean名字映射，如URL为“上下文/hello”，
    则Spring容器中必须有一个名字为“/hello”的bean，上下文默认忽略。

    SimpleControllerHandlerAdapter：表示所有实现了org.springframework.web.servlet.mvc.Controller接口的bean
    可以作为SpringWebMVC中的处理器，这是由它的supports方法决定的，它会调用controller的handleRequest方法。 

    SimpleUrlHandlerMapping，默认为BeanNameUrlHandlerMapping，但一般都不用，而是使用DefaultAnnotationHandlerMapping
    注解方式配置的controller则默认使用RequestMappingHandlerMapping

## @RequestParam
    SpringMVC的Controller处理请求时会自动绑定数据（HttpServletRequest、HttpServletResponse等）
    
    而参数的自动绑定是跟据方法入参的变量名，javac编译后可以把局部变量名存储在LocalVariableTable属性中，
    但是这个feature是debugger的，也就是说编译的时候可以关闭，这时SpringMVC就没办法自动绑定了。
    
    
    
    
    