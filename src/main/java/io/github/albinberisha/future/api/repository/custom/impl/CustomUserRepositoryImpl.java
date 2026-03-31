package io.github.albinberisha.future.api.repository.custom.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import io.github.albinberisha.future.api.domain.Merchant;
import io.github.albinberisha.future.api.domain.User;
import io.github.albinberisha.future.api.repository.custom.AbstractBaseCustomRepository;
import io.github.albinberisha.future.api.repository.custom.CustomUserRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Transactional(readOnly = true)
public class CustomUserRepositoryImpl extends AbstractBaseCustomRepository<User> implements CustomUserRepository {

	@Override
	public Optional<User> findByUsername(@NotBlank String username, @NotBlank String entityGraphName) {
		return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
				.setParameter("username", username)
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultStream()
				.findFirst();
	}

	@Override
	public List<User> findByMerchant(@NotNull Merchant merchant, @NotBlank String entityGraphName) {
		return entityManager.createQuery("SELECT u FROM User u WHERE u.merchant = :merchant", User.class)
				.setParameter("merchant", merchant)
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultList();
	}

	@Override
	public Optional<User> findByMerchantAndId(@NotNull Merchant merchant, @NotBlank String id, @NotBlank String entityGraphName) {
		return entityManager.createQuery("SELECT u FROM User u WHERE u.merchant = :merchant AND u.id = :id", User.class)
				.setParameter("merchant", merchant)
				.setParameter("id", id)
				.setHint(FETCH_GRAPH, entityManager.getEntityGraph(entityGraphName))
				.getResultStream()
				.findFirst();
	}

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

}
