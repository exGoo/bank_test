package com.bank.transfer.entity;

import org.junit.jupiter.api.Test;

import static com.bank.transfer.ResourcesForTests.ACCOUNT_DETAILS_ID_1;
import static com.bank.transfer.ResourcesForTests.ACCOUNT_DETAILS_ID_2;
import static com.bank.transfer.ResourcesForTests.AMOUNT_1;
import static com.bank.transfer.ResourcesForTests.AMOUNT_2;
import static com.bank.transfer.ResourcesForTests.ID_1;
import static com.bank.transfer.ResourcesForTests.ID_2;
import static com.bank.transfer.ResourcesForTests.PHONE_NUMBER_1;
import static com.bank.transfer.ResourcesForTests.PHONE_NUMBER_2;
import static com.bank.transfer.ResourcesForTests.PHONE_TO_STRING;
import static com.bank.transfer.ResourcesForTests.PURPOSE_1;
import static com.bank.transfer.ResourcesForTests.PURPOSE_2;
import static com.bank.transfer.ResourcesForTests.phoneTransfer;
import static com.bank.transfer.ResourcesForTests.phoneTransfer1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PhoneTransferTest {
    @Test
    void testSettersAndGetters() {
        phoneTransfer1.setId(ID_2);
        assertEquals(ID_2, phoneTransfer1.getId());
        phoneTransfer1.setPhoneNumber(PHONE_NUMBER_2);
        assertEquals(PHONE_NUMBER_2, phoneTransfer1.getPhoneNumber());
        phoneTransfer1.setAmount(AMOUNT_2);
        assertEquals(AMOUNT_2, phoneTransfer1.getAmount());
        phoneTransfer1.setPurpose(PURPOSE_2);
        assertEquals(PURPOSE_2, phoneTransfer1.getPurpose());
        phoneTransfer1.setAccountDetailsId(ACCOUNT_DETAILS_ID_2);
        assertEquals(ACCOUNT_DETAILS_ID_2, phoneTransfer1.getAccountDetailsId());
    }

    @Test
    void testBuilder() {
        assertNotNull(phoneTransfer1);
        assertEquals(PHONE_NUMBER_2, phoneTransfer1.getPhoneNumber());
        assertEquals(AMOUNT_2, phoneTransfer1.getAmount());
        assertEquals(PURPOSE_2, phoneTransfer1.getPurpose());
        assertEquals(ACCOUNT_DETAILS_ID_2, phoneTransfer1.getAccountDetailsId());
    }

    @Test
    void testConstructor() {
        assertNotNull(phoneTransfer);
        assertEquals(PHONE_NUMBER_1, phoneTransfer.getPhoneNumber());
        assertEquals(AMOUNT_1, phoneTransfer.getAmount());
        assertEquals(PURPOSE_1, phoneTransfer.getPurpose());
        assertEquals(ACCOUNT_DETAILS_ID_1, phoneTransfer.getAccountDetailsId());
    }

    @Test
    void testToString() {
        phoneTransfer1.setId(ID_1);
        phoneTransfer1.setPhoneNumber(PHONE_NUMBER_1);
        phoneTransfer1.setAmount(AMOUNT_1);
        phoneTransfer1.setPurpose(PURPOSE_1);
        phoneTransfer1.setAccountDetailsId(ACCOUNT_DETAILS_ID_1);
        assertEquals(PHONE_TO_STRING, phoneTransfer1.toString());
    }
}
