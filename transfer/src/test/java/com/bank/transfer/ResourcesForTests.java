package com.bank.transfer;

import com.bank.transfer.dto.AccountTransferDTO;
import com.bank.transfer.dto.AuditDTO;
import com.bank.transfer.dto.CardTransferDTO;
import com.bank.transfer.dto.PhoneTransferDTO;
import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.entity.Audit;
import com.bank.transfer.entity.CardTransfer;
import com.bank.transfer.entity.PhoneTransfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResourcesForTests {

    public static final long ID_1 = 1L;
    public static final long ID_2 = 2L;
    public static final long ACCOUNT_NUMBER_1 = 5L;
    public static final long ACCOUNT_NUMBER_2 = 6L;
    public static final long CARD_NUMBER_1 = 7L;
    public static final long CARD_NUMBER_2 = 8L;
    public static final long PHONE_NUMBER_1 = 9L;
    public static final long PHONE_NUMBER_2 = 10L;
    public static final BigDecimal AMOUNT_1 = new BigDecimal("39654.34");
    public static final BigDecimal AMOUNT_2 = new BigDecimal("34.34");
    public static final String PURPOSE_1 = "for apartment payment";
    public static final String PURPOSE_2 = "for auto payment";
    public static final long ACCOUNT_DETAILS_ID_1 = 10L;
    public static final long ACCOUNT_DETAILS_ID_2 = 20L;
    public static final AccountTransfer accountTransfer = new AccountTransfer(ACCOUNT_NUMBER_1, AMOUNT_1, PURPOSE_1, ACCOUNT_DETAILS_ID_1);
    public static final CardTransfer cardTransfer = new CardTransfer(CARD_NUMBER_1, AMOUNT_1, PURPOSE_1, ACCOUNT_DETAILS_ID_1);
    public static final PhoneTransfer phoneTransfer = new PhoneTransfer(PHONE_NUMBER_1, AMOUNT_1, PURPOSE_1, ACCOUNT_DETAILS_ID_1);

    public static final String ENTITY_TYPE_A_T = "AccountTransfer";
    public static final String ENTITY_TYPE_C_T = "CardTransfer";
    public static final String ENTITY_TYPE_P_T = "PhoneTransfer";
    public static final String OPERATION_TYPE_CREATE = "CREATE";
    public static final String OPERATION_TYPE_UPDATE = "UPDATE";
    public static final String CREATED_BY = "100";
    public static final String MODIFIED_BY = "200";
    public static final LocalDateTime CREATED_AT = LocalDateTime.parse("2024-12-22T12:36:46.594599");
    public static final LocalDateTime MODIFIED_AT = LocalDateTime.parse("2024-12-22T12:40:28.096895");
    public static final String ENTITY_JSON = String.format("{\"id\":%d,\"accountNumber\":%d,\"amount\":%f," +
            "\"purpose\":%s,\"accountDetailsId\":%d}", ID_1, ACCOUNT_NUMBER_1, AMOUNT_1, PURPOSE_1, ACCOUNT_DETAILS_ID_1);
    public static final String NEW_ENTITY_JSON = String.format("{\"id\":%d,\"accountNumber\":%d,\"amount\":%f" +
            ",\"purpose\":%s,\"accountDetailsId\":%d}", ID_1, ACCOUNT_NUMBER_1, AMOUNT_1, PURPOSE_1, ACCOUNT_DETAILS_ID_1);

    public static final String ACCOUNT_TRANSFER_URL = "/account/";
    public static final String CARD_TRANSFER_URL = "/card/";
    public static final String PHONE_TRANSFER_URL = "/phone/";
    public static final String AUDIT_URL = "/audit/";
    public static List<AccountTransfer> accountTransferList = new ArrayList<>();
    public static List<AccountTransferDTO> accountTransferListDTO = new ArrayList<>();
    public static List<CardTransfer> cardTransferList = new ArrayList<>();
    public static List<CardTransferDTO> cardTransferListDTO = new ArrayList<>();
    public static List<PhoneTransfer> phoneTransferList = new ArrayList<>();
    public static List<PhoneTransferDTO> phoneTransferListDTO = new ArrayList<>();
    public static List<Audit> auditList = new ArrayList<>();
    public static List<AuditDTO> auditListDTO = new ArrayList<>();
    public static final String ACCOUNT_TO_STRING = "AccountTransfer(" +
            "id=" + ID_1 +
            ", accountNumber=" + ACCOUNT_NUMBER_1 +
            ", amount=" + AMOUNT_1 +
            ", purpose=" + PURPOSE_1 +
            ", accountDetailsId=" + ACCOUNT_DETAILS_ID_1 +
            ')';
    public static final String CARD_TO_STRING = "CardTransfer(" +
            "id=" + ID_1 +
            ", cardNumber=" + CARD_NUMBER_1 +
            ", amount=" + AMOUNT_1 +
            ", purpose=" + PURPOSE_1 +
            ", accountDetailsId=" + ACCOUNT_DETAILS_ID_1 +
            ')';
    public static final String PHONE_TO_STRING = "PhoneTransfer(" +
            "id=" + ID_1 +
            ", phoneNumber=" + PHONE_NUMBER_1 +
            ", amount=" + AMOUNT_1 +
            ", purpose=" + PURPOSE_1 +
            ", accountDetailsId=" + ACCOUNT_DETAILS_ID_1 +
            ')';

    public static final String AUDIT_TO_STRING = "Audit(" +
            "id=" + ID_1 +
            ", entityType=" + ENTITY_TYPE_C_T +
            ", operationType=" + OPERATION_TYPE_CREATE +
            ", createdBy=" + CREATED_BY +
            ", modifiedBy=" + MODIFIED_BY +
            ", createdAt=" + CREATED_AT +
            ", modifiedAt=" + MODIFIED_AT +
            ", newEntityJson=" + NEW_ENTITY_JSON +
            ", entityJson=" + ENTITY_JSON +
            ')';
    public static final AccountTransfer accountTransfer1 = AccountTransfer.builder()
            .id(ID_1)
            .accountNumber(ACCOUNT_NUMBER_1)
            .amount(AMOUNT_1)
            .purpose(PURPOSE_1)
            .accountDetailsId(ACCOUNT_DETAILS_ID_1)
            .build();
    public static final AccountTransfer accountTransfer2 = AccountTransfer.builder()
            .id(ID_2)
            .accountNumber(ACCOUNT_NUMBER_2)
            .amount(AMOUNT_2)
            .purpose(PURPOSE_2)
            .accountDetailsId(ACCOUNT_DETAILS_ID_2)
            .build();
    public static final AccountTransferDTO accountTransferDTO1 = AccountTransferDTO.builder()
            .id(ID_1)
            .accountNumber(ACCOUNT_NUMBER_1)
            .amount(AMOUNT_1)
            .purpose(PURPOSE_1)
            .accountDetailsId(ACCOUNT_DETAILS_ID_1)
            .build();
    public static final AccountTransferDTO accountTransferDTO2 = AccountTransferDTO.builder()
            .id(ID_2)
            .accountNumber(ACCOUNT_NUMBER_2)
            .amount(AMOUNT_2)
            .purpose(PURPOSE_2)
            .accountDetailsId(ACCOUNT_DETAILS_ID_2)
            .build();

    public static final CardTransfer cardTransfer1 = CardTransfer.builder()
            .id(ID_1)
            .cardNumber(CARD_NUMBER_1)
            .amount(AMOUNT_1)
            .purpose(PURPOSE_1)
            .accountDetailsId(ACCOUNT_DETAILS_ID_1)
            .build();
    public static CardTransfer cardTransfer2 = CardTransfer.builder()
            .id(ID_2)
            .cardNumber(CARD_NUMBER_2)
            .amount(AMOUNT_2)
            .purpose(PURPOSE_2)
            .accountDetailsId(ACCOUNT_DETAILS_ID_2)
            .build();
    public static final CardTransferDTO cardTransferDTO1 = CardTransferDTO.builder()
            .id(ID_1)
            .cardNumber(CARD_NUMBER_1)
            .amount(AMOUNT_1)
            .purpose(PURPOSE_1)
            .accountDetailsId(ACCOUNT_DETAILS_ID_1)
            .build();
    public static final CardTransferDTO cardTransferDTO2 = CardTransferDTO.builder()
            .id(ID_2)
            .cardNumber(CARD_NUMBER_2)
            .amount(AMOUNT_2)
            .purpose(PURPOSE_2)
            .accountDetailsId(ACCOUNT_DETAILS_ID_2)
            .build();
    public static final PhoneTransfer phoneTransfer1 = PhoneTransfer.builder()
            .id(ID_1)
            .phoneNumber(PHONE_NUMBER_1)
            .amount(AMOUNT_1)
            .purpose(PURPOSE_1)
            .accountDetailsId(ACCOUNT_DETAILS_ID_1)
            .build();
    public static final PhoneTransfer phoneTransfer2 = PhoneTransfer.builder()
            .id(ID_2)
            .phoneNumber(PHONE_NUMBER_2)
            .amount(AMOUNT_2)
            .purpose(PURPOSE_2)
            .accountDetailsId(ACCOUNT_DETAILS_ID_2)
            .build();
    public static final PhoneTransferDTO phoneTransferDTO1 = PhoneTransferDTO.builder()
            .id(ID_1)
            .phoneNumber(PHONE_NUMBER_1)
            .amount(AMOUNT_1)
            .purpose(PURPOSE_1)
            .accountDetailsId(ACCOUNT_DETAILS_ID_1)
            .build();
    public static final PhoneTransferDTO phoneTransferDTO2 = PhoneTransferDTO.builder()
            .id(ID_2)
            .phoneNumber(PHONE_NUMBER_2)
            .amount(AMOUNT_2)
            .purpose(PURPOSE_2)
            .accountDetailsId(ACCOUNT_DETAILS_ID_2)
            .build();

    public static final Audit audit1 = Audit.builder()
            .id(ID_1)
            .entityType(ENTITY_TYPE_A_T)
            .operationType(OPERATION_TYPE_UPDATE)
            .createdBy(CREATED_BY)
            .modifiedBy(MODIFIED_BY)
            .createdAt(CREATED_AT)
            .modifiedAt(MODIFIED_AT)
            .newEntityJson(NEW_ENTITY_JSON)
            .entityJson(ENTITY_JSON)
            .build();
    public static final Audit audit2 = Audit.builder()
            .id(ID_2)
            .entityType(ENTITY_TYPE_C_T)
            .operationType(OPERATION_TYPE_CREATE)
            .createdBy(CREATED_BY)
            .modifiedBy(MODIFIED_BY)
            .createdAt(CREATED_AT)
            .modifiedAt(MODIFIED_AT)
            .newEntityJson(NEW_ENTITY_JSON)
            .entityJson(ENTITY_JSON)
            .build();
    public static AuditDTO auditDTO = new AuditDTO();
    public static final AuditDTO auditDTO1 = AuditDTO.builder()
            .id(ID_1)
            .entityType(ENTITY_TYPE_A_T)
            .operationType(OPERATION_TYPE_UPDATE)
            .createdBy(CREATED_BY)
            .modifiedBy(MODIFIED_BY)
            .createdAt(CREATED_AT)
            .modifiedAt(MODIFIED_AT)
            .newEntityJson(NEW_ENTITY_JSON)
            .entityJson(ENTITY_JSON)
            .build();

    public static final AuditDTO auditDTO2 = AuditDTO.builder()
            .id(ID_2)
            .entityType(ENTITY_TYPE_C_T)
            .operationType(OPERATION_TYPE_CREATE)
            .createdBy(CREATED_BY)
            .modifiedBy(MODIFIED_BY)
            .createdAt(CREATED_AT)
            .modifiedAt(MODIFIED_AT)
            .newEntityJson(NEW_ENTITY_JSON)
            .entityJson(ENTITY_JSON)
            .build();

    static {
        accountTransferList.add(accountTransfer1);
        accountTransferList.add(accountTransfer2);
        accountTransferListDTO.add(accountTransferDTO1);
        accountTransferListDTO.add(accountTransferDTO2);
        cardTransferList.add(cardTransfer1);
        cardTransferList.add(cardTransfer1);
        cardTransferListDTO.add(cardTransferDTO1);
        cardTransferListDTO.add(cardTransferDTO2);
        phoneTransferList.add(phoneTransfer1);
        phoneTransferList.add(phoneTransfer2);
        phoneTransferListDTO.add(phoneTransferDTO1);
        phoneTransferListDTO.add(phoneTransferDTO2);
        auditList.add(audit1);
        auditList.add(audit2);
        auditListDTO.add(auditDTO1);
        auditListDTO.add(auditDTO2);
    }
}
