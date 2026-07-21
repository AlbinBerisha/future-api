package io.github.albinberisha.future.api.repository.custom.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import io.github.albinberisha.future.api.entity.Merchant;
import io.github.albinberisha.future.api.entity.Product;
import io.github.albinberisha.future.api.repository.custom.AbstractBaseCustomRepository;
import io.github.albinberisha.future.api.repository.custom.CustomProductRepository;

/**
 * @author Albin Berisha
 *
 */
@Transactional(readOnly = true)
public class CustomProductRepositoryImpl extends AbstractBaseCustomRepository<Product> implements CustomProductRepository {

	@Override
	public List<Product> findByMerchant(Merchant merchant, String entityGraphName) {
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
