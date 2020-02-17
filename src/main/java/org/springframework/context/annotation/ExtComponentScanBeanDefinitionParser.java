/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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