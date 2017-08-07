package org.zp.notes.spring.scheduler.executor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步任务实现代码
 * Created by zhangcheng on 2017/3/23.
 *
 */
@Component
public class DemoAsyncTask {
    @Async
    public void executeAsyncTask(Integer i){
        System.out.println("执行异步任务:"+i);
    }

    @Async
    public void executeAsyncTaskPlus(Integer i){
        System.out.println("执行异步任务+1:"+(i+1));
    }
}
