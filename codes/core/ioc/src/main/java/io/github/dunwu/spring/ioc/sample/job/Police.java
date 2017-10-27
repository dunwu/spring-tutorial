package io.github.dunwu.spring.ioc.sample.job;

import org.springframework.stereotype.Component;

@Component("police")
public class Police implements Job {
    @Override
    public String work() {
        return "抓罪犯";
    }
}
