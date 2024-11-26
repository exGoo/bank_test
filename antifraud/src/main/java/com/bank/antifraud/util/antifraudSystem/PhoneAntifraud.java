package com.bank.antifraud.util.antifraudSystem;

import com.bank.antifraud.model.SuspiciousPhoneTransfers;
import com.bank.antifraud.util.antifraudSystem.responseObject.PhoneTransfer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class PhoneAntifraud {

    private RestTemplate restTemplate;
    private String url;

    public SuspiciousPhoneTransfers checkTransaction(SuspiciousPhoneTransfers spt) {
        final PhoneTransfer phoneTransfer = getPhoneTransferById(spt.getPhoneTransferId());
        final List<PhoneTransfer> list = getTransactionsByPhoneNumber(
                phoneTransfer.getPhoneNumber(),
                phoneTransfer.getAccountDetailsId()
        );
        final int average = getAverageAmount(list);
        if (isSuspicious(average, phoneTransfer.getAmount())) {
            spt.setIsSuspicious(true);
            spt.setSuspiciousReason("The current transaction amount is greater " +
                    "than the average value of transaction amounts.");
            if (isMustBlocked(average, phoneTransfer.getAmount())) {
                spt.setIsBlocked(true);
                spt.setBlockedReason("Suspicion of theft of personal funds.");
            }
        }
        return spt;
    }

    private PhoneTransfer getPhoneTransferById(Long id) {
        restTemplate = new RestTemplate();
        url = "http://localhost:8080/transfer/phoneTransfer/" + id;
        final ResponseEntity<PhoneTransfer> response = restTemplate
                .getForEntity(url, PhoneTransfer.class, HttpMethod.GET);
        return Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new NotFoundException("Phone transfer not found."));
    }

    /*
        допустим у сервиса transfer будет метод, возвращающий список данных
        по уникальному полю phoneNumber и текущему account_details_id
    */
    private List<PhoneTransfer> getTransactionsByPhoneNumber(Long phoneNumber, Long accountDetailsId) {
        restTemplate = new RestTemplate();
        url = "http://localhost:8080/transfer/phoneTransfer" +
                "?phoneNumber=" + phoneNumber +
                "&accountDetailsId=" + accountDetailsId;
        final ResponseEntity<List<PhoneTransfer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PhoneTransfer>>() {
                });
        return Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new NotFoundException("Transfers by phone not found."));
    }

    private int getAverageAmount(List<PhoneTransfer> phoneTransfers) {
        return phoneTransfers.stream()
                .map(PhoneTransfer::getAmount)
                .reduce(0, Integer::sum) / phoneTransfers.size();
    }

    private boolean isSuspicious(int averageAmount, int currentAmount) {
        final double suspiciousPercent = 2.0;
        return averageAmount * suspiciousPercent < currentAmount;
    }

    private boolean isMustBlocked(int averageAmount, int currentAmount) {
        final double blockPercent = 5.0;
        return averageAmount * blockPercent < currentAmount;
    }
}
