package io.github.albinberisha.future.api.repository.custom;

import java.util.List;
import java.util.Optional;

import io.github.albinberisha.future.api.domain.Merchant;
import io.github.albinberisha.future.api.domain.User;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface CustomUserRepository extends CustomRepository<User> {

	Optional<User> findByUsername(String username, String entityGraphName);

	List<User> findByMerchant(Merchant merchant, String entityGraphName);

	Optional<User> findByMerchantAndId(Merchant merchant, String id, String entityGraphName);

}
