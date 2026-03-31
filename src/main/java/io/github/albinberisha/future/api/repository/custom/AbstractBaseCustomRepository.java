package io.github.albinberisha.future.api.repository.custom;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public abstract class AbstractBaseCustomRepository<T> implements CustomRepository<T> {
	protected static final String FETCH_GRAPH = "jakarta.persistence.fetchgraph";
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public List<T> findAll(@NotBlank String entityGraphName) {
		return entityManager.createQuery("SELECT e FROM " + getEntityClass().getName() + " e", getEntityClass())
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultList();
	}

	@Override
	public Optional<T> findById(@NotBlank String id, @NotBlank String entityGraphName) {
		Map<String, Object> hints = Map.of(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName));
		return Optional.ofNullable(entityManager.find(getEntityClass(), id, hints));
	}

	protected abstract Class<T> getEntityClass();
}
