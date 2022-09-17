package io.github.dunwu.spring.core.bean.java;

import org.springframework.stereotype.Component;

@Component("teacher")
public class Teacher implements Job {

    @Override
    public String work() {
        return "教课";
    }

}
