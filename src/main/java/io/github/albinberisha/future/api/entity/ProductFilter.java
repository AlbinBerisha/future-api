package io.github.albinberisha.future.api.entity;

import java.util.HashSet;
import java.util.Set;

import io.github.albinberisha.future.api.entity.embeddable.ProductFilterTranslations;
import io.github.albinberisha.future.api.entity.enums.ProductFilterType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@NamedEntityGraph(name = "ProductFilter.withAll", includeAllAttributes = true)
@Entity
@Table(name = "product_filter")
public class ProductFilter {
	@Id
	@Column(name = "id", length = 36, nullable = false)
	private String id;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "product_filter_translations", joinColumns = @JoinColumn(name = "product_filter_id"), uniqueConstraints = @UniqueConstraint(columnNames = { "product_filter_id", "language" }))
	private Set<ProductFilterTranslations> translations = new HashSet<>();
	@Enumerated(EnumType.STRING)
	@Column(name = "type", length = 16, nullable = false)
	private ProductFilterType type;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_category_id", nullable = false)
	private ProductCategory productCategory;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<ProductFilterTranslations> getTranslations() {
		return translations;
	}

	public void setTranslations(Set<ProductFilterTranslations> translations) {
		this.translations = translations;
	}

	public ProductFilterType getType() {
		return type;
	}

	public void setType(ProductFilterType type) {
		this.type = type;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
}
