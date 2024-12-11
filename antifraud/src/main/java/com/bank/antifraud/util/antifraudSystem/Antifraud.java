package com.bank.antifraud.util.antifraudSystem;

public interface Antifraud<M> {

    M checkTransaction(M model);

    default boolean isMustBlocked(int currentAmount, int averageAmount) {
        final double blockedPercent = 5.0;
        return currentAmount > averageAmount * blockedPercent;
    }

    default boolean isSuspicious(int currentAmount, int averageAmount) {
        final double suspiciousPercent = 2.0;
        return currentAmount > averageAmount * suspiciousPercent ;
    }

}
