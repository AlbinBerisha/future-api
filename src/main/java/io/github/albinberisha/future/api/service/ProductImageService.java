package io.github.albinberisha.future.api.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import io.github.albinberisha.future.api.entity.ProductImage;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.repository.ProductImageRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Service
@Validated
public class ProductImageService {
	@Autowired
	private ProductImageRepository productImageRepository;
	@Autowired
	private FileStorageService fileStorageService;

	public Optional<ProductImage> findById(@NotBlank String id) {
		return productImageRepository.findById(id);
	}

	public Set<ProductImage> findByIdIn(@NotEmpty Collection<String> ids) {
		return productImageRepository.findByIdIn(ids);
	}

	public ProductImage save(@NotNull MultipartFile file) {
		try {
			String path = "products/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
			String url = fileStorageService.upload(path, file);
			ProductImage productImage = new ProductImage();
			productImage.setId(UUID.randomUUID().toString());
			productImage.setUrl(url);
			return productImageRepository.save(productImage);
		} catch (IOException e) {
			throw new ApiException("File upload failed");
		}
	}
}
