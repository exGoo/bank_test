package com.bank.transfer.mapper;

import com.bank.transfer.dto.PhoneTransferDTO;
import com.bank.transfer.entity.PhoneTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhoneTransferMapper {
    PhoneTransferMapper INSTANCE = Mappers.getMapper(PhoneTransferMapper.class);

    PhoneTransferDTO phoneTransferToPhoneTransferDTO(PhoneTransfer phoneTransfer);

    PhoneTransfer phoneTransferDTOToPhoneTransfer(PhoneTransferDTO phoneTransferDTO);

    List<PhoneTransferDTO> phoneTransferListToDTOList(List<PhoneTransfer> phoneTransfers);
}
