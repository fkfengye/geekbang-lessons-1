/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geekbang.thinking.in.spring.bean.definition;

import org.geekbang.thinking.in.spring.bean.factory.DefaultUserFactory;
import org.geekbang.thinking.in.spring.bean.factory.UserFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 41、面试题精选
 * 单体 Bean 注册实例
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since
 */
public class D41_SingletonBeanRegistrationDemo {

    public static void main(String[] args) throws InterruptedException {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 启动 Spring 应用上下文
        applicationContext.refresh();

        outBeanRegistry(applicationContext);

        // 关闭 Spring 应用上下文
        applicationContext.close();
    }


    /**
     * 将非容器内的对象，注入到Spring容器中
     * @param applicationContext
     */
    public static void outBeanRegistry(AnnotationConfigApplicationContext applicationContext){
        // 手动创建 userFactory 对象，此时userFactory 并不受Spring 容器管理
        UserFactory userFactory = new DefaultUserFactory();

        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();

        // 将非Spring 容器内的对象注入到容器中【使用到了 org.springframework.beans.factory.config.SingletonBeanRegistry.registerSingleton】
        beanFactory.registerSingleton("userFactory", userFactory);


        // 通过依赖查找的方式来获取 UserFactory
        UserFactory userFactoryByLookup = applicationContext.getBean("userFactory", UserFactory.class);
        System.out.println("userFactory  == userFactoryByLookup : " + (userFactory == userFactoryByLookup));
    }

}
