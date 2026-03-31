package io.github.albinberisha.future.api.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.albinberisha.future.api.domain.ProductImage;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, String> {

	Set<ProductImage> findByIdIn(Collection<String> ids);

}
