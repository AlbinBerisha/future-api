package io.github.albinberisha.future.api.repository.custom.impl;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import io.github.albinberisha.future.api.entity.ProductCategory;
import io.github.albinberisha.future.api.repository.custom.AbstractBaseCustomRepository;
import io.github.albinberisha.future.api.repository.custom.CustomProductCategoryRepository;

/**
 * @author Albin Berisha
 *
 */
@Transactional(readOnly = true)
public class CustomProductCategoryRepositoryImpl extends AbstractBaseCustomRepository<ProductCategory> implements CustomProductCategoryRepository {

	@Override
	public Optional<ProductCategory> findByName(String name, String entityGraphName) {
		return entityManager.createQuery("SELECT pc FROM ProductCategory pc WHERE pc.name = :name", ProductCategory.class)
				.setParameter("name", name)
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultStream()
				.findFirst();
	}

	@Override
	protected Class<ProductCategory> getEntityClass() {
		return ProductCategory.class;
	}

}
