package io.github.dunwu.spring.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Audience {
    private final Logger log = LoggerFactory.getLogger(Audience.class);

    @Pointcut("execution(* io.github.dunwu.spring.aop.Instrumentalist.perform(..))")
    public void performance() {}

    @Before("performance()")
    public void takeSeats() {
        log.debug("takeSeats()");
    }

    @Before("performance()")
    public void turnOffCellPhones() {
        log.debug("turnOffCellPhones()");
    }

    @AfterReturning("performance()")
    public void applaud() {
        log.debug("applaud()");
    }

    @AfterThrowing("performance()")
    public void demandRefund() {
        log.debug("demandRefund()");
    }

}
