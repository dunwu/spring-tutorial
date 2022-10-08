package io.github.dunwu.springboot.scheduling;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SampleJob extends QuartzJobBean {

    private String name;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.printf("Hello %s!%n", this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

}
