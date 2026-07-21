package io.github.albinberisha.future.api.dto;

import java.util.Collection;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha
 *
 */
public class MerchantCreateRequest {
	@NotBlank
	private String name;
	@NotBlank
	private String description;
	@Valid
	@NotNull
	private UserCreateRequest mainUser;
	@NotEmpty
	private Collection<@Valid StoreCreateRequest> stores;

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

	public UserCreateRequest getMainUser() {
		return mainUser;
	}

	public void setMainUser(UserCreateRequest mainUser) {
		this.mainUser = mainUser;
	}

	public Collection<StoreCreateRequest> getStores() {
		return stores;
	}

	public void setStores(Collection<StoreCreateRequest> stores) {
		this.stores = stores;
	}
}
