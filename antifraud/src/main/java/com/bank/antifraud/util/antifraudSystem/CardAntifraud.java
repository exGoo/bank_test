package com.bank.antifraud.util.antifraudSystem;

import com.bank.antifraud.model.SuspiciousCardTransfer;
import com.bank.antifraud.util.antifraudSystem.responseObject.CardTransfer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class CardAntifraud {

    private RestTemplate restTemplate;
    private String url;

    public SuspiciousCardTransfer checkTransaction(SuspiciousCardTransfer sct) {
        final CardTransfer cardTransfer = getCardTransferById(sct.getCardTransferId());
        final List<CardTransfer> list = getTransactionsByCardNumber(
                cardTransfer.getCardNumber(),
                cardTransfer.getAccountDetailsId()
        );
        final int average = getAverageAmount(list);
        if (isSuspicious(average, cardTransfer.getAmount())) {
            sct.setIsSuspicious(true);
            sct.setSuspiciousReason("The current transaction amount is greater " +
                    "than the average value of transaction amounts.");
            if (isMustBlocked(average, cardTransfer.getAmount())) {
                sct.setIsBlocked(true);
                sct.setBlockedReason("Suspicion of theft of personal funds.");
            }
        }
        return sct;
    }

    private CardTransfer getCardTransferById(Long id) {
        restTemplate = new RestTemplate();
        url = "http://localhost:8080/transfer/cardTransfer/" + id;
        final ResponseEntity<CardTransfer> response = restTemplate
                .getForEntity(url, CardTransfer.class, HttpMethod.GET);
        return Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new NotFoundException("Card transfer not found."));
    }

    /*
        допустим у сервиса transfer будет метод, возвращающий список данных
        по уникальному полю cardNumber и текущему account_details_id
    */
    private List<CardTransfer> getTransactionsByCardNumber(Long cardNumber, Long accountDetailsId) {
        restTemplate = new RestTemplate();
        url = "http://localhost:8080/transfer/cardTransfer" +
                "?cardNumber=" + cardNumber +
                "&accountDetailsId=" + accountDetailsId;
        final ResponseEntity<List<CardTransfer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CardTransfer>>() {
                });
        return Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new NotFoundException("Transfers by account not found."));
    }

    private int getAverageAmount(List<CardTransfer> cardTransfers) {
        return cardTransfers.stream()
                .map(CardTransfer::getAmount)
                .reduce(0, Integer::sum) / cardTransfers.size();
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
