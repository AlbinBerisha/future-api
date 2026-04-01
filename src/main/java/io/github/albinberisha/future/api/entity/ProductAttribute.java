package io.github.albinberisha.future.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Entity
@Table(name = "product_attribute")
public class ProductAttribute {
	@Id
	@Column(name = "id", length = 36, nullable = false)
	private String id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_variant_id", nullable = false)
	private ProductVariant productVariant;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_filter_id", nullable = false)
	private ProductFilter productFilter;
	@Column(name = "value", length = 32, nullable = false)
	private String value;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_category_id")
	private ProductCategory productCategory;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ProductVariant getProductVariant() {
		return productVariant;
	}

	public void setProductVariant(ProductVariant productVariant) {
		this.productVariant = productVariant;
	}

	public ProductFilter getProductFilter() {
		return productFilter;
	}

	public void setProductFilter(ProductFilter productFilter) {
		this.productFilter = productFilter;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
}
