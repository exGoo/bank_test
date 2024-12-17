package com.bank.publicinfo.utils;


import com.bank.publicinfo.dto.ATMDto;
import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.dto.LicenseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class TestsUtils {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private TestsUtils() {
        throw new UnsupportedOperationException("This class cannot be installed");
    }

    public static final String TEST_NAME = "ExampleName";

    public static final String TEST_CITY_1 = "Moscow";

    public static final String TEST_CITY_2 = "Minsk";

    public static final String TEST_STREET_1 = "UnionStreet";

    public static final String TEST_STREET_2 = "MyStreet";

    public static final Long TEST_ID_1 = 1L;

    public static final Long TEST_ID_2 = 2L;

    public static final LocalTime TEST_START_WORK = LocalTime.of(8, 0);

    public static final LocalTime TEST_END_WORK = LocalTime.of(12, 0);

    public static final byte[] TEST_BYTE_ARRAY = {1, 2, 3, 4, 5, 6};

    public static final ATMDto TEST_ATM_DTO = new ATMDto(TEST_ID_1, TEST_STREET_1, TEST_START_WORK, TEST_END_WORK, false, TEST_ID_2);

    public static final List<ATMDto> TEST_LIST_ATMS = createList(TEST_ATM_DTO,
            new ATMDto(TEST_ID_2, TEST_STREET_2, TEST_START_WORK, TEST_END_WORK, false, TEST_ID_1)
    );

    public static final BankDetailsDto TEST_DETAILS_DTO = new BankDetailsDto(TEST_ID_1, 112L, 113L, 114L, 115, TEST_CITY_1, "JointStock", TEST_NAME,
            Set.of(TEST_ID_1, TEST_ID_2), Set.of(TEST_ID_2, TEST_ID_1));

    public static final List<BankDetailsDto> TEST_LIST_DETAILS = createList(TEST_DETAILS_DTO,
            new BankDetailsDto(TEST_ID_2, 113L, 114L, 115L, 116, TEST_CITY_1, "JointStock2", TEST_NAME,
                    Set.of(TEST_ID_2, TEST_ID_1), Set.of(TEST_ID_1, TEST_ID_2)));

    public static final BranchDto TEST_BRANCH_DTO = new BranchDto(TEST_ID_1, TEST_STREET_1, 12345L, TEST_CITY_1, TEST_START_WORK, TEST_END_WORK,
            Set.of(TEST_ID_1, TEST_ID_2));

    public static final List<BranchDto> TEST_LIST_BRANCHES = createList(TEST_BRANCH_DTO,
            new BranchDto(TEST_ID_2, TEST_STREET_2, 122345L, TEST_CITY_1, TEST_START_WORK, TEST_END_WORK,
                    Set.of(TEST_ID_2, TEST_ID_1)));

    public static final CertificateDto TEST_CERTIFICATE_DTO = new CertificateDto(TEST_ID_1, TEST_BYTE_ARRAY, TEST_ID_2);

    public static final List<CertificateDto> TEST_LIST_CERTIFICATES = createList(TEST_CERTIFICATE_DTO,
            new CertificateDto(TEST_ID_2, TEST_BYTE_ARRAY, TEST_ID_1));

    public static final LicenseDto TEST_LICENSE_DTO = new LicenseDto(TEST_ID_1, TEST_BYTE_ARRAY, TEST_ID_2);

    public static final List<LicenseDto> TEST_LIST_LICENSE = createList(TEST_LICENSE_DTO,
            new LicenseDto(TEST_ID_2, TEST_BYTE_ARRAY, TEST_ID_1));

    public static String toJson(Object object) {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while converting object to JSON", e);
        }
    }

    @SafeVarargs
    public static <T> List<T> createList(T... items) {
        List<T> list = new ArrayList<>();
        for (T item : items) {
            list.add(item);
        }
        return list;
    }
}
