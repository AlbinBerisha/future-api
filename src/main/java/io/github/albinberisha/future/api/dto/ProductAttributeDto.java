package io.github.albinberisha.future.api.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha
 *
 */
@JsonInclude(Include.NON_NULL)
public class ProductAttributeDto {
	private UUID id;
	@NotNull
	private UUID productFilterId;
	private ProductFilterDto productFilter;
	@NotBlank
	private String value;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getProductFilterId() {
		return productFilterId;
	}

	public void setProductFilterId(UUID productFilterId) {
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
