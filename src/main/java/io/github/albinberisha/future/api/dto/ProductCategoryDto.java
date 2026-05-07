package io.github.albinberisha.future.api.dto;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@JsonInclude(Include.NON_NULL)
public class ProductCategoryDto {
	private UUID id;
	private String name;
	private ProductCategoryDto parentCategory;
	private Map<String, String> names;
	private Collection<ProductFilterDto> filters;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductCategoryDto getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(ProductCategoryDto parentCategory) {
		this.parentCategory = parentCategory;
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
