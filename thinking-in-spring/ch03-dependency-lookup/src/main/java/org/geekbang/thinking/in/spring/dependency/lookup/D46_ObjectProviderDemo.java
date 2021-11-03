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
package org.geekbang.thinking.in.spring.dependency.lookup;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 46、延迟依赖查找：费延迟初始化Bean也能实现延迟查找？
 *
 * 通过 {@link ObjectProvider} 进行依赖查找
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since
 */
public class D46_ObjectProviderDemo { // @Configuration 是非必须注解

    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 D46_ObjectProviderDemo 作为配置类（Configuration Class）
        applicationContext.register(D46_ObjectProviderDemo.class);
        // 启动应用上下文
        applicationContext.refresh();

        // 依赖查找集合对象
        System.out.println("--------------- lookupByObjectProvider -----------");
        lookupByObjectProvider(applicationContext);

        System.out.println("\n--------------- lookupIfAvailable -----------");
        lookupIfAvailable(applicationContext);

        System.out.println("\n--------------- lookupByStreamOps -----------");
        lookupByStreamOps(applicationContext);

        // 关闭应用上下文
        applicationContext.close();

    }


    /**
     * 没有指定的情况下，方法名就是 Bean 名称 = "helloWorld"
     * @return
     */
    @Bean
    @Primary
    public String helloWorld() {
        return "Hello,World";
    }

    @Bean
    public String message() {
        return "Message";
    }


    /**
     * 1、ObjectProvider <- ObjectFactory、Iterable 的子类
     *      采用 ObjectProvider、ObjectFactory 可以实现延迟加载
     *
     * 下面单一查找Bean
     * @param applicationContext
     */
    private static void lookupByObjectProvider(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        System.out.println(objectProvider.getObject());
    }


    /**
     * 2、通过ObjectProvider延迟加载，查找一个不存在的User Bean对象
     * @param applicationContext
     */
    private static void lookupIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        // getIfAvailable 表示如果不存在，调用 User::createUser方法返回一个默认的User
        User user = userObjectProvider.getIfAvailable(User::createUser);
        System.out.println("当前 User 对象：" + user);
    }


    /**
     * 3、通过ObjectProvider对象实现集合类型查找
     * @param applicationContext
     */
    private static void lookupByStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        objectProvider.stream().forEach(System.out::println);
    }


}
