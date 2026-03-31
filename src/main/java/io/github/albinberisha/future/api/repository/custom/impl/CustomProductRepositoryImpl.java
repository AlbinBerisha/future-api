package io.github.albinberisha.future.api.repository.custom.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import io.github.albinberisha.future.api.domain.Merchant;
import io.github.albinberisha.future.api.domain.Product;
import io.github.albinberisha.future.api.repository.custom.AbstractBaseCustomRepository;
import io.github.albinberisha.future.api.repository.custom.CustomProductRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Transactional(readOnly = true)
public class CustomProductRepositoryImpl extends AbstractBaseCustomRepository<Product> implements CustomProductRepository {

	@Override
	public List<Product> findByMerchant(@NotNull Merchant merchant, @NotBlank String entityGraphName) {
		return entityManager.createQuery("SELECT p FROM Product p WHERE p.merchant = :merchant", Product.class)
				.setParameter("merchant", merchant)
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultList();
	}

	@Override
	protected Class<Product> getEntityClass() {
		return Product.class;
	}

}
