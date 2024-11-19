package com.bank.antifraud.aop;

import com.bank.antifraud.service.AuditService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class Pointcuts {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void isRestControllerLayer() {
    }

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer() {
    }

    @Pointcut("isServiceLayer() && !this(com.bank.antifraud.service.AuditService)")
    public void isNonAuditServiceInServiceLayer() {
    }

    @Pointcut("!execution(public * com.bank.antifraud.service.*Service.get(*))" +
            "&& !execution(public * com.bank.antifraud.service.*Service.getAll(*))")
    public void isNonGetMethod() {
    }

}
