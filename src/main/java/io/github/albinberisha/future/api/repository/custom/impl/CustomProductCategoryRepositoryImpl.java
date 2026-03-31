package io.github.albinberisha.future.api.repository.custom.impl;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import io.github.albinberisha.future.api.domain.ProductCategory;
import io.github.albinberisha.future.api.repository.custom.AbstractBaseCustomRepository;
import io.github.albinberisha.future.api.repository.custom.CustomProductCategoryRepository;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Transactional(readOnly = true)
public class CustomProductCategoryRepositoryImpl extends AbstractBaseCustomRepository<ProductCategory> implements CustomProductCategoryRepository {

	@Override
	public Optional<ProductCategory> findByName(@NotBlank String name, @NotBlank String entityGraphName) {
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
