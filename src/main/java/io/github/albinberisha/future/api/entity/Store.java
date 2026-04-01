package io.github.albinberisha.future.api.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@NamedEntityGraph(name = "Store.withAll", includeAllAttributes = true)
@NamedEntityGraph(name = "Store.basic")
@Entity
@Table(name = "store")
public class Store {
	@Id
	@Column(name = "id", length = 36, nullable = false)
	private String id;
	@Column(name = "name", length = 32, nullable = false)
	private String name;
	@Column(name = "description", length = 255, nullable = false)
	private String description;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "merchant_id", nullable = false)
	private Merchant merchant;
	@ManyToMany(mappedBy = "stores", fetch = FetchType.LAZY)
	private Set<ProductVariant> productVariants = new HashSet<>();

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Set<ProductVariant> getProductVariants() {
		return productVariants;
	}

	public void setProductVariants(Set<ProductVariant> productVariants) {
		this.productVariants = productVariants;
	}

	@PrePersist
	public void handlePrePersist() {
		if (id == null)
			id = UUID.randomUUID().toString();
	}
}
