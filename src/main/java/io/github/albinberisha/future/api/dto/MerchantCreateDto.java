package io.github.albinberisha.future.api.dto;

import java.util.Collection;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public class MerchantCreateDto {
	@NotBlank
	private String name;
	@NotBlank
	private String description;
	@Valid
	@NotNull
	private UserCreateDto mainUser;
	@NotEmpty
	private Collection<@Valid StoreCreateDto> stores;

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

	public UserCreateDto getMainUser() {
		return mainUser;
	}

	public void setMainUser(UserCreateDto mainUser) {
		this.mainUser = mainUser;
	}

	public Collection<StoreCreateDto> getStores() {
		return stores;
	}

	public void setStores(Collection<StoreCreateDto> stores) {
		this.stores = stores;
	}
}
