package com.bank.antifraud.util.antifraudSystem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

public interface TransferChecker {

    default boolean isMustBlocked(BigDecimal currentAmount, BigDecimal averageAmount) {
        final BigDecimal blockedCoefficient = BigDecimal.valueOf(5.0);
        return currentAmount.compareTo(averageAmount.multiply(blockedCoefficient)) > 0;
    }

    default boolean isSuspicious(BigDecimal currentAmount, BigDecimal averageAmount) {
        final BigDecimal suspiciousCoefficient = BigDecimal.valueOf(2.0);
        return currentAmount.compareTo(averageAmount.multiply(suspiciousCoefficient)) > 0;
    }

    default BigDecimal getAverageAmountOfTransfers(Stream<BigDecimal> stream, BigDecimal divider) {
        return stream.reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(divider, 2, RoundingMode.HALF_EVEN);
    }
}
