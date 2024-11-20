package com.bank.history.mapper;

import com.bank.history.dto.HistoryDto;
import com.bank.history.model.History;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    HistoryDto historyToDto(History history);

    History dtoToHistory(HistoryDto historyDto);

    List<HistoryDto> historiesToDtoList(List<History> histories);
}
