package io.github.albinberisha.future.api.service;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.albinberisha.future.api.domain.Merchant;
import io.github.albinberisha.future.api.domain.User;
import io.github.albinberisha.future.api.domain.UserRole;
import io.github.albinberisha.future.api.domain.enums.Scope;
import io.github.albinberisha.future.api.dto.UserCreateDto;
import io.github.albinberisha.future.api.dto.UserUpdateDto;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.mapper.ObjectMapper;
import io.github.albinberisha.future.api.repository.UserRepository;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Service
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

	public Optional<User> findById(@NotBlank String id) {
		return userRepository.findById(id, USER_WITH_ALL);
	}

	@Transactional
	public User save(@Valid @NotNull UserCreateDto userCreateDto, Scope scope, Merchant merchant) {
		User user = objectMapper.toUser(userCreateDto);
		UserRole role = userRoleService.findById(userCreateDto.getRoleId())
				.orElseThrow(() -> new ApiException("User role not found"));
		user.setRole(role);
		if (scope == Scope.MERCHANT)
			user.setMerchant(merchant);
		return userRepository.save(user);
	}

	@Transactional
	public User update(String id, @Valid UserUpdateDto userUpdateDto, User authenticatedUser) {
		User user = null;
		if (authenticatedUser.getRole().getScope() == Scope.SYSTEM)
			user = userRepository.findById(id)
					.orElseThrow(() -> new ApiException("User not found"));
		else if (authenticatedUser.getRole().getScope() == Scope.MERCHANT)
			user = userRepository.findByMerchantAndId(authenticatedUser.getMerchant(), id, USER_WITH_ALL)
					.orElseThrow(() -> new ApiException("User not found"));
		else
			throw new ApiException("Operation out of user scope");
		if (StringUtils.isNotBlank(userUpdateDto.getEmail()))
			user.setEmail(userUpdateDto.getEmail());
		if (StringUtils.isNotBlank(userUpdateDto.getUsername()))
			user.setUsername(userUpdateDto.getUsername());
		if (StringUtils.isNotBlank(userUpdateDto.getPassword()))
			user.setPassword(userUpdateDto.getPassword());
		if (StringUtils.isNotBlank(userUpdateDto.getFirstName()))
			user.setFirstName(userUpdateDto.getFirstName());
		if (StringUtils.isNotBlank(userUpdateDto.getLastName()))
			user.setLastName(userUpdateDto.getLastName());
		if (StringUtils.isNotBlank(userUpdateDto.getRoleId())) {
			UserRole role = userRoleService.findById(userUpdateDto.getRoleId())
					.orElseThrow(() -> new ApiException("User role not found"));
			user.setRole(role);
		}
		if (userUpdateDto.getEnabled() != null)
			user.setEnabled(BooleanUtils.isNotFalse(userUpdateDto.getEnabled()));
		return userRepository.save(user);
	}

	public void deleteById(@NotBlank String id) {
		userRepository.deleteById(id);
	}

	public List<User> findByMerchant(Merchant merchant) {
		return userRepository.findByMerchant(merchant, USER_WITH_ALL);
	}

	public Optional<User> findByMerchantAndId(@NotNull Merchant merchant, @NotBlank String id) {
		return userRepository.findByMerchantAndId(merchant, id, USER_WITH_ALL);
	}

	@Transactional
	public void deleteByMerchantAndId(@NotNull Merchant merchant, @NotBlank String id) {
		userRepository.deleteByMerchantAndId(merchant, id);
	}
}
