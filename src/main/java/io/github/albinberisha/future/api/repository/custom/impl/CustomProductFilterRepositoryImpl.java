package io.github.albinberisha.future.api.repository.custom.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import io.github.albinberisha.future.api.domain.ProductFilter;
import io.github.albinberisha.future.api.repository.custom.AbstractBaseCustomRepository;
import io.github.albinberisha.future.api.repository.custom.CustomProductFilterRepository;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Transactional(readOnly = true)
public class CustomProductFilterRepositoryImpl extends AbstractBaseCustomRepository<ProductFilter> implements CustomProductFilterRepository {

	@Override
	public Set<ProductFilter> findByProductCategoryId(String productCategoryId, String entityGraphName) {
		return entityManager.createQuery("SELECT pf FROM ProductFilter pf WHERE pf.productCategory.id = :productCategoryId", ProductFilter.class)
				.setParameter("productCategoryId", productCategoryId)
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultStream()
				.collect(Collectors.toSet());
	}

	@Override
	protected Class<ProductFilter> getEntityClass() {
		return ProductFilter.class;
	}

}
