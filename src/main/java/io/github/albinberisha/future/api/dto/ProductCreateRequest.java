package io.github.albinberisha.future.api.dto;

import java.util.Collection;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public class ProductCreateRequest {
	@NotEmpty
	private Map<String, String> names;
	@NotEmpty
	private Map<String, String> descriptions;
	@NotBlank
	private String categoryId;
	@NotEmpty
	private Collection<String> imageIds;
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

	public Collection<ProductVariantCreateRequest> getVariants() {
		return variants;
	}

	public void setVariants(Collection<ProductVariantCreateRequest> variants) {
		this.variants = variants;
	}
}
