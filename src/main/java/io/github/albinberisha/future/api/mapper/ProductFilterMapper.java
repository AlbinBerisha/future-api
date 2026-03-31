package io.github.albinberisha.future.api.mapper;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import io.github.albinberisha.future.api.domain.ProductFilter;
import io.github.albinberisha.future.api.domain.embeddable.ProductFilterTranslations;
import io.github.albinberisha.future.api.dto.ProductFilterDto;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface ProductFilterMapper {

	@Mapping(target = "productCategoryId", source = "productCategory.id")
	@Mapping(target = "names", source = "translations", qualifiedByName = "toProductFilterNamesMap")
	@Named("toProductFilterDto")
	ProductFilterDto toProductFilterDto(ProductFilter productFilter);

	@IterableMapping(qualifiedByName = "toProductFilterDto")
	Collection<ProductFilterDto> toProductFilterDtoList(Collection<ProductFilter> filters);

	@Mapping(target = "productCategoryId", ignore = true)
//	@Mapping(target = "productCategory", ignore = true)
	@Mapping(target = "names", source = "translations", qualifiedByName = "toProductFilterNamesMap")
	@Named("toProductFilterDtoSummary")
	ProductFilterDto toProductFilterDtoSummary(ProductFilter productFilter);

	@Named("toProductFilterNamesMap")
	default Map<String, String> toProductFilterNamesMap(Collection<ProductFilterTranslations> translations) {
		return translations.stream()
				.collect(Collectors.toMap(t -> t.getLanguage().getCode(), ProductFilterTranslations::getName, StringUtils::join));
	}

}
