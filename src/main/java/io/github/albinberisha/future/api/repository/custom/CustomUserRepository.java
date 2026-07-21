package io.github.albinberisha.future.api.repository.custom;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.github.albinberisha.future.api.entity.Merchant;
import io.github.albinberisha.future.api.entity.User;

/**
 * @author Albin Berisha
 *
 */
public interface CustomUserRepository extends CustomRepository<User> {

	Optional<User> findByUsername(String username, String entityGraphName);

	List<User> findByMerchant(Merchant merchant, String entityGraphName);

	Optional<User> findByMerchantAndId(Merchant merchant, UUID id, String entityGraphName);

}
