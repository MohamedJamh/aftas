package com.aftas.domain.mapper;

import com.aftas.domain.dto.request.user.SaveUserRequestDto;
import com.aftas.domain.dto.request.user.UserRequestDto;
import com.aftas.domain.dto.response.user.UserResponseDto;
import com.aftas.domain.entities.PermissionGroup;
import com.aftas.domain.entities.Role;
import com.aftas.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(source = "roles", target = "rolePermissions", qualifiedByName = "rolesToPermissions")
    @Mapping(source = "permissionGroups", target = "permissionGroupPermissions", qualifiedByName = "permissionGroupsToPermissions")
    UserResponseDto toDto(User user);
    User toUser(UserRequestDto userDto);
    User toUser(SaveUserRequestDto userDto);


    @Named("rolesToPermissions")
    default Set<String> rolesToPermissions(Set<Role> roles) {
        if (roles == null) {
            return Set.of();
        }
        Set<String> rolesPermissions = new HashSet<>();
        roles.forEach(role ->
            role.getPermissions().forEach(permission -> {
                rolesPermissions.add(permission.getSubject() + ":" + permission.getAction());
           })
        );
        return rolesPermissions;
    }

    @Named("permissionGroupsToPermissions")
    default Set<String> groupPermissionsToPermissions(Set<PermissionGroup> permissionGroups) {
        if (permissionGroups == null) {
            return Set.of();
        }
        Set<String> groupPermissions = new HashSet<>();
        permissionGroups.forEach(group ->
            group.getPermissions().forEach(permission -> {
                groupPermissions.add(permission.getSubject() + ":" + permission.getAction());
            })
        );
        return groupPermissions;
    }

}
