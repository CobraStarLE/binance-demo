package com.binance.demo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 在容器启动后保存Spring上下文 用于后续service中获取service实例
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        if (context == null) {
            throw new RuntimeException("Spring context didn't init.....");
        }
        return context;
    }

    public static void setContext(final ApplicationContext context) {
        SpringBeanUtil.context = context;
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> t) {
        return context.getBean(name, t);
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }


    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.context = applicationContext;
    }
}