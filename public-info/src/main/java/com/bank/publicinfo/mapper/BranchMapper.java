package com.bank.publicinfo.mapper;
import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.Branch;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    @Mapping(target = "atms",source = "atms")
    BranchDto modelToDto(Branch branch);

    @InheritInverseConfiguration
    Branch dtoToModel(BranchDto branchDto);

    @Mapping(target = "id", ignore = true)
    void createOrUpdateEntity(@MappingTarget Branch entity, BranchDto dto);


}
