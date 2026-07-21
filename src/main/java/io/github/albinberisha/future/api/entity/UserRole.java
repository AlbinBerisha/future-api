package io.github.albinberisha.future.api.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import io.github.albinberisha.future.api.entity.enums.Scope;
import io.github.albinberisha.future.api.entity.enums.UserPermission;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * @author Albin Berisha
 *
 */
@NamedEntityGraph(name = "UserRole.withAll", includeAllAttributes = true)
@NamedEntityGraph(name = "UserRole.basic")
@Entity
@Table(name = "user_role", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "merchant_id" }) })
public class UserRole {
	@Id
	@Column(name = "id", nullable = false)
	private UUID id;
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	@Column(name = "updated_at", updatable = false)
	private LocalDateTime updatedAt;
	@Column(name = "name", length = 32, nullable = false)
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(name = "scope", length = 16, nullable = false)
	private Scope scope;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "merchant_id", nullable = true)
	private Merchant merchant;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role_permission", joinColumns = @JoinColumn(name = "role_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "permission", length = 32, nullable = false)
	private Set<UserPermission> permissions = new HashSet<>();

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

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Set<UserPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<UserPermission> permissions) {
		this.permissions = permissions;
	}

	@PrePersist
	public void handlePrePersist() {
		if (id == null)
			id = UUID.randomUUID();
	}
}
