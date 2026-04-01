package io.github.albinberisha.future.api.controller;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.albinberisha.future.api.dto.PaginatedResponse;
import io.github.albinberisha.future.api.entity.User;
import io.github.albinberisha.future.api.entity.enums.Scope;
import io.github.albinberisha.future.api.entity.enums.UserPermission;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@RequestMapping("/api/user-permissions")
@RestController
public class UserPermissionController {
	@PreAuthorize("hasAuthority('VIEW_USER_ROLE')")
	@GetMapping
	public ResponseEntity<PaginatedResponse<UserPermission>> listUserPermissions(Authentication authentication) {
		User authenticatedUser = (User) authentication.getPrincipal();
		Scope scope = authenticatedUser.getRole().getScope();
		Set<UserPermission> permissions = null;
		if (scope == Scope.SYSTEM)
			permissions = Stream.of(UserPermission.values()).collect(Collectors.toSet());
		else if (scope == Scope.MERCHANT)
			permissions = authenticatedUser.getRole().getPermissions();
		PaginatedResponse<UserPermission> response = new PaginatedResponse<>();
		response.setContent(permissions);
		response.setSize(permissions.size());
		return ResponseEntity.ok(response);
	}
}
