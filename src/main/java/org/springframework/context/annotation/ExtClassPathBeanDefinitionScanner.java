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
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

/**
 * 扩展ClassPathBeanDefinitionScanner
 *
 * @author wenhy
 * @date 2018/8/9
 */
public class ExtClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public ExtClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    private Replace getReplaceAnnotation(String beanClassName) throws ClassNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        if (classLoader == null) {
            classLoader = ClassUtils.getDefaultClassLoader();
        }
        Class clazz = Class.forName(beanClassName, true, classLoader);
        Replace replace = (Replace) AnnotationUtils.findAnnotation(clazz, Replace.class);
        return replace;
    }

    @Override
    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) throws IllegalStateException {
        if (!getRegistry().containsBeanDefinition(beanName)) {
            return true;
        }
        BeanDefinition existingDef = getRegistry().getBeanDefinition(beanName);
        BeanDefinition originatingDef = existingDef.getOriginatingBeanDefinition();
        if (originatingDef != null) {
            existingDef = originatingDef;
        }
        if (isCompatible(beanDefinition, existingDef)) {
            return false;
        }

        /////////////////////////////////
        try {
            // 判断beanDefinition是不是本地化组件
            Replace replace = getReplaceAnnotation(beanDefinition.getBeanClassName());
            if (replace != null) {
                if (logger.isWarnEnabled()) {
                    logger.warn("组件名称【" + beanName + "】，本地组件类名【" + beanDefinition.getBeanClassName()
                            + "】替换核心组件类名【" + existingDef.getBeanClassName() + "】");
                }
                return true;
            }
            // 如果不是本地化组件，再看existingDef是否为本地化组件
            replace = getReplaceAnnotation(existingDef.getBeanClassName());
            if (replace != null) {
                if (logger.isWarnEnabled()) {
                    logger.warn("组件名称【" + beanName + "】，已加载本地组件类名【" + existingDef.getBeanClassName()
                            + "】，忽略核心组件的加载【" + beanDefinition.getBeanClassName() + "】");
                }
                return false;
            }

        } catch (ClassNotFoundException e) {
            logger.error("ExtClassPathBeanDefinitionScanner ClassNotFoundException: ", e);
        }
        /////////////////////////////////

        throw new IllegalStateException("Annotation-specified bean name '" + beanName + "' for bean class [" + beanDefinition
                .getBeanClassName() + "] conflicts with existing, " + "non-compatible bean definition of same name and class [" + existingDef
                .getBeanClassName() + "]");
    }
}