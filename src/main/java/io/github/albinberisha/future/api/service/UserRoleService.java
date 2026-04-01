package io.github.albinberisha.future.api.service;

import java.util.List;
import java.util.Optional;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.github.albinberisha.future.api.dto.UserRoleCreateRequest;
import io.github.albinberisha.future.api.dto.UserRoleUpdateRequest;
import io.github.albinberisha.future.api.entity.Merchant;
import io.github.albinberisha.future.api.entity.UserRole;
import io.github.albinberisha.future.api.entity.enums.Scope;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.repository.UserRoleRepository;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Service
@Validated
public class UserRoleService {
	private static final String USER_ROLE_WITH_ALL = "UserRole.withAll";
	@Autowired
	private UserRoleRepository userRoleRepository;

	public List<UserRole> findAll() {
		return userRoleRepository.findAll();
	}

	public Optional<UserRole> findById(@NotBlank String id) {
		return userRoleRepository.findById(id, USER_ROLE_WITH_ALL);
	}

	public List<UserRole> findByMerchant(@Nullable Merchant merchant) {
		return userRoleRepository.findByMerchant(merchant, USER_ROLE_WITH_ALL);
	}

	public Optional<UserRole> findByIdAndMerchant(@NotBlank String id, @NotNull Merchant merchant) {
		return userRoleRepository.findByIdAndMerchant(id, merchant, USER_ROLE_WITH_ALL);
	}

	public UserRole save(@Nullable Merchant merchant, @Valid @NotNull UserRoleCreateRequest userRoleCreationRequest) {
		UserRole role = new UserRole();
		role.setName(userRoleCreationRequest.getName());
		role.setScope(merchant != null ? Scope.MERCHANT : (userRoleCreationRequest.getScope() != null ? userRoleCreationRequest.getScope() : Scope.SYSTEM));
		role.setMerchant(merchant);
		return userRoleRepository.save(role);
	}

	@Transactional
	public UserRole update(@NotBlank String id, Merchant merchant, @Valid @NotNull UserRoleUpdateRequest userRoleUpdatingRequest) {
		UserRole role = userRoleRepository.findByIdAndMerchant(id, merchant, "UserRole.basic")
				.orElseThrow(() -> new ApiException("User role not found"));
		List<UserRole> merchantOwnedUserRoles = userRoleRepository.findByMerchant(merchant, USER_ROLE_WITH_ALL);
		if (merchant != null && merchantOwnedUserRoles.stream().noneMatch(r -> r.getId().equals(id)))
			throw new ApiException("User role not found");
		if (merchant != null && !merchant.getMainUser().getRole().getPermissions().containsAll(userRoleUpdatingRequest.getPermissions()))
			throw new ApiException("User permission not found");
		role.setPermissions(userRoleUpdatingRequest.getPermissions());
		return userRoleRepository.save(role);
	}

	public List<UserRole> findByMerchantAndScope(@NotNull Merchant merchant, @NotNull Scope scope) {
		return userRoleRepository.findByMerchantAndScope(merchant, scope, USER_ROLE_WITH_ALL);
	}

	@Transactional
	public void deleteByIdAndMerchant(@NotBlank String id, @NotNull Merchant merchant) {
		userRoleRepository.deleteByIdAndMerchant(id, merchant);
	}
}
