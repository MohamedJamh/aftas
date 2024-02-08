package com.aftas.domain.mapper;

import com.aftas.domain.dto.request.user.UserRequestDto;
import com.aftas.domain.dto.response.user.UserResponseDto;
import com.aftas.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserResponseDto toDto(User user);
    User toUser(UserRequestDto userDto);
}
