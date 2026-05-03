package io.github.albinberisha.future.api.dto;

import java.math.BigDecimal;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@JsonInclude(Include.NON_NULL)
public class ProductVariantDto {
	private String id;
	private Collection<ProductAttributeDto> attributes;
	private BigDecimal price;
	private Integer stockQuantity;
	private Collection<FileResourceDto> images;
	private Collection<StoreDto> stores;

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

	public Collection<FileResourceDto> getImages() {
		return images;
	}

	public void setImages(Collection<FileResourceDto> images) {
		this.images = images;
	}

	public Collection<StoreDto> getStores() {
		return stores;
	}

	public void setStores(Collection<StoreDto> stores) {
		this.stores = stores;
	}
}
