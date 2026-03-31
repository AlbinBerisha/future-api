package io.github.albinberisha.future.api.service;

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

import io.github.albinberisha.future.api.domain.ProductCategory;
import io.github.albinberisha.future.api.domain.ProductFilter;
import io.github.albinberisha.future.api.domain.embeddable.ProductFilterTranslations;
import io.github.albinberisha.future.api.domain.enums.Language;
import io.github.albinberisha.future.api.dto.ProductFilterCreateDto;
import io.github.albinberisha.future.api.dto.ProductFilterUpdateDto;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.repository.ProductFilterRepository;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Service
public class ProductFilterService {
	private static final String PRODUCT_FILTER_WITH_ALL = "ProductFilter.withAll";
	@Autowired
	private ProductFilterRepository productFilterRepository;
	@Autowired
	private ProductCategoryService productCategoryService;

	public ProductFilter save(@Valid @NotNull ProductFilterCreateDto productFilterCreateDto) {
		ProductFilter productFilter = new ProductFilter();
		productFilter.setId(UUID.randomUUID().toString());
		ProductCategory productCategory = productCategoryService.findById(productFilterCreateDto.getProductCategoryId()).orElseThrow(() -> new ApiException("Product category not found"));
		productFilter.setProductCategory(productCategory);
		productFilter.setTranslations(productFilterCreateDto.getNames().entrySet().stream().filter(entry -> StringUtils.isNotBlank(entry.getValue())).map(name -> {
			ProductFilterTranslations pct = new ProductFilterTranslations();
			pct.setLanguage(Language.getByCode(name.getKey()));
			pct.setName(name.getValue());
			return pct;
		}).collect(Collectors.toSet()));
		productFilter.setType(productFilterCreateDto.getType());
		return productFilterRepository.save(productFilter);
	}

	public ProductFilter update(@NotBlank String id, @Valid @NotNull ProductFilterUpdateDto productFilterUpdateDto) {
		ProductFilter productFilter = productFilterRepository.findById(id)
				.orElseThrow(() -> new ApiException("Product filter not found"));
		productFilter.setTranslations(productFilterUpdateDto.getNames().entrySet().stream().filter(entry -> StringUtils.isNotBlank(entry.getValue())).map(name -> {
			ProductFilterTranslations pct = new ProductFilterTranslations();
			pct.setLanguage(Language.getByCode(name.getKey()));
			pct.setName(name.getValue());
			return pct;
		}).collect(Collectors.toSet()));
		return productFilterRepository.save(productFilter);
	}

	public Set<ProductFilter> findByProductCategoryId(@NotBlank String productCategoryId) {
		return productFilterRepository.findByProductCategoryId(productCategoryId, PRODUCT_FILTER_WITH_ALL);
	}

	public Optional<ProductFilter> findById(@NotBlank String id) {
		return productFilterRepository.findById(id, PRODUCT_FILTER_WITH_ALL);
	}

	public void deleteById(@NotBlank String id) {
		productFilterRepository.deleteById(id);
	}
}
