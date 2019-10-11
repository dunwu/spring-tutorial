package io.github.dunwu.spring.core.aop;

import org.springframework.stereotype.Component;

@Component
@Aspect
public class Audience {

	@Pointcut("execution(* io.github.dunwu.spring.core.aop.Instrumentalist.perform(..))")
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
