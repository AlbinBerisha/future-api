package io.github.albinberisha.future.api.dto;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public class ProductCreateRequest {
	@NotEmpty
	private Map<String, String> names;
	@NotEmpty
	private Map<String, String> descriptions;
	@NotNull
	private UUID categoryId;
	@NotEmpty
	private Collection<UUID> imageIds;
	@NotEmpty
	private Collection<@Valid ProductVariantCreateRequest> variants;

	public Map<String, String> getNames() {
		return names;
	}

	public void setNames(Map<String, String> names) {
		this.names = names;
	}

	public Map<String, String> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(Map<String, String> descriptions) {
		this.descriptions = descriptions;
	}

	public UUID getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(UUID categoryId) {
		this.categoryId = categoryId;
	}

	public Collection<UUID> getImageIds() {
		return imageIds;
	}

	public void setImageIds(Collection<UUID> imageIds) {
		this.imageIds = imageIds;
	}

	public Collection<ProductVariantCreateRequest> getVariants() {
		return variants;
	}

	public void setVariants(Collection<ProductVariantCreateRequest> variants) {
		this.variants = variants;
	}
}
