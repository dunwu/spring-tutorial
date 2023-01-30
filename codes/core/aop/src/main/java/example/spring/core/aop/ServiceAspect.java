package example.spring.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 方法拦截器
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-09
 */
@Aspect
@Component
public class ServiceAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* example.spring.core.aop..*Service.*(..))")
    public void execute() {}

    @Before("execute()")
    public void before(JoinPoint joinPoint) {
        log.info("call @Before, joinPoint: {}, execute args: {}", joinPoint, joinPoint.getArgs());
    }

    @After("execute()")
    public void after(JoinPoint joinPoint) {
        log.info("call @After, joinPoint: {}", joinPoint);
    }

    @AfterReturning("execute()")
    public void afterReturning(JoinPoint joinPoint) {
        log.info("call @AfterReturning, joinPoint: {}", joinPoint);
    }

    @AfterThrowing("execute()")
    public void afterThrowing(JoinPoint joinPoint) {
        log.error("call @AfterThrowing, joinPoint: {}", joinPoint);
    }

}
