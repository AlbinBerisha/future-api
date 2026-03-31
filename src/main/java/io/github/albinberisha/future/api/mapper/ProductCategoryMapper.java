package io.github.albinberisha.future.api.mapper;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import io.github.albinberisha.future.api.domain.ProductCategory;
import io.github.albinberisha.future.api.domain.embeddable.ProductCategoryTranslations;
import io.github.albinberisha.future.api.dto.ProductCategoryDto;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface ProductCategoryMapper {

	@Mapping(target = "names", source = "translations", qualifiedByName =  "toProductCategoryNamesMap")
	@Mapping(target = "parentCategory", source = "parentCategory", qualifiedByName = "toProductCategoryDtoSummary")
	@Named("toProductCategoryDto")
	ProductCategoryDto toProductCategoryDto(ProductCategory productCategory);

	@IterableMapping(qualifiedByName = "toProductCategoryDto")
	Collection<ProductCategoryDto> toProductCategoryDtoList(Collection<ProductCategory> productCategories);

	@Mapping(target = "names", source = "translations", qualifiedByName =  "toProductCategoryNamesMap")
	@Mapping(target = "parentCategory", ignore = true)
	@Mapping(target = "filters", ignore = true)
	@Named("toProductCategoryDtoSummary")
	ProductCategoryDto toProductCategoryDtoSummary(ProductCategory productCategory);

	@Named("toProductCategoryNamesMap")
	default Map<String, String> toProductCategoryNamesMap(Collection<ProductCategoryTranslations> translations) {
		return translations.stream()
				.collect(Collectors.toMap(pct -> pct.getLanguage().getCode(), ProductCategoryTranslations::getName, StringUtils::join));
	}

}
