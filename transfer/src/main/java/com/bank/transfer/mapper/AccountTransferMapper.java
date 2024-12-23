package com.bank.transfer.mapper;

import com.bank.transfer.dto.AccountTransferDTO;
import com.bank.transfer.entity.AccountTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountTransferMapper {
    AccountTransferMapper INSTANCE = Mappers.getMapper(AccountTransferMapper.class);

    AccountTransferDTO accountTransferToAccountTransferDTO(AccountTransfer accountTransfer);

    AccountTransfer accountTransferDTOToAccountTransfer(AccountTransferDTO accountTransferDTO);

    List<AccountTransferDTO> accountTransferListToDTOList(List<AccountTransfer> accountTransfers);
}
