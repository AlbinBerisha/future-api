package io.github.albinberisha.future.api.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/**
 * @author Albin Berisha
 *
 */
@NamedEntityGraph(name = "Merchant.withAll", includeAllAttributes = true)
@NamedEntityGraph(name = "Merchant.basic")
@NamedEntityGraph(name = "Merchant.withStores", attributeNodes = { @NamedAttributeNode("stores") })
@Entity
@Table(name = "merchant")
public class Merchant {
	@Id
	@Column(name = "id", nullable = false)
	private UUID id;
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	@Column(name = "updated_at", updatable = false)
	private LocalDateTime updatedAt;
	@Column(name = "name", length = 32, nullable = false)
	private String name;
	@Column(name = "description", length = 255, nullable = false)
	private String description;
	@OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY)
	private Set<Store> stores = new HashSet<>();
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "main_user_id", referencedColumnName = "id")
	private User mainUser;
	@OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<>();
	@OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY)
	private Set<UserRole> ownedUserRoles = new HashSet<>();
	@OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY)
	private Set<Product> products = new HashSet<>();

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
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

	public Set<Store> getStores() {
		return stores;
	}

	public void setStores(Set<Store> stores) {
		this.stores = stores;
	}

	public User getMainUser() {
		return mainUser;
	}

	public void setMainUser(User mainUser) {
		this.mainUser = mainUser;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<UserRole> getOwnedUserRoles() {
		return ownedUserRoles;
	}

	public void setOwnedUserRoles(Set<UserRole> ownedUserRoles) {
		this.ownedUserRoles = ownedUserRoles;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	@PrePersist
	public void handlePrePersist() {
		if (id == null)
			id = UUID.randomUUID();
	}
}
