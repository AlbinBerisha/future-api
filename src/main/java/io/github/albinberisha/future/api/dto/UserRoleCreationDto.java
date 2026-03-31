package io.github.albinberisha.future.api.dto;

import io.github.albinberisha.future.api.domain.enums.Scope;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public class UserRoleCreationDto {
	@NotBlank
	public String name;
	private Scope scope;

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
}
