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
	@Column(name = "id", length = 36, nullable = false)
	private String id;
	@Enumerated(EnumType.STRING)
	@Column(name = "owner_type", length = 50)
	private ResourceOwnerType ownerType;
	@Column(name = "owner_id", length = 36)
	private String ownerId;
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
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ResourceOwnerType getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(ResourceOwnerType ownerType) {
		this.ownerType = ownerType;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@PrePersist
	public void handlePrePersist() {
		if (id == null)
			id = UUID.randomUUID().toString();
		if (createdAt == null)
			createdAt = LocalDateTime.now();
	}
}
