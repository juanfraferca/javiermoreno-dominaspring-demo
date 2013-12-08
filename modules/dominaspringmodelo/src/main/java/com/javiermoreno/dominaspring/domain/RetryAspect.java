package com.javiermoreno.dominaspring.domain;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component 
@Aspect 
public class RetryAspect {

	public RetryAspect() {
	
	}

	@Pointcut(value = "execution(@com.javiermoreno.dominaspring.domain.Retry public * *(..))")
	public void metodosRetriables() {
	}
	

	@Around("metodosRetriables()")
	public Object process(ProceedingJoinPoint jointPoint) throws Throwable {
		Throwable lastException = null;
		MethodSignature  signature = (MethodSignature) jointPoint.getSignature();
		Method signatureMethod = signature.getMethod();
		Object target = jointPoint.getTarget();
		Method targetMethod = target.getClass().getMethod(signatureMethod.getName(), signatureMethod.getParameterTypes());
		Retry retryAnnotation = targetMethod.getAnnotation(Retry.class);
		for (int i=0; i < retryAnnotation.value(); i++) {
			try {
				Object result = jointPoint.proceed();
				return result;
			} catch (Throwable exc) {
				lastException = exc;
				Thread.sleep((int) (Math.pow(i, 2) * 100 * (1 + Math.random())));
			}
		}
		throw lastException;
	}
}
