package io.github.albinberisha.future.api.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.albinberisha.future.api.entity.Merchant;
import io.github.albinberisha.future.api.entity.Product;
import io.github.albinberisha.future.api.repository.custom.CustomProductRepository;

/**
 * @author Albin Berisha
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, CustomProductRepository {

	@EntityGraph("Product.withAll")
	@Query("SELECT p FROM Product p WHERE (:categories IS NULL OR p.category.name IN :categories)")
	List<Product> findByFilters(@Param("categories") List<String> categories);

	Optional<Product> findByIdAndMerchant(UUID id, Merchant merchant);

	void deleteByIdAndMerchant(UUID id, Merchant merchant);

}
