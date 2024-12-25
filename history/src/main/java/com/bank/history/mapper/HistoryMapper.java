package com.bank.history.mapper;

import com.bank.history.dto.HistoryDto;
import com.bank.history.model.History;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    HistoryDto historyToDto(History history);

    History dtoToHistory(HistoryDto historyDto);

    List<HistoryDto> historiesToDtoList(List<History> histories);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(HistoryDto historyDto, @MappingTarget History history);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void editEntityFromDto(HistoryDto historyDto, @MappingTarget History history);
}
