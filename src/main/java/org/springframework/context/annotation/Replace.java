package org.springframework.context.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Replace注解：在Spring启动加载Bean时，被@Replace标注的组件替换容器中原来的同名组件
 *
 * @author wenhy
 * @date 2018/5/31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
public @interface Replace {

    @AliasFor(annotation = Component.class)
    String value();
}
