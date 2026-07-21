package io.github.albinberisha.future.api.controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.albinberisha.future.api.dto.PaginatedResponse;
import io.github.albinberisha.future.api.dto.ProductCategoryCreateRequest;
import io.github.albinberisha.future.api.dto.ProductCategoryDto;
import io.github.albinberisha.future.api.dto.ProductFilterDto;
import io.github.albinberisha.future.api.entity.ProductCategory;
import io.github.albinberisha.future.api.entity.ProductFilter;
import io.github.albinberisha.future.api.mapper.ObjectMapper;
import io.github.albinberisha.future.api.service.ProductCategoryService;
import io.github.albinberisha.future.api.service.ProductFilterService;

/**
 * @author Albin Berisha
 *
 */
@RequestMapping("/api/product-categories")
@RestController
public class ProductCategoryController {
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private ProductFilterService productFilterService;
	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping
	public ResponseEntity<PaginatedResponse<ProductCategoryDto>> listProductCategories() {
		List<ProductCategory> categories = productCategoryService.findAll();
		PaginatedResponse<ProductCategoryDto> response = new PaginatedResponse<>();
		response.setContent(objectMapper.toProductCategoryDtoList(categories));
		response.setSize(categories.size());
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAuthority('CREATE_PRODUCT_CATEGORY')")
	@PostMapping
	public ResponseEntity<ProductCategoryDto> createProductCategory(@Valid @RequestBody ProductCategoryCreateRequest productCategoryCreateRequest) {
		ProductCategory category = productCategoryService.save(productCategoryCreateRequest);
		return new ResponseEntity<>(objectMapper.toProductCategoryDto(category), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('UPDATE_PRODUCT_CATEGORY')")
	@PutMapping("/{id}")
	public ResponseEntity<ProductCategoryDto> updateProductCategory(@PathVariable UUID id, @Valid @RequestBody ProductCategoryDto productCategoryDto) {
		ProductCategory category = productCategoryService.update(id, productCategoryDto);
		return ResponseEntity.ok(objectMapper.toProductCategoryDto(category));
	}

	@PreAuthorize("hasAuthority('DELETE_PRODUCT_CATEGORY')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProductCategory(@PathVariable UUID id) {
		productCategoryService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{productCategoryId}/product-filters")
	public ResponseEntity<PaginatedResponse<ProductFilterDto>> listProductFilters(@PathVariable UUID productCategoryId) {
		Set<ProductFilter> filters = productFilterService.findByProductCategoryId(productCategoryId);
		PaginatedResponse<ProductFilterDto> response = new PaginatedResponse<>();
		response.setContent(objectMapper.toProductFilterDtoList(filters));
		response.setSize(filters.size());
		return ResponseEntity.ok(response);
	}
}
