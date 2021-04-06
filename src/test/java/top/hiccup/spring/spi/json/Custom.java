package top.hiccup.spring.spi.json;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Custom
 *
 * @author wenhy
 * @date 2021/3/6
 */
@Component
public class Custom {

    @Value("${name}")
    public String name;

    @Value("${age}")
    public Integer age;

}
