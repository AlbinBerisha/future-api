package io.github.albinberisha.future.api.dto;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import jakarta.validation.Valid;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public class ProductUpdateRequest {
	private Map<String, String> names;
	private Map<String, String> descriptions;
	private UUID categoryId;
	private Collection<UUID> imageIds;
	private Collection<@Valid ProductVariantUpdateRequest> variants;
	private Collection<UUID> storeIds;

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

	public Collection<ProductVariantUpdateRequest> getVariants() {
		return variants;
	}

	public void setVariants(Collection<ProductVariantUpdateRequest> variants) {
		this.variants = variants;
	}

	public Collection<UUID> getStoreIds() {
		return storeIds;
	}

	public void setStoreIds(Collection<UUID> storeIds) {
		this.storeIds = storeIds;
	}
}
