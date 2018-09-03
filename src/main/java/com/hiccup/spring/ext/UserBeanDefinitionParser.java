package com.hiccup.spring.ext;

import com.hiccup.test.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * user元素的BeanDefinition解析类
 *
 * @author wenhy
 * @date 2018/7/29
 */
public class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    public Class getBeanClass(Element element) {
        // 因为是针对单个BeanDefinition的解析，所以这里直接返回对应类的Class对象
        return User.class;
    }

    @Override
    public void doParse(Element element, BeanDefinitionBuilder builder) {
        String userId = element.getAttribute("userId");
        String name = element.getAttribute("name");
        String age = element.getAttribute("age");
        builder.addPropertyValue("userId", userId);
        builder.addPropertyValue("name", name);
        builder.addPropertyValue("age", age);
    }
}
