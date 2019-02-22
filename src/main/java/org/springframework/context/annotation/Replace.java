package org.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * Replace注解：
 * 在Spring启动加载Bean时用被@Replace标注的组件替换容器中原来的同名组件
 *
 * @author wenhy
 * @date 2018/5/31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Replace {

    /**
     * 多个继承子类时，以order值最大的作为最终注册到容器的bean
     * @return
     */
    String order() default "0";
}
