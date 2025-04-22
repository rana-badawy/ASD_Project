package edu.miu.cs.cs489.surgeries.mapper;

import edu.miu.cs.cs489.surgeries.dto.request.RoleRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.RoleResponseDto;
import edu.miu.cs.cs489.surgeries.model.Role;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    Role RoleRequestDtoToRole(RoleRequestDto roleRequestDto);

    RoleResponseDto RoleToRoleResponseDto(Role role);

    List<RoleResponseDto> RoleToRoleResponseDtoList(List<Role> roles);
}
