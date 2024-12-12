package com.bank.publicinfo.utils;

public interface Auditable <T> {

    T getEntityId();

    String getEntityName();
}
