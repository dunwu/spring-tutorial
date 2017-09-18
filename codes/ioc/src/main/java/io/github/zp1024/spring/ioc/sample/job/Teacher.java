package io.github.zp1024.spring.ioc.sample.job;

import org.springframework.stereotype.Component;

@Component("teacher")
public class Teacher implements Job {
    @Override
    public String work() {
        return "教课";
    }
}
