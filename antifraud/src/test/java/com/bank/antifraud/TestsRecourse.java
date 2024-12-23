package com.bank.antifraud;

import com.bank.antifraud.annotation.Auditable;
import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;
import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.entity.SuspiciousPhoneTransfers;
import com.bank.antifraud.entity.SuspiciousAccountTransfers;
import com.bank.antifraud.util.antifraudSystem.transferDto.AccountTransferDto;
import com.bank.antifraud.util.antifraudSystem.transferDto.CardTransferDto;
import com.bank.antifraud.util.antifraudSystem.transferDto.PhoneTransferDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestsRecourse {

    // Common constants
    public static final long ID = 1L;
    public static final long WRONG_ID = 500L;
    public static final String SUSPICIOUS_REASON = "suspicious";
    public static final String BLOCKED_REASON = "blocked";
    public static final Pageable PAGEABLE = PageRequest.of(0, 10);

    // Audit constants
    public static final String ENTITY_TYPE = "SuspiciousAccountTransfers";
    public static final String OPERATION_TYPE_CREATE = "CREATE";
    public static final String CREATOR = "admin";
    public static final String MODIFIER = "user";
    public static final String ENTITY_JSON = String.format(
            "{\"id\":%d, \"accountTransferId\":%d, \"isBlocked\":%b, " +
                    "\"isSuspicious\":%b, \"blockedReason\":%s, \"suspiciousReason\":%s}",
            ID, ID, false, true, BLOCKED_REASON, SUSPICIOUS_REASON
    );
    public static final String NEW_ENTITY_JSON = String.format(
            "{\"id\":%d, \"accountTransferId\":%d, \"isBlocked\":%b, " +
                    "\"isSuspicious\":%b, \"blockedReason\":%s, \"suspiciousReason\":%s}",
            ID, ID, false, true, BLOCKED_REASON, SUSPICIOUS_REASON
    );
    public static final String MATCHER = "\"id\":1";

    // Enum with dto
    @Getter
    @RequiredArgsConstructor
    public enum Dto {

        DEFAULT(
                SuspiciousAccountTransfersDto.builder()
                        .id(ID)
                        .accountTransferId(ID)
                        .isBlocked(false)
                        .isSuspicious(true)
                        .suspiciousReason(SUSPICIOUS_REASON)
                        .build(),
                SuspiciousCardTransferDto.builder()
                        .id(ID)
                        .cardTransferId(ID)
                        .isBlocked(false)
                        .isSuspicious(true)
                        .suspiciousReason(SUSPICIOUS_REASON)
                        .build(),
                SuspiciousPhoneTransfersDto.builder()
                        .id(ID)
                        .phoneTransferId(ID)
                        .isBlocked(false)
                        .isSuspicious(true)
                        .suspiciousReason(SUSPICIOUS_REASON)
                        .build()
        ),

        FOR_UPDATE(
                SuspiciousAccountTransfersDto.builder()
                        .isBlocked(true)
                        .blockedReason(BLOCKED_REASON)
                        .build(),
                SuspiciousCardTransferDto.builder()
                        .isBlocked(true)
                        .blockedReason(BLOCKED_REASON)
                        .build(),
                SuspiciousPhoneTransfersDto.builder()
                        .isBlocked(true)
                        .blockedReason(BLOCKED_REASON)
                        .build()
        ),

        UPDATED_DTO (
                SuspiciousAccountTransfersDto.builder()
                        .id(ID)
                        .accountTransferId(ID)
                        .isBlocked(true)
                        .blockedReason(BLOCKED_REASON)
                        .isSuspicious(true)
                        .suspiciousReason(SUSPICIOUS_REASON)
                        .build(),
                SuspiciousCardTransferDto.builder()
                        .id(ID)
                        .cardTransferId(ID)
                        .isBlocked(true)
                        .blockedReason(BLOCKED_REASON)
                        .isSuspicious(true)
                        .suspiciousReason(SUSPICIOUS_REASON)
                        .build(),
                SuspiciousPhoneTransfersDto.builder()
                        .id(ID)
                        .phoneTransferId(ID)
                        .isBlocked(true)
                        .blockedReason(BLOCKED_REASON)
                        .isSuspicious(true)
                        .suspiciousReason(SUSPICIOUS_REASON)
                        .build()
        );

        private final SuspiciousAccountTransfersDto satDto;
        private final SuspiciousCardTransferDto sctDto;
        private final SuspiciousPhoneTransfersDto sptDto;
    }

    // Enum with entity
    @Getter
    @RequiredArgsConstructor
    public enum Entity {
        DEFAULT(
                SuspiciousAccountTransfers.builder()
                        .id(ID)
                        .accountTransferId(ID)
                        .isBlocked(false)
                        .isSuspicious(true)
                        .suspiciousReason(SUSPICIOUS_REASON)
                        .build(),
                SuspiciousCardTransfer.builder()
                        .id(ID)
                        .cardTransferId(ID)
                        .isBlocked(false)
                        .isSuspicious(true)
                        .suspiciousReason(SUSPICIOUS_REASON)
                        .build(),
                SuspiciousPhoneTransfers.builder()
                        .id(ID)
                        .phoneTransferId(ID)
                        .isBlocked(false)
                        .isSuspicious(true)
                        .suspiciousReason(SUSPICIOUS_REASON)
                        .build(),
                Audit.builder()
                        .id(ID)
                        .entityType(ENTITY_TYPE)
                        .operationType(OPERATION_TYPE_CREATE)
                        .createdBy(CREATOR)
                        .createdAt(OffsetDateTime.now())
                        .entityJson(ENTITY_JSON)
                        .build()
        );

        private final SuspiciousAccountTransfers sat;
        private final SuspiciousCardTransfer sct;
        private final SuspiciousPhoneTransfers spt;
        private final Audit audit;
    }

    // Antifraud constants
    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.valueOf(1000);
    private static final BigDecimal SUSPICIOUS_AMOUNT = BigDecimal.valueOf(3000);
    private static final BigDecimal BLOCKED_AMOUNT = BigDecimal.valueOf(10000);

    // Enum with Transfers dto
    @Getter
    @RequiredArgsConstructor
    public enum TransferDto {
        DEFAULT(
                AccountTransferDto.builder()
                        .id(ID)
                        .accountNumber(ID)
                        .accountDetailsId(ID)
                        .amount(DEFAULT_AMOUNT)
                        .build(),
                CardTransferDto.builder()
                        .id(ID)
                        .cardNumber(ID)
                        .amount(DEFAULT_AMOUNT)
                        .accountDetailsId(ID)
                        .build(),
                PhoneTransferDto.builder()
                        .id(ID)
                        .phoneNumber(ID)
                        .amount(DEFAULT_AMOUNT)
                        .accountDetailsId(ID)
                        .build()
        ),

        SUSPICIOUS(
                AccountTransferDto.builder()
                        .id(ID)
                        .accountNumber(ID)
                        .accountDetailsId(ID)
                        .amount(SUSPICIOUS_AMOUNT)
                        .build(),
                CardTransferDto.builder()
                        .id(ID)
                        .cardNumber(ID)
                        .amount(SUSPICIOUS_AMOUNT)
                        .accountDetailsId(ID)
                        .build(),
                PhoneTransferDto.builder()
                        .id(ID)
                        .phoneNumber(ID)
                        .amount(SUSPICIOUS_AMOUNT)
                        .accountDetailsId(ID)
                        .build()
        ),

        BLOCKED(
                AccountTransferDto.builder()
                        .id(ID)
                        .accountNumber(ID)
                        .accountDetailsId(ID)
                        .amount(BLOCKED_AMOUNT)
                        .build(),
                CardTransferDto.builder()
                        .id(ID)
                        .cardNumber(ID)
                        .amount(BLOCKED_AMOUNT)
                        .accountDetailsId(ID)
                        .build(),
                PhoneTransferDto.builder()
                        .id(ID)
                        .phoneNumber(ID)
                        .amount(BLOCKED_AMOUNT)
                        .accountDetailsId(ID)
                        .build()
        );

        private final AccountTransferDto accountTransferDto;
        private final CardTransferDto cardTransferDto;
        private final PhoneTransferDto phoneTransferDto;
    }

    public enum ExpectedResult {
        NORMAL,
        IT_SUSPICIOUS,
        IT_MUST_BLOCKED
    }

    // Methods for @MethodSource
    public static Stream<Arguments> getId() {
        return Stream.of(
                Arguments.of(WRONG_ID),
                Arguments.of(ID)
        );
    }

    public static Stream<Arguments> getListOfSuspiciousAccountTransfersDto() {
        final List<SuspiciousAccountTransfersDto> list = new ArrayList<>();
        long id = ID;
        for (int i = 0; i < 2; i++) {
            list.add(SuspiciousAccountTransfersDto.builder()
                    .id(id)
                    .accountTransferId(id)
                    .isBlocked(false)
                    .isSuspicious(false)
                    .suspiciousReason(SUSPICIOUS_REASON)
                    .build());
            id++;
        }
        return Stream.of(
                Arguments.of(list),
                Arguments.of(List.of())
        );
    }

    public static Stream<Arguments> getListOfSuspiciousCardTransferDto() {
        final List<SuspiciousCardTransferDto> list = new ArrayList<>();
        long id = ID;
        for (int i = 0; i < 2; i++) {
            list.add(SuspiciousCardTransferDto.builder()
                    .id(id)
                    .cardTransferId(id)
                    .isBlocked(false)
                    .isSuspicious(false)
                    .suspiciousReason(SUSPICIOUS_REASON)
                    .build());
            id++;
        }
        return Stream.of(
                Arguments.of(list),
                Arguments.of(List.of())
        );
    }

    public static Stream<Arguments> getListOfSuspiciousPhoneTransfersDto() {
        final List<SuspiciousPhoneTransfersDto> list = new ArrayList<>();
        long id = ID;
        for (int i = 0; i < 2; i++) {
            list.add(SuspiciousPhoneTransfersDto.builder()
                    .id(id)
                    .phoneTransferId(id)
                    .isBlocked(false)
                    .isSuspicious(false)
                    .suspiciousReason(SUSPICIOUS_REASON)
                    .build());
            id++;
        }
        return Stream.of(
                Arguments.of(list),
                Arguments.of(List.of())
        );
    }

    public static Stream<Arguments> getJoinPointProceedResult() {
        return Stream.of(
                Arguments.of(Dto.DEFAULT.getSatDto(), Auditable.EntityType.SUSPICIOUS_ACCOUNT_TRANSFERS),
                Arguments.of(Dto.DEFAULT.getSctDto(), Auditable.EntityType.SUSPICIOUS_CARD_TRANSFER),
                Arguments.of(Dto.DEFAULT.getSptDto(), Auditable.EntityType.SUSPICIOUS_PHONE_TRANSFERS)
        );
    }

    public static Stream<Arguments> getPageSuspiciousAccountTransfersDto() {
        return Stream.of(
                Arguments.of(new PageImpl<>(List.of(Dto.DEFAULT.getSatDto()), PAGEABLE, 1)),
                Arguments.of(Page.empty())
        );
    }

    public static Stream<Arguments> getPageSuspiciousCardTransferDto() {
        return Stream.of(
                Arguments.of(new PageImpl<>(List.of(Dto.DEFAULT.getSctDto()), PAGEABLE, 1)),
                Arguments.of(new PageImpl<>(List.of(), PAGEABLE, 0))
        );
    }

    public static Stream<Arguments> getPageSuspiciousPhoneTransfersDto() {
        return Stream.of(
                Arguments.of(new PageImpl<>(List.of(Dto.DEFAULT.getSptDto()), PAGEABLE, 1)),
                Arguments.of(new PageImpl<>(List.of(), PAGEABLE, 0))
        );
    }

    public static Stream<Arguments> getArgumentsForCheckAccountTransferTest() {
        SuspiciousAccountTransfers entity = SuspiciousAccountTransfers.builder()
                .id(ID)
                .accountTransferId(ID)
                .isSuspicious(false)
                .isBlocked(false)
                .build();
        List<AccountTransferDto> list = List.of(
                TransferDto.DEFAULT.getAccountTransferDto(),
                TransferDto.DEFAULT.getAccountTransferDto(),
                TransferDto.DEFAULT.getAccountTransferDto());
        return Stream.of(
                Arguments.of(
                        entity,
                        list,
                        TransferDto.DEFAULT.getAccountTransferDto(),
                        ExpectedResult.NORMAL
                ),
                Arguments.of(
                        entity,
                        List.of(),
                        TransferDto.DEFAULT.getAccountTransferDto(),
                        ExpectedResult.NORMAL
                ),
                Arguments.of(
                        entity,
                        list,
                        TransferDto.SUSPICIOUS.getAccountTransferDto(),
                        ExpectedResult.IT_SUSPICIOUS
                ),
                Arguments.of(
                        entity,
                        list,
                        TransferDto.BLOCKED.getAccountTransferDto(),
                        ExpectedResult.IT_MUST_BLOCKED
                )
        );
    }

    public static Stream<Arguments> getArgumentsForCheckCardTransferTest() {
        SuspiciousCardTransfer entity = SuspiciousCardTransfer.builder()
                .id(ID)
                .cardTransferId(ID)
                .isSuspicious(false)
                .isBlocked(false)
                .build();
        List<CardTransferDto> list = List.of(
                TransferDto.DEFAULT.getCardTransferDto(),
                TransferDto.DEFAULT.getCardTransferDto(),
                TransferDto.DEFAULT.getCardTransferDto());
        return Stream.of(
                Arguments.of(
                        entity,
                        list,
                        TransferDto.DEFAULT.getCardTransferDto(),
                        ExpectedResult.NORMAL
                ),
                Arguments.of(
                        entity,
                        List.of(),
                        TransferDto.DEFAULT.getCardTransferDto(),
                        ExpectedResult.NORMAL
                ),
                Arguments.of(
                        entity,
                        list,
                        TransferDto.SUSPICIOUS.getCardTransferDto(),
                        ExpectedResult.IT_SUSPICIOUS
                ),
                Arguments.of(
                        entity,
                        list,
                        TransferDto.BLOCKED.getCardTransferDto(),
                        ExpectedResult.IT_MUST_BLOCKED
                )
        );
    }

    public static Stream<Arguments> getArgumentsForCheckPhoneTransferTest() {
        SuspiciousPhoneTransfers entity = SuspiciousPhoneTransfers.builder()
                .id(ID)
                .phoneTransferId(ID)
                .isSuspicious(false)
                .isBlocked(false)
                .build();
        List<PhoneTransferDto> list = List.of(
                TransferDto.DEFAULT.getPhoneTransferDto(),
                TransferDto.DEFAULT.getPhoneTransferDto(),
                TransferDto.DEFAULT.getPhoneTransferDto());
        return Stream.of(
                Arguments.of(
                        entity,
                        list,
                        TransferDto.DEFAULT.getPhoneTransferDto(),
                        ExpectedResult.NORMAL
                ),
                Arguments.of(
                        entity,
                        List.of(),
                        TransferDto.DEFAULT.getPhoneTransferDto(),
                        ExpectedResult.NORMAL
                ),
                Arguments.of(
                        entity,
                        list,
                        TransferDto.SUSPICIOUS.getPhoneTransferDto(),
                        ExpectedResult.IT_SUSPICIOUS
                ),
                Arguments.of(
                        entity,
                        list,
                        TransferDto.BLOCKED.getPhoneTransferDto(),
                        ExpectedResult.IT_MUST_BLOCKED
                )
        );
    }
}
