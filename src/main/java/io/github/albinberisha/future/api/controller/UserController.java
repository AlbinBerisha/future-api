package io.github.albinberisha.future.api.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.albinberisha.future.api.dto.PaginatedResponse;
import io.github.albinberisha.future.api.dto.UserCreateRequest;
import io.github.albinberisha.future.api.dto.UserDto;
import io.github.albinberisha.future.api.dto.UserUpdateRequest;
import io.github.albinberisha.future.api.entity.User;
import io.github.albinberisha.future.api.entity.enums.Scope;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.mapper.ObjectMapper;
import io.github.albinberisha.future.api.service.UserService;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@RequestMapping("/api/users")
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private ObjectMapper objectMapper;

	@PreAuthorize("hasAuthority('VIEW_USER')")
	@GetMapping
	public ResponseEntity<PaginatedResponse<UserDto>> listUsers(Authentication authentication) {
		User authenticatedUser = (User) authentication.getPrincipal();
		Scope scope = authenticatedUser.getRole().getScope();
		List<User> users = null;
		if (scope == Scope.SYSTEM)
			users = userService.findAll();
		else if (scope == Scope.MERCHANT)
			users = userService.findByMerchant(authenticatedUser.getMerchant());
		PaginatedResponse<UserDto> response = new PaginatedResponse<>();
		response.setContent(objectMapper.toUserDtoList(users));
		response.setSize(users.size());
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAuthority('VIEW_USER')")
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUser(Authentication authentication, @PathVariable UUID id) {
		User authenticatedUser = (User) authentication.getPrincipal();
		Scope scope = authenticatedUser.getRole().getScope();
		User user = null;
		if (scope == Scope.SYSTEM)
			user = userService.findById(id).orElseThrow(() -> new ApiException("User not found"));
		else if (scope == Scope.MERCHANT)
			user = userService.findByMerchantAndId(authenticatedUser.getMerchant(), id)
					.orElseThrow(() -> new ApiException("User not found"));
		return ResponseEntity.ok(objectMapper.toUserDto(user));
	}

	@PreAuthorize("hasAuthority('CREATE_USER')")
	@PostMapping
	public ResponseEntity<UserDto> createUser(Authentication authentication, @Valid @RequestBody UserCreateRequest userCreateRequest) {
		User authenticatedUser = (User) authentication.getPrincipal();
		Scope scope = authenticatedUser.getRole().getScope();
		User user = userService.save(userCreateRequest, scope, authenticatedUser.getMerchant());
		return new ResponseEntity<>(objectMapper.toUserDto(user), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('UPDATE_USER')")
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(Authentication authentication, @PathVariable UUID id, @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
		User authenticatedUser = (User) authentication.getPrincipal();
		User user = userService.update(id, userUpdateRequest, authenticatedUser);
		return ResponseEntity.ok(objectMapper.toUserDto(user));
	}

	@PreAuthorize("hasAuthority('DELETE_USER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(Authentication authentication, @PathVariable UUID id) {
		User authenticatedUser = (User) authentication.getPrincipal();
		if (authenticatedUser.getId().equals(id))
			throw new ApiException("User cannot be deleted");
		Scope scope = authenticatedUser.getRole().getScope();
		if (scope == Scope.SYSTEM)
			userService.deleteById(id);
		else if (scope == Scope.MERCHANT)
			userService.deleteByMerchantAndId(authenticatedUser.getMerchant(), id);
		return ResponseEntity.noContent().build();
	}
}
