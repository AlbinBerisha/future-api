package io.github.albinberisha.future.api.mapper;

import java.util.Collection;

import io.github.albinberisha.future.api.dto.ProductVariantDto;
import io.github.albinberisha.future.api.entity.ProductVariant;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface ProductVariantMapper {

	ProductVariantDto toProductVariantDto(ProductVariant productVariant);

	Collection<ProductVariantDto> toProductVariantDtoList(Collection<ProductVariant> productVariants);

}
