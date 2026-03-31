package io.github.albinberisha.future.api.repository.custom;

import java.util.Set;

import io.github.albinberisha.future.api.domain.ProductFilter;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface CustomProductFilterRepository extends CustomRepository<ProductFilter> {

	Set<ProductFilter> findByProductCategoryId(String productCategoryId, String entityGraphName);

}
