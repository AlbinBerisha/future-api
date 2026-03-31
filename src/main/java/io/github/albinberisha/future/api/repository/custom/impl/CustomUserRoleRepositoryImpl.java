package io.github.albinberisha.future.api.repository.custom.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import io.github.albinberisha.future.api.domain.Merchant;
import io.github.albinberisha.future.api.domain.UserRole;
import io.github.albinberisha.future.api.domain.enums.Scope;
import io.github.albinberisha.future.api.repository.custom.AbstractBaseCustomRepository;
import io.github.albinberisha.future.api.repository.custom.CustomUserRoleRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Transactional(readOnly = true)
public class CustomUserRoleRepositoryImpl extends AbstractBaseCustomRepository<UserRole> implements CustomUserRoleRepository {

	@Override
	public List<UserRole> findByMerchant(@Nullable Merchant merchant, @NotBlank String entityGraphName) {
		return entityManager.createQuery("SELECT ur FROM UserRole ur WHERE (:merchant IS NULL AND ur.merchant IS NULL) OR ur.merchant = :merchant", UserRole.class)
				.setParameter("merchant", merchant)
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultList();
	}

	@Override
	public Optional<UserRole> findByName(@NotBlank String name, @NotBlank String entityGraphName) {
		return entityManager.createQuery("SELECT ur FROM UserRole ur WHERE ur.name = :name", UserRole.class)
				.setParameter("name", name)
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultStream()
				.findFirst();
	}

	@Override
	public Optional<UserRole> findByIdAndMerchant(@NotBlank String id, @NotNull Merchant merchant, @NotBlank String entityGraphName) {
		return entityManager.createQuery("SELECT ur FROM UserRole ur WHERE ur.id = :id AND ur.merchant = :merchant", UserRole.class)
				.setParameter("id", id)
				.setParameter("merchant", merchant)
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultStream()
				.findFirst();
	}

	@Override
	public List<UserRole> findByMerchantAndScope(@NotNull Merchant merchant, @NotNull Scope scope, @NotBlank String entityGraphName) {
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
