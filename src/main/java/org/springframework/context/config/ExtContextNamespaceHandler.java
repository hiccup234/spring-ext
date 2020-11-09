package org.springframework.context.config;

import org.springframework.context.annotation.AnnotationConfigBeanDefinitionParser;
import org.springframework.context.annotation.ExtComponentScanBeanDefinitionParser;

/**
 * Spring context名称空间处理器扩展类
 * （不同的名称空间可以自定义对应的扩展类）
 *
 * @author wenhy
 * @date 2018/7/29
 */
public class ExtContextNamespaceHandler extends ContextNamespaceHandler {

    @Override
    public void init() {

        registerBeanDefinitionParser("property-placeholder", new PropertyPlaceholderBeanDefinitionParser());
        registerBeanDefinitionParser("property-override", new PropertyOverrideBeanDefinitionParser());
        registerBeanDefinitionParser("annotation-config", new AnnotationConfigBeanDefinitionParser());
        // 这里只扩展了component-scan元素的解析器
        registerBeanDefinitionParser("component-scan", new ExtComponentScanBeanDefinitionParser());
        registerBeanDefinitionParser("load-time-weaver", new LoadTimeWeaverBeanDefinitionParser());
        registerBeanDefinitionParser("spring-configured", new SpringConfiguredBeanDefinitionParser());
        registerBeanDefinitionParser("mbean-export", new MBeanExportBeanDefinitionParser());
        registerBeanDefinitionParser("mbean-server", new MBeanServerBeanDefinitionParser());
    }

}