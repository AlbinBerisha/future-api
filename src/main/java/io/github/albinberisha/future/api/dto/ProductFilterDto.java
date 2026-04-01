package io.github.albinberisha.future.api.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.github.albinberisha.future.api.entity.enums.ProductFilterType;

/**
 * @author Albin Berisha <albin1999915@gmail.com>
 *
 */
@JsonInclude(Include.NON_NULL)
public class ProductFilterDto {
	private String id;
	private String productCategoryId;
	private Map<String, String> names;
	private ProductFilterType type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
