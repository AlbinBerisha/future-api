package io.github.albinberisha.future.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.albinberisha.future.api.entity.ProductFilter;
import io.github.albinberisha.future.api.repository.custom.CustomProductFilterRepository;

/**
 * @author Albin Berisha
 *
 */
@Repository
public interface ProductFilterRepository extends JpaRepository<ProductFilter, UUID>, CustomProductFilterRepository {

}
