package io.github.albinberisha.future.api.dto;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public class ProductVariantUpdateDto {
	private String id;
	private Collection<ProductAttributeDto> attributes;
	private BigDecimal price;
	private Integer stockQuantity;
	private Collection<String> imageIds;
	private Collection<String> storeIds;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
