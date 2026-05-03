package io.github.albinberisha.future.api.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import io.github.albinberisha.future.api.dto.PaginatedResponse;
import io.github.albinberisha.future.api.dto.ProductCreateRequest;
import io.github.albinberisha.future.api.dto.ProductDto;
import io.github.albinberisha.future.api.dto.ProductUpdateRequest;
import io.github.albinberisha.future.api.dto.ProductVariantDto;
import io.github.albinberisha.future.api.entity.Product;
import io.github.albinberisha.future.api.entity.ProductCategory;
import io.github.albinberisha.future.api.entity.User;
import io.github.albinberisha.future.api.entity.enums.ResourceOwnerType;
import io.github.albinberisha.future.api.entity.enums.Scope;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.mapper.ObjectMapper;
import io.github.albinberisha.future.api.service.FileResourceService;
import io.github.albinberisha.future.api.service.ProductCategoryService;
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
	private ProductCategoryService productCategoryService;
	@Autowired
	private FileResourceService fileResourceService;
	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping("/home")
	public ResponseEntity<PaginatedResponse<ProductDto>> homeProducts() {
		List<Product> products = productService.findAll();
		List<ProductDto> dtos = products.stream().map(this::enrichProductDto).toList();
		PaginatedResponse<ProductDto> response = new PaginatedResponse<>();
		response.setContent(dtos);
		response.setSize(dtos.size());
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<PaginatedResponse<ProductDto>> listProducts(Authentication authentication,
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
		List<ProductDto> dtos = products.stream().map(this::enrichProductDto).toList();
		PaginatedResponse<ProductDto> response = new PaginatedResponse<>();
		response.setContent(dtos);
		response.setSize(dtos.size());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable String id) {
		Product product = productService.findById(id)
				.orElseThrow(() -> new ApiException("Product not found"));
		return ResponseEntity.ok(enrichProductDto(product));
	}

	@PreAuthorize("hasAuthority('CREATE_PRODUCT')")
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(Authentication authentication, @Valid @RequestBody ProductCreateRequest productCreateRequest) {
		User authenticatedUser = (User) authentication.getPrincipal();
		Product product = productService.save(authenticatedUser.getMerchant(), productCreateRequest);
		return new ResponseEntity<>(enrichProductDto(product), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(Authentication authentication, @PathVariable String id, @Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
		User authenticatedUser = (User) authentication.getPrincipal();
		productService.update(id, authenticatedUser.getMerchant(), productUpdateRequest);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasAuthority('DELETE_PRODUCT')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(Authentication authentication, @PathVariable String id) {
		User authenticatedUser = (User) authentication.getPrincipal();
		productService.deleteByIdAndMerchant(id, authenticatedUser.getMerchant());
		return ResponseEntity.noContent().build();
	}

	private ProductDto enrichProductDto(Product product) {
		ProductDto dto = objectMapper.toProductDto(product);
		dto.setImages(objectMapper.toFileResourceDtoList(
				fileResourceService.findByOwner(ResourceOwnerType.PRODUCT, product.getId())));
		if (CollectionUtils.isNotEmpty(dto.getVariants())) {
			for (ProductVariantDto variantDto : dto.getVariants()) {
				variantDto.setImages(objectMapper.toFileResourceDtoList(
						fileResourceService.findByOwner(ResourceOwnerType.PRODUCT_VARIANT, variantDto.getId())));
			}
		}
		return dto;
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
