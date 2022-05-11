package dev.amplifire.app.aspects;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component // basic stereotype to add this into the Spring IoC container
@Aspect // says this is an aspect class (a class with advice)
public class LoggingAspect {
private Logger log;
	
	@Around("execution(* dev.revature.app..* (..))")
	public Object loggingAdvice(ProceedingJoinPoint targetedMethod) throws Throwable {
		Class joinPointClass = targetedMethod.getTarget().getClass();
		log = LogManager.getLogger(joinPointClass);
		
		log.info("Method called: " + targetedMethod.getSignature().toShortString());
		log.info("with arguments: " + Arrays.toString(targetedMethod.getArgs()));
		
		Object returnedObj = null;
		
		try {
			returnedObj = targetedMethod.proceed();
		} catch (Throwable t) {
			log.error(t.getMessage());
			throw t;
		}
		
		log.info(targetedMethod.getSignature().toShortString() + " returned " + returnedObj);
		return returnedObj;
	}

	/*
	@Before("execution(* com.revature.petapp..* (..))")
	public void beforeAdvice() {
		System.out.println("ADVICE HAPPENING BEFORE THE METHOD");
	}
	@After("everything()") // "everything()" refers to the method that the pointcut is on
	public void afterAdvice() {
		System.out.println("ADVICE HAPPENING AFTER THE METHOD");
	}
	@AfterThrowing("everything()")
	public void afterThrowingAdvice() {
		System.out.println("ADVICE HAPPENING AFTER AN EXCEPTION IS THROWN");
	}
	@AfterReturning("everything()")
	public void afterReturningAdvice() {
		System.out.println("ADVICE HAPPENING AFTER THE METHOD IS SUCCESSFUL");
	}
	@Around("everything()")
	public Object aroundAdvice(ProceedingJoinPoint targetedMethod) throws Throwable {
		System.out.println("AROUND ADVICE BEFORE THE METHOD");
		// with around advice, we actually have to tell the method when it's allowed
		// to actually run, otherwise it won't, and we also have to return the 
		// returned object or the method won't return anything
		Object returnObj = targetedMethod.proceed();
		System.out.println("AROUND ADVICE AFTER METHOD BUT BEFORE RETURNING");
		return returnObj;
	}
	
	// reusable pointcut
	@Pointcut("execution(* com.revature.petapp..* (..))")
	public void everything() {} // hook method (empty method to put an annotation on)
	*/

}
