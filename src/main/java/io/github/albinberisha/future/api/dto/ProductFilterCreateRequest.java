package io.github.albinberisha.future.api.dto;

import java.util.Map;
import java.util.UUID;

import io.github.albinberisha.future.api.entity.enums.ProductFilterType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha
 *
 */
public class ProductFilterCreateRequest {
	@NotNull
	private UUID productCategoryId;
	@NotEmpty
	private Map<String, String> names;
	@NotNull
	private ProductFilterType type;

	public UUID getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(UUID productCategoryId) {
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
