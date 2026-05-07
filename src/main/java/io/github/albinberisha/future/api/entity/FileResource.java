package io.github.albinberisha.future.api.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import io.github.albinberisha.future.api.entity.enums.ResourceOwnerType;
import io.github.albinberisha.future.api.entity.enums.StorageType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@NamedEntityGraph(name = "FileResource.withAll", includeAllAttributes = true)
@Entity
@Table(name = "file_resource")
public class FileResource {
	@Id
	@Column(name = "id", nullable = false)
	private UUID id;
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	@Column(name = "updated_at", updatable = false)
	private LocalDateTime updatedAt;
	@Enumerated(EnumType.STRING)
	@Column(name = "owner_type", length = 50)
	private ResourceOwnerType ownerType;
	@Column(name = "owner_id")
	private UUID ownerId;
	@Column(name = "original_filename", length = 255)
	private String originalFilename;
	@Column(name = "content_type", length = 100)
	private String contentType;
	@Column(name = "file_size")
	private Long fileSize;
	@Column(name = "path", length = 500, nullable = false)
	private String path;
	@Enumerated(EnumType.STRING)
	@Column(name = "storage_type", length = 10, nullable = false)
	private StorageType storageType;

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

	public ResourceOwnerType getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(ResourceOwnerType ownerType) {
		this.ownerType = ownerType;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public StorageType getStorageType() {
		return storageType;
	}

	public void setStorageType(StorageType storageType) {
		this.storageType = storageType;
	}

	@PrePersist
	public void handlePrePersist() {
		if (id == null)
			id = UUID.randomUUID();
		if (createdAt == null)
			createdAt = LocalDateTime.now();
	}
}
