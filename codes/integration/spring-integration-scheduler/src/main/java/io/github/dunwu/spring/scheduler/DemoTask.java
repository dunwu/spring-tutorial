package io.github.dunwu.spring.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一个调度任务例子
 *
 * @author Zhang Peng
 * @since 2016年8月31日
 */
public class DemoTask implements Runnable {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        logger.info("call DemoTask.run");
    }

}
