package io.github.albinberisha.future.api.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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

import io.github.albinberisha.future.api.domain.User;
import io.github.albinberisha.future.api.domain.enums.Scope;
import io.github.albinberisha.future.api.dto.PaginatedResponseDto;
import io.github.albinberisha.future.api.dto.UserCreateDto;
import io.github.albinberisha.future.api.dto.UserDto;
import io.github.albinberisha.future.api.dto.UserUpdateDto;
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
	public ResponseEntity<PaginatedResponseDto<UserDto>> listUsers(Authentication authentication) {
		User authenticatedUser = (User) authentication.getPrincipal();
		Scope scope = authenticatedUser.getRole().getScope();
		List<User> users = null;
		if (scope == Scope.SYSTEM)
			users = userService.findAll();
		else if (scope == Scope.MERCHANT)
			users = userService.findByMerchant(authenticatedUser.getMerchant());
		PaginatedResponseDto<UserDto> response = new PaginatedResponseDto<>();
		response.setContent(objectMapper.toUserDtoList(users));
		response.setSize(users.size());
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAuthority('VIEW_USER')")
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUser(Authentication authentication, @PathVariable String id) {
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
	public ResponseEntity<UserDto> createUser(Authentication authentication, @Valid @RequestBody UserCreateDto userCreateDto) {
		User authenticatedUser = (User) authentication.getPrincipal();
		Scope scope = authenticatedUser.getRole().getScope();
		User user = userService.save(userCreateDto, scope, authenticatedUser.getMerchant());
		return new ResponseEntity<>(objectMapper.toUserDto(user), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('UPDATE_USER')")
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(Authentication authentication, @PathVariable String id, @Valid @RequestBody UserUpdateDto userUpdateDto) {
		User authenticatedUser = (User) authentication.getPrincipal();
		User user = userService.update(id, userUpdateDto, authenticatedUser);
		return ResponseEntity.ok(objectMapper.toUserDto(user));
	}

	@PreAuthorize("hasAuthority('DELETE_USER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(Authentication authentication, @PathVariable String id) {
		User authenticatedUser = (User) authentication.getPrincipal();
		if (authenticatedUser.getId().equals(id))
			throw new ApiException("User cannot be deleted");
		Scope scope = authenticatedUser.getRole().getScope();
		try {
			if (scope == Scope.SYSTEM)
				userService.deleteById(id);
			else if (scope == Scope.MERCHANT)
				userService.deleteByMerchantAndId(authenticatedUser.getMerchant(), id);
			return ResponseEntity.noContent().build();
		} catch (EmptyResultDataAccessException e) {
			throw new ApiException("User not found");
		} catch (DataIntegrityViolationException e) {
			throw new ApiException("User cannot be deleted");
		}
	}
}
