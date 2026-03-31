package io.github.albinberisha.future.api.mapper;

import java.util.Collection;

import io.github.albinberisha.future.api.domain.ProductVariant;
import io.github.albinberisha.future.api.dto.ProductVariantDto;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface ProductVariantMapper {

	ProductVariantDto toProductVariantDto(ProductVariant productVariant);

	Collection<ProductVariantDto> toProductVariantDtoList(Collection<ProductVariant> productVariants);

}
