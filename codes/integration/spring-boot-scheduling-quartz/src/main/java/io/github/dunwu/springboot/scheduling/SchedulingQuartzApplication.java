package io.github.dunwu.springboot.scheduling;

import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SchedulingQuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulingQuartzApplication.class, args);
    }

    @Bean
    public Trigger sampleJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder =
            SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever();

        return TriggerBuilder.newTrigger()
            .forJob(sampleJobDetail())
            .withIdentity("sampleTrigger")
            .withSchedule(scheduleBuilder)
            .build();
    }

    @Bean
    public JobDetail sampleJobDetail() {
        return JobBuilder.newJob(SampleJob.class)
            .withIdentity("sampleJob")
            .usingJobData("name", "World")
            .storeDurably()
            .build();
    }

}
