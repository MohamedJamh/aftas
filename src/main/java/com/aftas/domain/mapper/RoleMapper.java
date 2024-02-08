package com.aftas.domain.mapper;

import com.aftas.domain.dto.request.role.RoleRequestDto;
import com.aftas.domain.dto.response.role.RoleResponseDto;
import com.aftas.domain.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleResponseDto toDto(Role user);
    Role toRole(RoleRequestDto userDto);
}
