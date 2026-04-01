package io.github.albinberisha.future.api.mapper;

import java.util.List;

import io.github.albinberisha.future.api.dto.UserRoleDto;
import io.github.albinberisha.future.api.entity.UserRole;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface UserRoleMapper {

	UserRoleDto toUserRoleDto(UserRole userRole);

	List<UserRoleDto> toUserRoleDtoList(List<UserRole> userRoles);

}
