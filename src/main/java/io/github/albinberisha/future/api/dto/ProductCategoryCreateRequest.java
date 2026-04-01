package io.github.albinberisha.future.api.dto;

import java.util.Collection;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public class ProductCategoryCreateRequest {
	private String parentCategoryId;
	@NotEmpty
	private Map<String, String> names;
	private Collection<@Valid ProductFilterDto> filters;

	public String getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public Map<String, String> getNames() {
		return names;
	}

	public void setNames(Map<String, String> names) {
		this.names = names;
	}

	public Collection<ProductFilterDto> getFilters() {
		return filters;
	}

	public void setFilters(Collection<ProductFilterDto> filters) {
		this.filters = filters;
	}
}
