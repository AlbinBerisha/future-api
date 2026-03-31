package io.github.albinberisha.future.api.mapper;

import java.util.Collection;

import org.mapstruct.Mapping;

import io.github.albinberisha.future.api.domain.ProductAttribute;
import io.github.albinberisha.future.api.dto.ProductAttributeDto;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface ProductAttributeMapper {

	@Mapping(target = "productFilter", source = "productFilter", qualifiedByName = "toProductFilterDtoSummary")
	@Mapping(target = "productFilterId", source = "productFilter.id")
	ProductAttributeDto toProductAttributeDto(ProductAttribute productAttribute);

	Collection<ProductAttributeDto> toProductAttributeDtoList(Collection<ProductAttribute> productAttributes);

}
