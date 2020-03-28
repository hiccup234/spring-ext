package top.hiccup.spring.ext.test.replace.scan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 替换组件注解RRR
 *
 * @author wenhy
 * @date 2020/3/25
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RRR {

    String value();
}
