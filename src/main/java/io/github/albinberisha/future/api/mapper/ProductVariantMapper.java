package io.github.albinberisha.future.api.mapper;

import java.util.Collection;

import org.mapstruct.Mapping;

import io.github.albinberisha.future.api.dto.ProductVariantDto;
import io.github.albinberisha.future.api.entity.ProductVariant;

/**
 * @author Albin Berisha
 *
 */
public interface ProductVariantMapper {

	@Mapping(target = "images", ignore = true)
	ProductVariantDto toProductVariantDto(ProductVariant productVariant);

	Collection<ProductVariantDto> toProductVariantDtoList(Collection<ProductVariant> productVariants);

}
