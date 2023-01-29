package example.spring.core.aop.entity;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Audience {

    @Pointcut("execution(* example.spring.core.aop.entity.Instrumentalist.perform(..))")
    public void performance() {
    }

    @Before("performance()")
    public void takeSeats() {
        System.out.println("takeSeats()");
    }

    @Before("performance()")
    public void turnOffCellPhones() {
        System.out.println("turnOffCellPhones()");
    }

    @AfterReturning("performance()")
    public void applaud() {
        System.out.println("applaud()");
    }

    @AfterThrowing("performance()")
    public void demandRefund() {
        System.out.println("demandRefund()");
    }

}
