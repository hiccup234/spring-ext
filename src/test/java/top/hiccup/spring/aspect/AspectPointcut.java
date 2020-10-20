package top.hiccup.spring.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 专门定义Aspect切入点的类
 *
 * @author wenhy
 * @date 2018/9/2
 */
@Aspect
public class AspectPointcut {

    @Pointcut("execution(* com.hiccup.test.aspectj.service.*.*(..))")
    public void myPointcut() {}
}