package com.bank.antifraud.annotation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {

    @NotNull
    Action action();

    @NotNull
    EntityType entityType();

    @Getter
    @RequiredArgsConstructor
    enum Action {

        CREATE("CREATE"),

        UPDATE("UPDATE");

        private final String stringAction;
    }

    @Getter
    @RequiredArgsConstructor
    enum EntityType {

        SUSPICIOUS_ACCOUNT_TRANSFERS("SuspiciousAccountTransfers"),

        SUSPICIOUS_CARD_TRANSFER("SuspiciousCardTransfer"),

        SUSPICIOUS_PHONE_TRANSFERS("SuspiciousPhoneTransfers");

        private final String stringEntityType;
    }
}
