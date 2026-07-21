package io.github.albinberisha.future.api.mapper;

import org.mapstruct.Mapper;

/**
 * @author Albin Berisha
 *
 */
@Mapper(componentModel = "spring")
public interface ObjectMapper extends UserMapper, MerchantMapper, StoreMapper, ProductMapper, ProductCategoryMapper,
		ProductVariantMapper, ProductFilterMapper, ProductAttributeMapper, UserRoleMapper,
		FileResourceMapper {

}
