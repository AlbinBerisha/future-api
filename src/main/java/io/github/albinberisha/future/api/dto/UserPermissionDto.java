package io.github.albinberisha.future.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.github.albinberisha.future.api.entity.enums.Scope;

/**
 * @author Albin Berisha
 *
 */
@JsonInclude(Include.NON_NULL)
public class UserPermissionDto {
	private String name;
	private List<Scope> scopes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Scope> getScopes() {
		return scopes;
	}

	public void setScopes(List<Scope> scopes) {
		this.scopes = scopes;
	}
}
