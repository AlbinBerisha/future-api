package io.github.albinberisha.future.api.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.albinberisha.future.api.domain.ProductFilter;
import io.github.albinberisha.future.api.dto.ProductFilterCreateDto;
import io.github.albinberisha.future.api.dto.ProductFilterDto;
import io.github.albinberisha.future.api.dto.ProductFilterUpdateDto;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.mapper.ObjectMapper;
import io.github.albinberisha.future.api.service.ProductFilterService;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@RequestMapping("/api/product-filters")
@RestController
public class ProductFilterController {
	@Autowired
	private ProductFilterService productFilterService;
	@Autowired
	private ObjectMapper objectMapper;

	@PreAuthorize("hasAuthority('UPDATE_PRODUCT_CATEGORY')")
	@PostMapping
	public ResponseEntity<ProductFilterDto> createProductFilter(@Valid @RequestBody ProductFilterCreateDto productFilterCreateDto) {
		ProductFilter productFilter = productFilterService.save(productFilterCreateDto);
		return new ResponseEntity<>(objectMapper.toProductFilterDto(productFilter), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('UPDATE_PRODUCT_CATEGORY')")
	@PutMapping("/{id}")
	public ResponseEntity<ProductFilterDto> updateProductFilter(@PathVariable String id, @Valid @RequestBody ProductFilterUpdateDto productFilterUpdateDto) {
		ProductFilter productFilter = productFilterService.update(id, productFilterUpdateDto);
		return ResponseEntity.ok(objectMapper.toProductFilterDto(productFilter));
	}

	@PreAuthorize("hasAuthority('UPDATE_PRODUCT_CATEGORY')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProductFilter(@PathVariable String id) {
		try {
			productFilterService.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (EmptyResultDataAccessException e) {
			throw new ApiException("Filter not found");
		} catch (DataIntegrityViolationException e) {
			throw new ApiException("Filter cannot be deleted");
		}
	}
}
