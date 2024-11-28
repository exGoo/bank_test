package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.ATM;
import com.bank.publicinfo.entity.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    @Mapping(target = "atmsIds", source = "atms", qualifiedByName = "mapAtmsIds")
    BranchDto toDto(Branch branch);

    @Mapping(target = "atms", ignore = true)
    @Mapping(target = "id", ignore = true)
    Branch toModel(BranchDto branchDto);

    @Named("mapAtmsIds")
    default Set<Long> mapAtmsIds(Set<ATM> atms) {
        return atms == null ? null : atms.stream()
                .map(ATM::getId)
                .collect(Collectors.toSet());
    }


}
