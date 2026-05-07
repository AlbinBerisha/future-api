package io.github.albinberisha.future.api.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.github.albinberisha.future.api.dto.ProductCategoryCreateRequest;
import io.github.albinberisha.future.api.dto.ProductCategoryDto;
import io.github.albinberisha.future.api.entity.ProductCategory;
import io.github.albinberisha.future.api.entity.ProductFilter;
import io.github.albinberisha.future.api.entity.embeddable.ProductCategoryTranslations;
import io.github.albinberisha.future.api.entity.embeddable.ProductFilterTranslations;
import io.github.albinberisha.future.api.entity.enums.Language;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.repository.ProductCategoryRepository;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Service
@Validated
public class ProductCategoryService {
	private static final String PRODUCT_CATEGORY_WITH_ALL = "ProductCategory.withAll";
	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	public Optional<ProductCategory> findById(@NotNull UUID id) {
		return productCategoryRepository.findById(id, PRODUCT_CATEGORY_WITH_ALL);
	}

	public List<ProductCategory> findAll() {
		return productCategoryRepository.findAll(PRODUCT_CATEGORY_WITH_ALL);
	}

	public Optional<ProductCategory> findByName(@NotBlank String name) {
		return productCategoryRepository.findByName(name, PRODUCT_CATEGORY_WITH_ALL);
	}

	public ProductCategory save(@Valid @NotNull ProductCategoryCreateRequest productCategoryCreateRequest) {
		ProductCategory category = new ProductCategory();
		category.setId(UUID.randomUUID());
		category.setTranslations(productCategoryCreateRequest.getNames().entrySet().stream().filter(entry -> StringUtils.isNotBlank(entry.getValue())).map(name -> {
			ProductCategoryTranslations pct = new ProductCategoryTranslations();
			Language language = Language.getByCode(name.getKey());
			pct.setLanguage(language);
			pct.setName(name.getValue());
			if (language == Language.ENGLISH)
				category.setName(name.getValue());
			return pct;
		}).collect(Collectors.toSet()));
		if (productCategoryCreateRequest.getFilters() != null) {
			category.setFilters(productCategoryCreateRequest.getFilters().stream().map(productFilterDto -> {
				ProductFilter productFilter = new ProductFilter();
				productFilter.setId(UUID.randomUUID());
				productFilter.setTranslations(productFilterDto.getNames().entrySet().stream().filter(entry -> StringUtils.isNotBlank(entry.getValue())).map(name -> {
					ProductFilterTranslations pft = new ProductFilterTranslations();
					pft.setLanguage(Language.getByCode(name.getKey()));
					pft.setName(name.getValue());
					return pft;
				}).collect(Collectors.toSet()));
				productFilter.setProductCategory(category);
				productFilter.setType(productFilterDto.getType());
				return productFilter;
			}).collect(Collectors.toSet()));
		}
		ProductCategory parentCategory = Optional.ofNullable(productCategoryCreateRequest.getParentCategoryId())
				.map(productCategoryId -> productCategoryRepository.findById(productCategoryId)
						.orElseThrow(() -> new ApiException("Parent product category not found")))
				.orElse(null);
		category.setParentCategory(parentCategory);
		return productCategoryRepository.save(category);
	}

	public void deleteById(@NotNull UUID id) {
		productCategoryRepository.deleteById(id);
	}

	public ProductCategory update(@NotNull UUID id, @Valid @NotNull ProductCategoryDto productCategoryRequest) {
		ProductCategory category = productCategoryRepository.findById(id).orElseThrow(() -> new ApiException("Product category not found"));
		category.setTranslations(productCategoryRequest.getNames().entrySet().stream().filter(entry -> StringUtils.isNotBlank(entry.getValue())).map(name -> {
			ProductCategoryTranslations pct = new ProductCategoryTranslations();
			pct.setLanguage(Language.getByCode(name.getKey()));
			pct.setName(name.getValue());
			return pct;
		}).collect(Collectors.toSet()));
		if (productCategoryRequest.getFilters() != null) {
			category.setFilters(productCategoryRequest.getFilters().stream().map(productFilterDto -> {
				ProductFilter productFilter = new ProductFilter();
				productFilter.setId(UUID.randomUUID());
				productFilter.setTranslations(productFilterDto.getNames().entrySet().stream().filter(entry -> StringUtils.isNotBlank(entry.getValue())).map(name -> {
					ProductFilterTranslations pft = new ProductFilterTranslations();
					pft.setLanguage(Language.getByCode(name.getKey()));
					pft.setName(name.getValue());
					return pft;
				}).collect(Collectors.toSet()));
				productFilter.setProductCategory(category);
				productFilter.setType(productFilterDto.getType());
				return productFilter;
			}).collect(Collectors.toSet()));
		}
		return productCategoryRepository.save(category);
	}

	@Transactional
	public Set<ProductCategory> getSubCategories(@NotNull ProductCategory productCategory) {
		return productCategory.getSubCategories();
	}

	@Transactional
	public ProductCategory getParentCategory(@NotNull ProductCategory productCategory) {
		return productCategory.getParentCategory();
	}

	@Transactional
	public Set<ProductFilter> getFilters(@NotNull ProductCategory productCategory) {
		return productCategory.getFilters();
	}
}
