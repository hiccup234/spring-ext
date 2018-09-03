package org.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * Replace注解
 *
 * @author wenhy
 * @date 2018/5/31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Replace {

    // 多个继承子类时，以order值最大的作为最终注册到容器的bean
    String order() default "0";

}
