package io.github.albinberisha.future.api.repository.custom;

import java.util.Optional;

import io.github.albinberisha.future.api.entity.ProductCategory;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface CustomProductCategoryRepository extends CustomRepository<ProductCategory> {

	Optional<ProductCategory> findByName(String name, String entityGraphName);

}
