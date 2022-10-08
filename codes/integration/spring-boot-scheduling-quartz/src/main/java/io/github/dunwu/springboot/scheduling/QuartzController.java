package io.github.dunwu.springboot.scheduling;

import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-29
 */
@RestController
@RequestMapping("quartz")
public class QuartzController {

    private final SchedulerFactoryBean schedulerFactoryBean;

    public QuartzController(SchedulerFactoryBean schedulerFactoryBean) {
        this.schedulerFactoryBean = schedulerFactoryBean;
    }

    @GetMapping("create")
    public void create(String name) throws SchedulerException {

        JobDetail jobDetail = JobBuilder.newJob(SampleJob.class).withIdentity(name).usingJobData("name", name)
            .storeDurably().build();

        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2)
            .repeatForever();

        Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(name).withSchedule(scheduleBuilder)
            .build();

        schedulerFactoryBean.getScheduler().scheduleJob(jobDetail, trigger);
    }

    @GetMapping("remove")
    public void remove(String name) throws SchedulerException {
        JobKey jobKey = new JobKey(name);
        TriggerKey triggerKey = TriggerKey.triggerKey(name);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        // 删除任务
        scheduler.deleteJob(jobKey);
    }

}
