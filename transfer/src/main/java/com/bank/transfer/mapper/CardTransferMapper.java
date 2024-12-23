package com.bank.transfer.mapper;

import com.bank.transfer.dto.CardTransferDTO;
import com.bank.transfer.entity.CardTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardTransferMapper {
    CardTransferMapper INSTANCE = Mappers.getMapper(CardTransferMapper.class);

    CardTransferDTO cardTransferToCardTransferDTO(CardTransfer cardTransfer);

    CardTransfer cardTransferDTOToCardTransfer(CardTransferDTO cardTransferDTO);

    List<CardTransferDTO> cardTransferListToDTOList(List<CardTransfer> cardTransfers);
}
