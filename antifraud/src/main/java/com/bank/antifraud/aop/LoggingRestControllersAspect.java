package com.bank.antifraud.aop;

import liquibase.pro.packaged.P;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Aspect
@Component
@Order(1)
public class LoggingRestControllersAspect {

    @Around("com.bank.antifraud.aop.Pointcuts.isRestControllerLayer()")
    public Object addLoggingAroundRestController(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        String className = joinPoint.getTarget().getClass().getName();
        log.info("RestController - invoke method {} in class {}", methodName, className);
        try {
            Object result = Optional.of(joinPoint.proceed());
            log.info("RestController - Invoked method {} in class {} return value {}",methodName, className, result);
            return result;
        } catch (Throwable ex) {
            log.error("RestController - invoked method {} in class {} failed", methodName, className, ex);
            throw ex;
        } finally {
            log.info("RestController - invoked method {} in class {} completed", methodName, className);
        }
    }

}
