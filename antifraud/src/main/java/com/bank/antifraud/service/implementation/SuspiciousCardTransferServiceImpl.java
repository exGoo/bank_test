package com.bank.antifraud.service.implementation;

import com.bank.antifraud.exception.NotFoundSuspiciousCardTransferException;
import com.bank.antifraud.model.SuspiciousCardTransfer;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the {@link SuspiciousCardTransferService} interface.
 * Provides methods to manage suspicious card transfers.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SuspiciousCardTransferServiceImpl implements SuspiciousCardTransferService {

    private final SuspiciousCardTransferRepository sctr;

    /**
     * Adds a new suspicious card transfer.
     *
     * @param scd the suspicious card transfer to add
     */
    @Override
    public void add(SuspiciousCardTransfer scd) {
        sctr.save(scd);
    }

    /**
     * Retrieves a suspicious card transfer by its ID.
     *
     * @param id the ID of the suspicious card transfer
     * @return the suspicious card transfer with the specified ID
     * @throws NotFoundSuspiciousCardTransferException if no suspicious card transfer is found with the specified ID
     */
    @Override
    @Transactional(readOnly = true)
    public SuspiciousCardTransfer get(Long id) {
        return sctr.findById(id)
                .orElseThrow(() -> new NotFoundSuspiciousCardTransferException(id));
    }

    /**
     * Updates an existing suspicious card transfer.
     *
     * @param scd the suspicious card transfer to update
     * @throws NotFoundSuspiciousCardTransferException if no suspicious card transfer is found with the specified ID
     */
    @Override
    public void update(SuspiciousCardTransfer scd) {
        if (!sctr.existsById(scd.getId())) {
            throw new NotFoundSuspiciousCardTransferException(scd.getId());
        }
        sctr.save(scd);
    }

    /**
     * Removes a suspicious card transfer by its ID.
     *
     * @param id the ID of the suspicious card transfer to remove
     * @throws NotFoundSuspiciousCardTransferException if no suspicious card transfer is found with the specified ID
     */
    @Override
    public void remove(Long id) {
        if (!sctr.existsById(id)) {
            throw new NotFoundSuspiciousCardTransferException(id);
        }
        sctr.deleteById(id);
    }

    /**
     * Retrieves all suspicious card transfers.
     *
     * @return a list of all suspicious card transfers
     */
    @Override
    @Transactional(readOnly = true)
    public List<SuspiciousCardTransfer> getAll() {
        return sctr.findAll();
    }
}