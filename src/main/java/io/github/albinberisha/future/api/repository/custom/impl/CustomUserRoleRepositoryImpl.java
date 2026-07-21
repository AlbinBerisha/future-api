package io.github.albinberisha.future.api.repository.custom.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import io.github.albinberisha.future.api.entity.Merchant;
import io.github.albinberisha.future.api.entity.UserRole;
import io.github.albinberisha.future.api.entity.enums.Scope;
import io.github.albinberisha.future.api.repository.custom.AbstractBaseCustomRepository;
import io.github.albinberisha.future.api.repository.custom.CustomUserRoleRepository;

/**
 * @author Albin Berisha
 *
 */
@Transactional(readOnly = true)
public class CustomUserRoleRepositoryImpl extends AbstractBaseCustomRepository<UserRole> implements CustomUserRoleRepository {

	@Override
	public List<UserRole> findByMerchant(Merchant merchant, String entityGraphName) {
		return entityManager.createQuery("SELECT ur FROM UserRole ur WHERE (:merchant IS NULL AND ur.merchant IS NULL) OR ur.merchant = :merchant", UserRole.class)
				.setParameter("merchant", merchant)
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultList();
	}

	@Override
	public Optional<UserRole> findByName(String name, String entityGraphName) {
		return entityManager.createQuery("SELECT ur FROM UserRole ur WHERE ur.name = :name", UserRole.class)
				.setParameter("name", name)
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultStream()
				.findFirst();
	}

	@Override
	public Optional<UserRole> findByIdAndMerchant(UUID id, Merchant merchant, String entityGraphName) {
		return entityManager.createQuery("SELECT ur FROM UserRole ur WHERE ur.id = :id AND ((:merchant IS NULL AND ur.merchant IS NULL) OR ur.merchant = :merchant)", UserRole.class)
				.setParameter("id", id)
				.setParameter("merchant", merchant)
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultStream()
				.findFirst();
	}

	@Override
	public List<UserRole> findByMerchantAndScope(Merchant merchant, Scope scope, String entityGraphName) {
		return entityManager.createQuery("SELECT ur FROM UserRole ur WHERE ur.merchant = :merchant AND ur.scope = :scope", UserRole.class)
				.setParameter("merchant", merchant)
				.setParameter("scope", scope)
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultList();
	}

	@Override
	protected Class<UserRole> getEntityClass() {
		return UserRole.class;
	}

}
