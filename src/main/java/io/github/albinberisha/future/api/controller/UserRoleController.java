package io.github.albinberisha.future.api.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.albinberisha.future.api.dto.PaginatedResponse;
import io.github.albinberisha.future.api.dto.UserRoleCreateRequest;
import io.github.albinberisha.future.api.dto.UserRoleDto;
import io.github.albinberisha.future.api.dto.UserRoleUpdateRequest;
import io.github.albinberisha.future.api.entity.User;
import io.github.albinberisha.future.api.entity.UserRole;
import io.github.albinberisha.future.api.entity.enums.Scope;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.mapper.ObjectMapper;
import io.github.albinberisha.future.api.service.UserRoleService;

/**
 * @author Albin Berisha
 *
 */
@RequestMapping("/api/user-roles")
@RestController
public class UserRoleController {
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private ObjectMapper objectMapper;

	@PreAuthorize("hasAuthority('VIEW_USER_ROLE')")
	@GetMapping
	public ResponseEntity<PaginatedResponse<UserRoleDto>> listUserRoles(Authentication authentication, @RequestParam(required = false) Scope scope) {
		User authenticatedUser = (User) authentication.getPrincipal();
		if (authenticatedUser.getRole().getScope() == Scope.MERCHANT && scope == Scope.SYSTEM)
			throw new AuthorizationServiceException("Not authorized");
		List<UserRole> roles = null;
		if (authenticatedUser.getRole().getScope() == Scope.MERCHANT)
			roles = userRoleService.findByMerchantAndScope(authenticatedUser.getMerchant(), Scope.MERCHANT);
		else if (authenticatedUser.getRole().getScope() == Scope.SYSTEM)
			roles = scope == null ? userRoleService.findByMerchant(null) : userRoleService.findByMerchantAndScope(null, scope);
		PaginatedResponse<UserRoleDto> response = new PaginatedResponse<>();
		response.setContent(objectMapper.toUserRoleDtoList(roles));
		response.setSize(roles.size());
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAuthority('VIEW_USER_ROLE')")
	@GetMapping("/{id}")
	public ResponseEntity<UserRoleDto> getUserRole(Authentication authentication, @PathVariable UUID id) {
		User authenticatedUser = (User) authentication.getPrincipal();
		UserRole role = userRoleService.findByIdAndMerchant(id, authenticatedUser.getMerchant())
				.orElseThrow(() -> new ApiException("User role not found"));
		return ResponseEntity.ok(objectMapper.toUserRoleDto(role));
	}

	@PreAuthorize("hasAuthority('CREATE_USER_ROLE')")
	@PostMapping
	public ResponseEntity<UserRoleDto> createUserRole(Authentication authentication, @Valid @RequestBody UserRoleCreateRequest userRoleCreationRequest) {
		User authenticatedUser = (User) authentication.getPrincipal();
		UserRole role = userRoleService.save(authenticatedUser.getMerchant(), userRoleCreationRequest);
		return new ResponseEntity<>(objectMapper.toUserRoleDto(role), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('UPDATE_USER_ROLE')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUserRole(Authentication authentication, @PathVariable UUID id, @Valid @RequestBody UserRoleUpdateRequest userRoleUpdatingRequest) {
		User authenticatedUser = (User) authentication.getPrincipal();
		userRoleService.update(id, authenticatedUser.getMerchant(), userRoleUpdatingRequest);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasAuthority('DELETE_USER_ROLE')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserRole(Authentication authentication, @PathVariable UUID id) {
		User authenticatedUser = (User) authentication.getPrincipal();
		userRoleService.deleteByIdAndMerchant(id, authenticatedUser.getMerchant());
		return ResponseEntity.noContent().build();
	}
}
