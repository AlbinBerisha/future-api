package io.github.albinberisha.future.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@JsonInclude(Include.NON_NULL)
public class ProductAttributeDto {
	private String id;
	@NotEmpty
	private String productFilterId;
	private ProductFilterDto productFilter;
	@NotBlank
	private String value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductFilterId() {
		return productFilterId;
	}

	public void setProductFilterId(String productFilterId) {
		this.productFilterId = productFilterId;
	}

	public ProductFilterDto getProductFilter() {
		return productFilter;
	}

	public void setProductFilter(ProductFilterDto productFilter) {
		this.productFilter = productFilter;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
