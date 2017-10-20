package io.github.dunwu.spring.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @description Spring Scheduler实时调度范例
 * @author Zhang Peng
 * @date 2016年8月31日
 */
@Controller
@RequestMapping("/scheduler")
public class SchedulerController {

    @Autowired
    TaskScheduler scheduler;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void start() {
        scheduler.schedule(new DemoTask(), new CronTrigger("0/5 * * * * *"));
    }
}
