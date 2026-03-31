package io.github.albinberisha.future.api.dto;

import java.math.BigDecimal;
import java.util.Collection;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public class ProductVariantCreateDto {
	private Collection<@Valid ProductAttributeDto> attributes;
	@NotNull
	private BigDecimal price;
	@NotNull
	private Integer stockQuantity;
	private Collection<String> imageIds;
	private Collection<String> storeIds;

	public Collection<ProductAttributeDto> getAttributes() {
		return attributes;
	}

	public void setAttributes(Collection<ProductAttributeDto> attributes) {
		this.attributes = attributes;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public Collection<String> getImageIds() {
		return imageIds;
	}

	public void setImageIds(Collection<String> imageIds) {
		this.imageIds = imageIds;
	}

	public Collection<String> getStoreIds() {
		return storeIds;
	}

	public void setStoreIds(Collection<String> storeIds) {
		this.storeIds = storeIds;
	}
}
