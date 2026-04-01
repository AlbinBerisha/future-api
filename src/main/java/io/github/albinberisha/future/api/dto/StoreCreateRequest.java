package io.github.albinberisha.future.api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public class StoreCreateRequest {
	@NotBlank
	private String name;
	@NotBlank
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
