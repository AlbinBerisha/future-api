package io.github.albinberisha.future.api.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.albinberisha.future.api.domain.Product;
import io.github.albinberisha.future.api.domain.ProductCategory;
import io.github.albinberisha.future.api.domain.ProductImage;
import io.github.albinberisha.future.api.domain.User;
import io.github.albinberisha.future.api.domain.enums.Scope;
import io.github.albinberisha.future.api.dto.PaginatedResponseDto;
import io.github.albinberisha.future.api.dto.ProductCreateDto;
import io.github.albinberisha.future.api.dto.ProductDto;
import io.github.albinberisha.future.api.dto.ProductUpdateDto;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.mapper.ObjectMapper;
import io.github.albinberisha.future.api.service.ProductCategoryService;
import io.github.albinberisha.future.api.service.ProductImageService;
import io.github.albinberisha.future.api.service.ProductService;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@RequestMapping("/api/products")
@RestController
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductImageService productImageService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping("/home")
	public ResponseEntity<PaginatedResponseDto<ProductDto>> homeProducts() {
		List<Product> products = productService.findAll();
		PaginatedResponseDto<ProductDto> response = new PaginatedResponseDto<>();
		response.setContent(objectMapper.toProductDtoList(products));
		response.setSize(products.size());
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<PaginatedResponseDto<ProductDto>> listProducts(Authentication authentication,
			@RequestParam(required = false, name = "category") String categoryName) {
		List<Product> products = null;
		if (authentication == null) {
			ProductCategory category = productCategoryService.findByName(categoryName)
					.orElseThrow(() -> new ApiException("Product category not found"));
			List<String> filterCategories = prepareFilterCategories(category);
			products = productService.findByFilters(filterCategories);
		} else {
			User authenticatedUser = (User) authentication.getPrincipal();
			Scope scope = authenticatedUser.getRole().getScope();
			if (scope == Scope.SYSTEM)
				products = productService.findAll();
			else if (scope == Scope.MERCHANT)
				products = productService.findByMerchant(authenticatedUser.getMerchant());
		}
		PaginatedResponseDto<ProductDto> response = new PaginatedResponseDto<>();
		response.setContent(objectMapper.toProductDtoList(products));
		response.setSize(products.size());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable String id) {
		Product product = productService.findById(id)
				.orElseThrow(() -> new ApiException("Product not found"));
		if (product == null)
			throw new ApiException("Product not found");
		return ResponseEntity.ok(objectMapper.toProductDto(product));
	}

	@PreAuthorize("hasAuthority('CREATE_PRODUCT')")
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(Authentication authentication, @Valid @RequestBody ProductCreateDto productCreateDto) {
		User authenticatedUser = (User) authentication.getPrincipal();
		Product product = productService.save(authenticatedUser.getMerchant(), productCreateDto);
		return new ResponseEntity<>(objectMapper.toProductDto(product), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(Authentication authentication, @PathVariable String id, @Valid @RequestBody ProductUpdateDto productUpdateDto) {
		User authenticatedUser = (User) authentication.getPrincipal();
		productService.update(id, authenticatedUser.getMerchant(), productUpdateDto);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasAuthority('DELETE_PRODUCT')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(Authentication authentication, @PathVariable String id) {
		User authenticatedUser = (User) authentication.getPrincipal();
		try {
			productService.deleteByIdAndMerchant(id, authenticatedUser.getMerchant());
		} catch (EmptyResultDataAccessException e) {
			throw new ApiException("Product not found");
		}
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
	@PostMapping("/images")
	public ResponseEntity<?> uploadImage(@RequestParam MultipartFile image) {
		ProductImage productImage = productImageService.save(image);
		return new ResponseEntity<>(objectMapper.toProductImageDto(productImage), HttpStatus.CREATED);
	}

	private static List<String> prepareFilterCategories(ProductCategory category) {
		List<String> filterCategories = new ArrayList<>();
		filterCategories.add(category.getName());
		if (CollectionUtils.isNotEmpty(category.getSubCategories()))
			category.getSubCategories().stream()
					.map(ProductController::prepareFilterCategories)
					.forEach(filterCategories::addAll);
		return filterCategories;
	}
}
