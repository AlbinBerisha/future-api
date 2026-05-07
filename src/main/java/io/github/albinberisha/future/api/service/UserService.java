package io.github.albinberisha.future.api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.github.albinberisha.future.api.dto.UserCreateRequest;
import io.github.albinberisha.future.api.dto.UserUpdateRequest;
import io.github.albinberisha.future.api.entity.Merchant;
import io.github.albinberisha.future.api.entity.User;
import io.github.albinberisha.future.api.entity.UserRole;
import io.github.albinberisha.future.api.entity.enums.Scope;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.mapper.ObjectMapper;
import io.github.albinberisha.future.api.repository.UserRepository;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Service
@Validated
public class UserService {
	private static final String USER_WITH_ALL = "User.withAll";
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private ObjectMapper objectMapper;

	public List<User> findAll() {
		return userRepository.findAll(USER_WITH_ALL);
	}

	public Optional<User> findByUsername(@NotBlank String username) {
		return userRepository.findByUsername(username, USER_WITH_ALL);
	}

	public Optional<User> findById(@NotNull UUID id) {
		return userRepository.findById(id, USER_WITH_ALL);
	}

	@Transactional
	public User save(@Valid @NotNull UserCreateRequest userCreateRequest, Scope scope, Merchant merchant) {
		User user = objectMapper.toUser(userCreateRequest);
		UserRole role = userRoleService.findById(userCreateRequest.getRoleId())
				.orElseThrow(() -> new ApiException("User role not found"));
		user.setRole(role);
		if (scope == Scope.MERCHANT)
			user.setMerchant(merchant);
		return userRepository.save(user);
	}

	@Transactional
	public User update(@NotNull UUID id, @Valid UserUpdateRequest userUpdateRequest, User authenticatedUser) {
		User user = null;
		if (authenticatedUser.getRole().getScope() == Scope.SYSTEM)
			user = userRepository.findById(id)
					.orElseThrow(() -> new ApiException("User not found"));
		else if (authenticatedUser.getRole().getScope() == Scope.MERCHANT)
			user = userRepository.findByMerchantAndId(authenticatedUser.getMerchant(), id, USER_WITH_ALL)
					.orElseThrow(() -> new ApiException("User not found"));
		else
			throw new ApiException("Operation out of user scope");
		if (StringUtils.isNotBlank(userUpdateRequest.getEmail()))
			user.setEmail(userUpdateRequest.getEmail());
		if (StringUtils.isNotBlank(userUpdateRequest.getUsername()))
			user.setUsername(userUpdateRequest.getUsername());
		if (StringUtils.isNotBlank(userUpdateRequest.getPassword()))
			user.setPassword(userUpdateRequest.getPassword());
		if (StringUtils.isNotBlank(userUpdateRequest.getFirstName()))
			user.setFirstName(userUpdateRequest.getFirstName());
		if (StringUtils.isNotBlank(userUpdateRequest.getLastName()))
			user.setLastName(userUpdateRequest.getLastName());
		if (userUpdateRequest.getRoleId() != null) {
			UserRole role = userRoleService.findById(userUpdateRequest.getRoleId())
					.orElseThrow(() -> new ApiException("User role not found"));
			user.setRole(role);
		}
		if (userUpdateRequest.getEnabled() != null)
			user.setEnabled(BooleanUtils.isNotFalse(userUpdateRequest.getEnabled()));
		return userRepository.save(user);
	}

	public void deleteById(@NotNull UUID id) {
		userRepository.deleteById(id);
	}

	public List<User> findByMerchant(Merchant merchant) {
		return userRepository.findByMerchant(merchant, USER_WITH_ALL);
	}

	public Optional<User> findByMerchantAndId(@NotNull Merchant merchant, @NotNull UUID id) {
		return userRepository.findByMerchantAndId(merchant, id, USER_WITH_ALL);
	}

	@Transactional
	public void deleteByMerchantAndId(@NotNull Merchant merchant, @NotNull UUID id) {
		userRepository.deleteByMerchantAndId(merchant, id);
	}
}
