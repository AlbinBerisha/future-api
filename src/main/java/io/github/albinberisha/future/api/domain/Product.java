package io.github.albinberisha.future.api.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import io.github.albinberisha.future.api.domain.embeddable.ProductTranslations;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@NamedEntityGraph(name = "Product.withAll", includeAllAttributes = true)
@Entity
@Table(name = "product")
public class Product {
	@Id
	@Column(name = "id", length = 36, nullable = false)
	private String id;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "product_translations", joinColumns = @JoinColumn(name = "product_id"), uniqueConstraints = @UniqueConstraint(columnNames = { "product_id", "language" }))
	private Set<ProductTranslations> translations = new HashSet<>();
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private ProductCategory category;
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ProductImage> images = new HashSet<>();
	@Column(name = "rating", scale = 2)
	private Double rating;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "merchant_id", nullable = false)
	private Merchant merchant;
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProductVariant> variants = new HashSet<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<ProductTranslations> getTranslations() {
		return translations;
	}

	public void setTranslations(Set<ProductTranslations> translations) {
		this.translations = translations;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public Set<ProductImage> getImages() {
		return images;
	}

	public void setImages(Set<ProductImage> images) {
		this.images = images;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Set<ProductVariant> getVariants() {
		return variants;
	}

	public void setVariants(Set<ProductVariant> variants) {
		this.variants = variants;
	}

	@PrePersist
	public void handlePrePersist() {
		if (id == null)
			id = UUID.randomUUID().toString();
	}
}
