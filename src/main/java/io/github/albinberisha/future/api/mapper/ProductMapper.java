package io.github.albinberisha.future.api.mapper;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import io.github.albinberisha.future.api.dto.ProductDto;
import io.github.albinberisha.future.api.entity.Product;
import io.github.albinberisha.future.api.entity.embeddable.ProductTranslations;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface ProductMapper {

	@Mapping(target = "names", source = "translations", qualifiedByName = "toProductNamesMap")
	@Mapping(target = "descriptions", source = "translations", qualifiedByName = "toProductDescriptionsMap")
	@Mapping(target = "category", source = "category", qualifiedByName = "toProductCategoryDtoSummary")
	@Mapping(target = "images", ignore = true)
	@Named("toProductDto")
	ProductDto toProductDto(Product product);

	@IterableMapping(qualifiedByName = "toProductDto")
	Collection<ProductDto> toProductDtoList(Collection<Product> products);

	@Named("toProductNamesMap")
	default Map<String, String> toProductNamesMap(Collection<ProductTranslations> translations) {
		return translations.stream()
				.collect(Collectors.toMap(t -> t.getLanguage().getCode(), ProductTranslations::getName, StringUtils::join));
	}

	@Named("toProductDescriptionsMap")
	default Map<String, String> toDescriptionsMap(Collection<ProductTranslations> translations) {
		return translations.stream()
				.collect(Collectors.toMap(t -> t.getLanguage().getCode(), ProductTranslations::getDescription, StringUtils::join));
	}

}
