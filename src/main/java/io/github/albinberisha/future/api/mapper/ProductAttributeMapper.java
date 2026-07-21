package io.github.albinberisha.future.api.mapper;

import java.util.Collection;

import org.mapstruct.Mapping;

import io.github.albinberisha.future.api.dto.ProductAttributeDto;
import io.github.albinberisha.future.api.entity.ProductAttribute;

/**
 * @author Albin Berisha
 *
 */
public interface ProductAttributeMapper {

	@Mapping(target = "productFilter", source = "productFilter", qualifiedByName = "toProductFilterDtoSummary")
	@Mapping(target = "productFilterId", source = "productFilter.id")
	ProductAttributeDto toProductAttributeDto(ProductAttribute productAttribute);

	Collection<ProductAttributeDto> toProductAttributeDtoList(Collection<ProductAttribute> productAttributes);

}
