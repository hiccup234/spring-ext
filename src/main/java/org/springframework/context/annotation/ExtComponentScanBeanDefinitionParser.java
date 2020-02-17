package org.springframework.context.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlReaderContext;

import top.hiccup.spring.ext.SpringContextHolder;
import org.w3c.dom.Element;

public class ExtComponentScanBeanDefinitionParser extends ComponentScanBeanDefinitionParser {

//    protected static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
//    protected static final String EXPRESSION_ATTRIBUTE = "expression";
//    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected ClassPathBeanDefinitionScanner createScanner(XmlReaderContext readerContext, boolean useDefaultFilters) {
        return new ExtClassPathBeanDefinitionScanner(readerContext.getRegistry(), useDefaultFilters);
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        registerContextHolderBean(element, parserContext);
        return super.parse(element, parserContext);
    }

    protected void registerContextHolderBean(Element element, ParserContext parserContext) {
        Object source = parserContext.extractSource(element);
        RootBeanDefinition contextHolderDef = new RootBeanDefinition(SpringContextHolder.class);
        contextHolderDef.setSource(source);
        contextHolderDef.setRole(2);

        String beanName = parserContext.getReaderContext().generateBeanName(contextHolderDef);
        parserContext.getRegistry().registerBeanDefinition(beanName, contextHolderDef);
        parserContext.registerComponent(new BeanComponentDefinition(contextHolderDef, beanName));
    }
}