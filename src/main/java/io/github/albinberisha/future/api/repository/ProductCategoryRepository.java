package io.github.albinberisha.future.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.albinberisha.future.api.entity.ProductCategory;
import io.github.albinberisha.future.api.repository.custom.CustomProductCategoryRepository;

/**
 * @author Albin Berisha
 *
 */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID>, CustomProductCategoryRepository {

}
