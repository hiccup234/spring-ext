package top.hiccup.spring.ext.test.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Spring不会把@Aspect修饰的Bean当作组件Bean处理，负责自动增强的后处理器Bean会忽略该Bean
 *
 * @author wenhy
 * @date 2018/9/2
 */
@Aspect
public class AspectAdvice {

    @Before("execution(* com.hiccup.test.aspectj.service.*.*(..))")
    public void auth() {
        System.out.println("方法执行前，模拟权限检查");
    }

    @After("execution(* com.hiccup.test.aspectj.service.*.*(..))")
    public void logout() {
        System.out.println("方法执行后，模拟推出登陆");
    }

    @AfterReturning(returning = "ret", pointcut = "execution(* com.hiccup.test.aspectj.service.*.*(..))")
    public void getReturn(Object ret) {
        System.out.println("目标方法返回值：" + ret);
    }

//    @Pointcut("execution(* com.hiccup.test.aspectj.service.*.*(..))")
//    private void myPointcut() {}
    // TODO 这个版本的@Pointcut定义一直会报错
//    @AfterThrowing(throwing = "e", pointcut = "AspectPointcut.myPointcut()")
    public void dealException(Throwable e) {
        e.printStackTrace();
        System.out.println("模拟处理异常：" + e);
    }

    @Around("execution(* com.hiccup.test.aspectj.service.*.*(..))")
    public Object dealTx(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("模拟事务开始");
        System.out.println("原来参数：" + joinPoint.getArgs());
        Object ret = joinPoint.proceed(new Object[]{"修改参数"});
        System.out.println("模拟事务结束");
        System.out.println("模拟事务结束后：" + ret);
        return ret + "修改结果";
    }

}
