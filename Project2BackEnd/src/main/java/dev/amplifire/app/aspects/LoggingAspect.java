package dev.amplifire.app.aspects;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
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

}
