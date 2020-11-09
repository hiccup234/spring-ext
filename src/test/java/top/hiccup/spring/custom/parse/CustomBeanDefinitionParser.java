package top.hiccup.spring.custom.parse;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;
import top.hiccup.spring.custom.domain.User;

/**
 * 自定义bean解析器
 *
 * @author wenhy
 * @date 2018/7/29
 */
public class CustomBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    protected Class<?> getBeanClass(Element element) {
        if ("user".equals(element.getTagName())) {
            System.out.println("fffffffffffffffff" + element.getTagName());
        }
        return User.class;
    }

    protected String getBeanClassName(Element element) {
        return User.class.getName();
    }

    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String name = element.getAttribute("name");
        String age = element.getAttribute("age");
        builder.addPropertyValue("name", name);
        builder.addPropertyValue("age", age);
    }
}
