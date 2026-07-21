package io.github.albinberisha.future.api.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import io.github.albinberisha.future.api.entity.FileResource;
import io.github.albinberisha.future.api.entity.enums.ResourceOwnerType;
import io.github.albinberisha.future.api.entity.enums.StorageProvider;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.repository.FileResourceRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha
 *
 */
@Service
@Validated
public class FileResourceService {
	@Autowired
	private FileResourceRepository fileResourceRepository;
	@Autowired
	private FileStorageService fileStorageService;
	@Value("${storage.provider}")
	private StorageProvider storageProvider;

	public Optional<FileResource> findById(@NotNull UUID id) {
		return fileResourceRepository.findById(id);
	}

	public Set<FileResource> findByIdIn(@NotEmpty Collection<UUID> ids) {
		return fileResourceRepository.findByIdIn(ids);
	}

	public List<FileResource> findByOwner(@NotNull ResourceOwnerType ownerType, @NotNull UUID ownerId) {
		return fileResourceRepository.findByOwnerTypeAndOwnerId(ownerType, ownerId);
	}

	public FileResource upload(@NotNull MultipartFile file) {
		try {
			String path = "uploads/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
			String storedPath = fileStorageService.upload(path, file);
			FileResource resource = new FileResource();
			resource.setPath(storedPath);
			resource.setStorageProvider(storageProvider);
			resource.setOriginalFilename(file.getOriginalFilename());
			resource.setContentType(file.getContentType());
			resource.setFileSize(file.getSize());
			return fileResourceRepository.save(resource);
		} catch (IOException e) {
			throw new ApiException("File upload failed");
		}
	}

	@Transactional
	public void linkToOwner(@NotEmpty Collection<UUID> resourceIds, @NotNull ResourceOwnerType ownerType,
			@NotBlank UUID ownerId) {
		Set<FileResource> resources = fileResourceRepository.findByIdIn(resourceIds);
		if (resources.size() != resourceIds.size()) {
			throw new ApiException("Some resources not found");
		}
		resources.forEach(resource -> {
			resource.setOwnerType(ownerType);
			resource.setOwnerId(ownerId);
		});
		fileResourceRepository.saveAll(resources);
	}

	public void deleteById(@NotNull UUID id) {
		FileResource resource = fileResourceRepository.findById(id)
				.orElseThrow(() -> new ApiException("Resource not found"));
		fileResourceRepository.delete(resource);
	}

	public InputStream download(@NotNull UUID id) throws IOException {
		FileResource resource = fileResourceRepository.findById(id)
				.orElseThrow(() -> new ApiException("Resource not found"));
		return fileStorageService.download(resource.getPath());
	}

	public String getDownloadUrl(@NotNull UUID id) {
		FileResource resource = fileResourceRepository.findById(id)
				.orElseThrow(() -> new ApiException("Resource not found"));
		if (resource.getStorageProvider() != StorageProvider.S3) {
			throw new ApiException("Direct URL not available for this storage type");
		}
		return fileStorageService.generateDownloadUrl(resource.getPath());
	}
}
