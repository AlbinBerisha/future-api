package io.github.albinberisha.future.api.dto;

import java.util.Collection;
import java.util.Map;

import jakarta.validation.Valid;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public class ProductUpdateRequest {
	private Map<String, String> names;
	private Map<String, String> descriptions;
	private String categoryId;
	private Collection<String> imageIds;
	private Collection<@Valid ProductVariantUpdateRequest> variants;
	private Collection<String> storeIds;

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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Collection<String> getImageIds() {
		return imageIds;
	}

	public void setImageIds(Collection<String> imageIds) {
		this.imageIds = imageIds;
	}

	public Collection<ProductVariantUpdateRequest> getVariants() {
		return variants;
	}

	public void setVariants(Collection<ProductVariantUpdateRequest> variants) {
		this.variants = variants;
	}

	public Collection<String> getStoreIds() {
		return storeIds;
	}

	public void setStoreIds(Collection<String> storeIds) {
		this.storeIds = storeIds;
	}
}
