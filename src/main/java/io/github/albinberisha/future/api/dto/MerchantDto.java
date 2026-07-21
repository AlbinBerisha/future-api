package io.github.albinberisha.future.api.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Albin Berisha
 *
 */
@JsonInclude(Include.NON_NULL)
public class MerchantDto {
	private UUID id;
	private String name;
	private String description;
	private UserDto mainUser;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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

	public UserDto getMainUser() {
		return mainUser;
	}

	public void setMainUser(UserDto mainUser) {
		this.mainUser = mainUser;
	}
}
