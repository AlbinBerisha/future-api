package io.github.albinberisha.future.api.repository.custom.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import io.github.albinberisha.future.api.entity.Merchant;
import io.github.albinberisha.future.api.entity.Store;
import io.github.albinberisha.future.api.repository.custom.AbstractBaseCustomRepository;
import io.github.albinberisha.future.api.repository.custom.CustomStoreRepository;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Transactional(readOnly = true)
public class CustomStoreRepositoryImpl extends AbstractBaseCustomRepository<Store> implements CustomStoreRepository {

	@Override
	public Optional<Store> findByIdAndMerchant(String id, Merchant merchant, String entityGraphName) {
		return entityManager.createQuery("SELECT s FROM Store s WHERE s.id = :id AND s.merchant = :merchant", Store.class)
				.setParameter("id", id)
				.setParameter("merchant", merchant)
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultStream()
				.findFirst();
	}

	@Override
	public List<Store> findByMerchant(Merchant merchant, String entityGraphName) {
		return entityManager.createQuery("SELECT s FROM Store s WHERE (:merchant IS NULL OR s.merchant = :merchant)", Store.class)
				.setParameter("merchant", merchant)
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultList();
	}

	@Override
	protected Class<Store> getEntityClass() {
		return Store.class;
	}

}
