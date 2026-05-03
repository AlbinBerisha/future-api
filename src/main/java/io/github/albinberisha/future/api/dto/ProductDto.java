package io.github.albinberisha.future.api.dto;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Albin Berisha <albin.berisha@asseco-see.com>
 *
 */
@JsonInclude(Include.NON_NULL)
public class ProductDto {
	private String id;
	private Map<String, String> names;
	private Map<String, String> descriptions;
	private ProductCategoryDto category;
	private Collection<FileResourceDto> images;
	private Double rating;
	private Collection<ProductVariantDto> variants;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public ProductCategoryDto getCategory() {
		return category;
	}

	public void setCategory(ProductCategoryDto category) {
		this.category = category;
	}

	public Collection<FileResourceDto> getImages() {
		return images;
	}

	public void setImages(Collection<FileResourceDto> images) {
		this.images = images;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Collection<ProductVariantDto> getVariants() {
		return variants;
	}

	public void setVariants(Collection<ProductVariantDto> variants) {
		this.variants = variants;
	}
}
