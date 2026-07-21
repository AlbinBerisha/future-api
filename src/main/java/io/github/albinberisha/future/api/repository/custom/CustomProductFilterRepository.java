package io.github.albinberisha.future.api.repository.custom;

import java.util.Set;
import java.util.UUID;

import io.github.albinberisha.future.api.entity.ProductFilter;

/**
 * @author Albin Berisha
 *
 */
public interface CustomProductFilterRepository extends CustomRepository<ProductFilter> {

	Set<ProductFilter> findByProductCategoryId(UUID productCategoryId, String entityGraphName);

}
