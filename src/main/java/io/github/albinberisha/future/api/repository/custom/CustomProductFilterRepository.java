package io.github.albinberisha.future.api.repository.custom;

import java.util.Set;
import java.util.UUID;

import io.github.albinberisha.future.api.entity.ProductFilter;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface CustomProductFilterRepository extends CustomRepository<ProductFilter> {

	Set<ProductFilter> findByProductCategoryId(UUID productCategoryId, String entityGraphName);

}
