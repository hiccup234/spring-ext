package top.hiccup.spring.custom.parse;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 自定义名称空间处理器
 *
 * @author wenhy
 * @date 2020/11/8
 */
public class CustomNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("user", new CustomBeanDefinitionParser());
    }
}
