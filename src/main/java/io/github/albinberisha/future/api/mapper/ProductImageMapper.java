package io.github.albinberisha.future.api.mapper;

import java.util.Collection;

import org.mapstruct.Mapping;

import io.github.albinberisha.future.api.dto.ProductImageDto;
import io.github.albinberisha.future.api.entity.ProductImage;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface ProductImageMapper {

	@Mapping(target = "name", ignore = true)
	ProductImageDto toProductImageDto(ProductImage productImage);

	default Collection<ProductImageDto> toProductImageDtoList(Collection<ProductImage> productImages) {
		return productImages.stream().map(this::toProductImageDto).toList();
	}

}
