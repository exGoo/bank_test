package com.bank.transfer.entity;

import org.junit.jupiter.api.Test;

import static com.bank.transfer.ResourcesForTests.ACCOUNT_DETAILS_ID_1;
import static com.bank.transfer.ResourcesForTests.ACCOUNT_DETAILS_ID_2;
import static com.bank.transfer.ResourcesForTests.ACCOUNT_NUMBER_1;
import static com.bank.transfer.ResourcesForTests.ACCOUNT_NUMBER_2;
import static com.bank.transfer.ResourcesForTests.ACCOUNT_TO_STRING;
import static com.bank.transfer.ResourcesForTests.AMOUNT_1;
import static com.bank.transfer.ResourcesForTests.AMOUNT_2;
import static com.bank.transfer.ResourcesForTests.ID_1;
import static com.bank.transfer.ResourcesForTests.ID_2;
import static com.bank.transfer.ResourcesForTests.PURPOSE_1;
import static com.bank.transfer.ResourcesForTests.PURPOSE_2;
import static com.bank.transfer.ResourcesForTests.accountTransfer;
import static com.bank.transfer.ResourcesForTests.accountTransfer1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountTransferTest {

    @Test
    void testSettersAndGetters() {
        accountTransfer1.setId(ID_2);
        assertEquals(ID_2, accountTransfer1.getId());
        accountTransfer1.setAccountNumber(ACCOUNT_NUMBER_2);
        assertEquals(ACCOUNT_NUMBER_2, accountTransfer1.getAccountNumber());
        accountTransfer1.setAmount(AMOUNT_2);
        assertEquals(AMOUNT_2, accountTransfer1.getAmount());
        accountTransfer1.setPurpose(PURPOSE_2);
        assertEquals(PURPOSE_2, accountTransfer1.getPurpose());
        accountTransfer1.setAccountDetailsId(ACCOUNT_DETAILS_ID_2);
        assertEquals(ACCOUNT_DETAILS_ID_2, accountTransfer1.getAccountDetailsId());
    }

    @Test
    void testBuilder() {
        assertNotNull(accountTransfer1);
        assertEquals(ACCOUNT_NUMBER_2, accountTransfer1.getAccountNumber());
        assertEquals(AMOUNT_2, accountTransfer1.getAmount());
        assertEquals(PURPOSE_2, accountTransfer1.getPurpose());
        assertEquals(ACCOUNT_DETAILS_ID_2, accountTransfer1.getAccountDetailsId());
    }

    @Test
    void testConstructor() {
        assertNotNull(accountTransfer);
        assertEquals(ACCOUNT_NUMBER_1, accountTransfer.getAccountNumber());
        assertEquals(AMOUNT_1, accountTransfer.getAmount());
        assertEquals(PURPOSE_1, accountTransfer.getPurpose());
        assertEquals(ACCOUNT_DETAILS_ID_1, accountTransfer.getAccountDetailsId());
    }

    @Test
    void testToString() {
        accountTransfer1.setId(ID_1);
        accountTransfer1.setAccountNumber(ACCOUNT_NUMBER_1);
        accountTransfer1.setAmount(AMOUNT_1);
        accountTransfer1.setPurpose(PURPOSE_1);
        accountTransfer1.setAccountDetailsId(ACCOUNT_DETAILS_ID_1);
        assertEquals(ACCOUNT_TO_STRING, accountTransfer1.toString());
    }
}
