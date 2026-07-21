package io.github.albinberisha.future.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.albinberisha.future.api.dto.PaginatedResponse;
import io.github.albinberisha.future.api.dto.UserPermissionDto;
import io.github.albinberisha.future.api.entity.User;
import io.github.albinberisha.future.api.entity.enums.Scope;
import io.github.albinberisha.future.api.entity.enums.UserPermission;

/**
 * @author Albin Berisha
 *
 */
@RequestMapping("/api/user-permissions")
@RestController
public class UserPermissionController {
	@PreAuthorize("hasAuthority('VIEW_USER_ROLE')")
	@GetMapping
	public ResponseEntity<PaginatedResponse<UserPermissionDto>> listUserPermissions(Authentication authentication) {
		User authenticatedUser = (User) authentication.getPrincipal();
		Scope scope = authenticatedUser.getRole().getScope();

		List<UserPermissionDto> permissions;
		if (scope == Scope.SYSTEM) {
			permissions = Arrays.stream(UserPermission.values()).map(this::toDto).collect(Collectors.toList());
		} else {
			permissions = Arrays.stream(UserPermission.values())
					.filter(p -> p.getScopes().contains(Scope.MERCHANT))
					.map(this::toDto)
					.collect(Collectors.toList());
		}

		PaginatedResponse<UserPermissionDto> response = new PaginatedResponse<>();
		response.setContent(permissions);
		response.setSize(permissions.size());
		return ResponseEntity.ok(response);
	}

	private UserPermissionDto toDto(UserPermission permission) {
		UserPermissionDto dto = new UserPermissionDto();
		dto.setName(permission.name());
		dto.setScopes(permission.getScopes());
		return dto;
	}
}
