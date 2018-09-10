package org.springframework.context.config;

import com.hiccup.spring.ext.UserBeanDefinitionParser;
import com.hiccup.spring.ext.UserBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.context.annotation.AnnotationConfigBeanDefinitionParser;
import org.springframework.context.annotation.ExtComponentScanBeanDefinitionParser;

/**
 * Spring命名空间处理器扩展类
 *
 * @author wenhy
 * @date 2018/7/29
 */
public class ExtContextNamespaceHandler extends NamespaceHandlerSupport
{
    @Override
    public void init()
    {
        // 注册元素user的BeanDefinition解析器，类似context中的component-scan
        registerBeanDefinitionParser("user", new UserBeanDefinitionParser());

        registerBeanDefinitionParser("property-placeholder", new PropertyPlaceholderBeanDefinitionParser());
        registerBeanDefinitionParser("property-override", new PropertyOverrideBeanDefinitionParser());
        registerBeanDefinitionParser("annotation-config", new AnnotationConfigBeanDefinitionParser());

        registerBeanDefinitionParser("component-scan", new ExtComponentScanBeanDefinitionParser());
        registerBeanDefinitionParser("load-time-weaver", new LoadTimeWeaverBeanDefinitionParser());
        registerBeanDefinitionParser("spring-configured", new SpringConfiguredBeanDefinitionParser());
        registerBeanDefinitionParser("mbean-export", new MBeanExportBeanDefinitionParser());
        registerBeanDefinitionParser("mbean-server", new MBeanServerBeanDefinitionParser());
    }
}