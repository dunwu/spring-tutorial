package org.zp.notes.spring.scheduler.executor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by zhangcheng on 2017/3/23.
 */

/**
    TaskExecutor

    spring的TaskExecutor为在spring环境下进行并发的多线程编程提供了支持;
    使用ThreadPoolTaskExecutor可实现一个基于线程池的TaskExecutor;
    使用@EnableAsync开启异步任务支持;
    使用@Async注解方法是异步方法;
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.zp.notes.spring.executor");
        DemoAsyncTask task = context.getBean(DemoAsyncTask.class);
        for (int i = 0; i < 10; i++) {
            task.executeAsyncTask(i);
            task.executeAsyncTaskPlus(i);
        }
        context.close();
    }
}
