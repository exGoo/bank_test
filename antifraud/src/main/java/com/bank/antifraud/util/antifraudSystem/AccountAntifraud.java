package com.bank.antifraud.util.antifraudSystem;

import com.bank.antifraud.model.SuspiciousAccountTransfers;
import com.bank.antifraud.util.antifraudSystem.responseObject.AccountTransfer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class AccountAntifraud {

    private RestTemplate restTemplate;
    private String url;

    public SuspiciousAccountTransfers checkTransaction(SuspiciousAccountTransfers sat) {
        final AccountTransfer accountTransfer = getAccountTransferById(sat.getAccountTransferId());
        final List<AccountTransfer> list = getTransactionsByAccountNumber(
                accountTransfer.getAccountNumber(),
                accountTransfer.getAccountDetailsId()
        );
        final int average = getAverageAmount(list);
        if (isSuspicious(average, accountTransfer.getAmount())) {
            sat.setIsSuspicious(true);
            sat.setSuspiciousReason("The current transaction amount is greater " +
                    "than the average value of transaction amounts.");
            if (isMustBlocked(average, accountTransfer.getAmount())) {
                sat.setIsBlocked(true);
                sat.setBlockedReason("Suspicion of theft of personal funds.");
            }
        }
        return sat;
    }

    private AccountTransfer getAccountTransferById(Long id) {
        restTemplate = new RestTemplate();
        url = "http://localhost:8080/transfer/accountTransfer/" + id;
        final ResponseEntity<AccountTransfer> response = restTemplate
                .getForEntity(url, AccountTransfer.class, HttpMethod.GET);
        return Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new NotFoundException("Account transfer not found."));
    }

    /*
        допустим у сервиса transfer будет метод, возвращающий список данных
        по уникальному полю accountNumber и текущему account_details_id
    */
    private List<AccountTransfer> getTransactionsByAccountNumber(Long accountNumber, Long accountDetailsId) {
        restTemplate = new RestTemplate();
        url = "http://localhost:8080/transfer/accountTransfer" +
                "?accountNumber=" + accountNumber +
                "&account_detailsId=" + accountDetailsId;
        final ResponseEntity<List<AccountTransfer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AccountTransfer>>() {
                });
        return Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new NotFoundException("Transfers by account not found."));
    }

    private int getAverageAmount(List<AccountTransfer> accountTransfers) {
        return accountTransfers.stream()
                .map(AccountTransfer::getAmount)
                .reduce(0, Integer::sum) / accountTransfers.size();
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
