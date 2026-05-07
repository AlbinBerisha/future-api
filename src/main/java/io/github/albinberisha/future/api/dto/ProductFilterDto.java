package io.github.albinberisha.future.api.dto;

import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.github.albinberisha.future.api.entity.enums.ProductFilterType;

/**
 * @author Albin Berisha <albin1999915@gmail.com>
 *
 */
@JsonInclude(Include.NON_NULL)
public class ProductFilterDto {
	private UUID id;
	private UUID productCategoryId;
	private Map<String, String> names;
	private ProductFilterType type;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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
