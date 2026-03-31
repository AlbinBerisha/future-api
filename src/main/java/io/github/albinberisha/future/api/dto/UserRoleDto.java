package io.github.albinberisha.future.api.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.github.albinberisha.future.api.domain.enums.Scope;
import io.github.albinberisha.future.api.domain.enums.UserPermission;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@JsonInclude(Include.NON_NULL)
public class UserRoleDto {
	private String id;
	private String name;
	private Scope scope;
	private Set<UserPermission> permissions;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public Set<UserPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<UserPermission> permissions) {
		this.permissions = permissions;
	}
}
