package io.github.albinberisha.future.api.entity;

import java.util.HashSet;
import java.util.Set;

import io.github.albinberisha.future.api.entity.embeddable.ProductCategoryTranslations;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@NamedEntityGraph(name = "ProductCategory.withAll", includeAllAttributes = true)
@Entity
@Table(name = "product_category")
public class ProductCategory {
	@Id
	@Column(name = "id", length = 36, nullable = false)
	private String id;
	@Column(name = "name", length = 32, nullable = false, unique = true)
	private String name;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "product_category_translations", joinColumns = @JoinColumn(name = "product_category_id"), uniqueConstraints = @UniqueConstraint(columnNames = { "product_category_id", "language" }))
	private Set<ProductCategoryTranslations> translations = new HashSet<>();
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_category_id")
	private ProductCategory parentCategory;
	@OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
	private Set<ProductCategory> subCategories = new HashSet<>();
	@OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY)
	private Set<ProductFilter> filters = new HashSet<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ProductCategoryTranslations> getTranslations() {
		return translations;
	}

	public void setTranslations(Set<ProductCategoryTranslations> translations) {
		this.translations = translations;
	}

	public ProductCategory getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(ProductCategory parentCategory) {
		this.parentCategory = parentCategory;
	}

	public Set<ProductCategory> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(Set<ProductCategory> subCategories) {
		this.subCategories = subCategories;
	}

	public Set<ProductFilter> getFilters() {
		return filters;
	}

	public void setFilters(Set<ProductFilter> filters) {
		this.filters = filters;
	}
}
