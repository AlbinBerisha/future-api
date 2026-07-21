package io.github.albinberisha.future.api.dto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha
 *
 */
public class ProductVariantCreateRequest {
	private Collection<@Valid ProductAttributeDto> attributes;
	@NotNull
	private BigDecimal price;
	@NotNull
	private Integer stockQuantity;
	private Collection<UUID> imageIds;
	private Collection<UUID> storeIds;

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

	public Collection<UUID> getImageIds() {
		return imageIds;
	}

	public void setImageIds(Collection<UUID> imageIds) {
		this.imageIds = imageIds;
	}

	public Collection<UUID> getStoreIds() {
		return storeIds;
	}

	public void setStoreIds(Collection<UUID> storeIds) {
		this.storeIds = storeIds;
	}
}
