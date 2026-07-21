package io.github.albinberisha.future.api.repository.custom;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * @author Albin Berisha
 *
 */
public abstract class AbstractBaseCustomRepository<T> implements CustomRepository<T> {
	protected static final String FETCH_GRAPH = "jakarta.persistence.fetchgraph";
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public List<T> findAll(String entityGraphName) {
		return entityManager.createQuery("SELECT e FROM " + getEntityClass().getName() + " e", getEntityClass())
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultList();
	}

	@Override
	public Optional<T> findById(UUID id, String entityGraphName) {
		Map<String, Object> hints = Map.of(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName));
		return Optional.ofNullable(entityManager.find(getEntityClass(), id, hints));
	}

	protected abstract Class<T> getEntityClass();
}
