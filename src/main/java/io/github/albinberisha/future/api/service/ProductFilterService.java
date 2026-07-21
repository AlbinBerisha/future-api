package io.github.albinberisha.future.api.service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import io.github.albinberisha.future.api.dto.ProductFilterCreateRequest;
import io.github.albinberisha.future.api.dto.ProductFilterUpdateRequest;
import io.github.albinberisha.future.api.entity.ProductCategory;
import io.github.albinberisha.future.api.entity.ProductFilter;
import io.github.albinberisha.future.api.entity.embeddable.ProductFilterTranslations;
import io.github.albinberisha.future.api.entity.enums.Language;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.repository.ProductFilterRepository;

/**
 * @author Albin Berisha
 *
 */
@Service
@Validated
public class ProductFilterService {
	private static final String PRODUCT_FILTER_WITH_ALL = "ProductFilter.withAll";
	@Autowired
	private ProductFilterRepository productFilterRepository;
	@Autowired
	private ProductCategoryService productCategoryService;

	public ProductFilter save(@Valid @NotNull ProductFilterCreateRequest productFilterCreateRequest) {
		ProductFilter productFilter = new ProductFilter();
		productFilter.setId(UUID.randomUUID());
		ProductCategory productCategory = productCategoryService.findById(productFilterCreateRequest.getProductCategoryId()).orElseThrow(() -> new ApiException("Product category not found"));
		productFilter.setProductCategory(productCategory);
		productFilter.setTranslations(productFilterCreateRequest.getNames().entrySet().stream().filter(entry -> StringUtils.isNotBlank(entry.getValue())).map(name -> {
			ProductFilterTranslations pct = new ProductFilterTranslations();
			pct.setLanguage(Language.getByCode(name.getKey()));
			pct.setName(name.getValue());
			return pct;
		}).collect(Collectors.toSet()));
		productFilter.setType(productFilterCreateRequest.getType());
		return productFilterRepository.save(productFilter);
	}

	public ProductFilter update(@NotNull UUID id, @Valid @NotNull ProductFilterUpdateRequest productFilterUpdateRequest) {
		ProductFilter productFilter = productFilterRepository.findById(id)
				.orElseThrow(() -> new ApiException("Product filter not found"));
		productFilter.setTranslations(productFilterUpdateRequest.getNames().entrySet().stream().filter(entry -> StringUtils.isNotBlank(entry.getValue())).map(name -> {
			ProductFilterTranslations pct = new ProductFilterTranslations();
			pct.setLanguage(Language.getByCode(name.getKey()));
			pct.setName(name.getValue());
			return pct;
		}).collect(Collectors.toSet()));
		return productFilterRepository.save(productFilter);
	}

	public Set<ProductFilter> findByProductCategoryId(@NotNull UUID productCategoryId) {
		return productFilterRepository.findByProductCategoryId(productCategoryId, PRODUCT_FILTER_WITH_ALL);
	}

	public Optional<ProductFilter> findById(@NotNull UUID id) {
		return productFilterRepository.findById(id, PRODUCT_FILTER_WITH_ALL);
	}

	public void deleteById(@NotNull UUID id) {
		productFilterRepository.deleteById(id);
	}
}
