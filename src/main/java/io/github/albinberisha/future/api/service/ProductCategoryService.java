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

import io.github.albinberisha.future.api.domain.ProductCategory;
import io.github.albinberisha.future.api.domain.ProductFilter;
import io.github.albinberisha.future.api.domain.embeddable.ProductCategoryTranslations;
import io.github.albinberisha.future.api.domain.embeddable.ProductFilterTranslations;
import io.github.albinberisha.future.api.domain.enums.Language;
import io.github.albinberisha.future.api.dto.ProductCategoryCreateDto;
import io.github.albinberisha.future.api.dto.ProductCategoryDto;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.repository.ProductCategoryRepository;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Service
public class ProductCategoryService {
	private static final String PRODUCT_CATEGORY_WITH_ALL = "ProductCategory.withAll";
	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	public Optional<ProductCategory> findById(@NotBlank String id) {
		return productCategoryRepository.findById(id, PRODUCT_CATEGORY_WITH_ALL);
	}

	public List<ProductCategory> findAll() {
		return productCategoryRepository.findAll(PRODUCT_CATEGORY_WITH_ALL);
	}

	public Optional<ProductCategory> findByName(@NotBlank String name) {
		return productCategoryRepository.findByName(name, PRODUCT_CATEGORY_WITH_ALL);
	}

	public ProductCategory save(@Valid @NotNull ProductCategoryCreateDto productCategoryCreateDto) {
		ProductCategory category = new ProductCategory();
		category.setId(UUID.randomUUID().toString());
		category.setTranslations(productCategoryCreateDto.getNames().entrySet().stream().filter(entry -> StringUtils.isNotBlank(entry.getValue())).map(name -> {
			ProductCategoryTranslations pct = new ProductCategoryTranslations();
			Language language = Language.getByCode(name.getKey());
			pct.setLanguage(language);
			pct.setName(name.getValue());
			if (language == Language.ENGLISH)
				category.setName(name.getValue());
			return pct;
		}).collect(Collectors.toSet()));
		if (productCategoryCreateDto.getFilters() != null) {
			category.setFilters(productCategoryCreateDto.getFilters().stream().map(productFilterDto -> {
				ProductFilter productFilter = new ProductFilter();
				productFilter.setId(UUID.randomUUID().toString());
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
		ProductCategory parentCategory = Optional.ofNullable(productCategoryCreateDto.getParentCategoryId())
				.filter(StringUtils::isNotBlank)
				.map(productCategoryId -> productCategoryRepository.findById(productCategoryId)
						.orElseThrow(() -> new ApiException("Parent product category not found")))
				.orElse(null);
		category.setParentCategory(parentCategory);
		return productCategoryRepository.save(category);
	}

	public void deleteById(@NotBlank String id) {
		productCategoryRepository.deleteById(id);
	}

	public ProductCategory update(@NotBlank String id, @Valid @NotNull ProductCategoryDto productCategoryDto) {
		ProductCategory category = productCategoryRepository.findById(id).orElseThrow(() -> new ApiException("Product category not found"));
		category.setTranslations(productCategoryDto.getNames().entrySet().stream().filter(entry -> StringUtils.isNotBlank(entry.getValue())).map(name -> {
			ProductCategoryTranslations pct = new ProductCategoryTranslations();
			pct.setLanguage(Language.getByCode(name.getKey()));
			pct.setName(name.getValue());
			return pct;
		}).collect(Collectors.toSet()));
		if (productCategoryDto.getFilters() != null) {
			category.setFilters(productCategoryDto.getFilters().stream().map(productFilterDto -> {
				ProductFilter productFilter = new ProductFilter();
				productFilter.setId(UUID.randomUUID().toString());
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
