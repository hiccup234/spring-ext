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
import top.hiccup.spring.ext.SpringContextHolder;
import org.w3c.dom.Element;

/**
 * 扩展AnnotationConfigBeanDefinitionParser
 *
 * @author wenhy
 * @date 2018/8/8
 */
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
