package io.github.albinberisha.future.api.dto;

import java.util.Set;

import io.github.albinberisha.future.api.entity.enums.UserPermission;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha
 *
 */
public class UserRoleUpdateRequest {
	@NotNull
	private Set<UserPermission> permissions;

	public Set<UserPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<UserPermission> permissions) {
		this.permissions = permissions;
	}
}
