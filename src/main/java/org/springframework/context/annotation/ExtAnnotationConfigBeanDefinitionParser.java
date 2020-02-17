package org.springframework.context.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import top.hiccup.spring.ext.SpringContextHolder;
import org.w3c.dom.Element;

public class ExtAnnotationConfigBeanDefinitionParser extends AnnotationConfigBeanDefinitionParser
{
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext)
    {
        super.parse(element, parserContext);
        registerContextHolderBean(element, parserContext);
        return null;
    }

    protected void registerContextHolderBean(Element element, ParserContext parserContext)
    {
        Object source = parserContext.extractSource(element);
        RootBeanDefinition contextHolderDef = new RootBeanDefinition(SpringContextHolder.class);
        contextHolderDef.setSource(source);
        contextHolderDef.setRole(2);

        String beanName = parserContext.getReaderContext().generateBeanName(contextHolderDef);
        parserContext.getRegistry().registerBeanDefinition(beanName, contextHolderDef);
        parserContext.registerComponent(new BeanComponentDefinition(contextHolderDef, beanName));
    }
}
