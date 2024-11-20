package com.bank.history.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingHistoryAspect {

    @Pointcut("execution(* com.bank.history.controller.HistoryController.*(..))")
    public void anyRestControllerMethod() {
    }

    @Around("anyRestControllerMethod()")
    public Object addLoggingForMethodsWithId(ProceedingJoinPoint joinPoint) throws Throwable {
        String nameMethod = joinPoint.getSignature().toShortString();
        log.info("Вызов метода: {}", nameMethod);
        Object result;
        try {
            result = joinPoint.proceed();
            log.info("Метод {} успешно выполнен. Результат: {}", nameMethod, result);
        } catch (Throwable throwable) {
            log.warn("Метод {} выбросил исключение: {}",
                    nameMethod, throwable.getClass().getSimpleName());
            throw throwable;
        }
        return result;
    }
}
