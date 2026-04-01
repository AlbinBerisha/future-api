package io.github.albinberisha.future.api.dto;

import java.util.Map;

import io.github.albinberisha.future.api.entity.enums.ProductFilterType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public class ProductFilterCreateRequest {
	@NotBlank
	private String productCategoryId;
	@NotEmpty
	private Map<String, String> names;
	@NotNull
	private ProductFilterType type;

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public Map<String, String> getNames() {
		return names;
	}

	public void setNames(Map<String, String> names) {
		this.names = names;
	}

	public ProductFilterType getType() {
		return type;
	}

	public void setType(ProductFilterType type) {
		this.type = type;
	}
}
