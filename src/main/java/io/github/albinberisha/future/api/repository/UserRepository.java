package io.github.albinberisha.future.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.albinberisha.future.api.entity.Merchant;
import io.github.albinberisha.future.api.entity.User;
import io.github.albinberisha.future.api.repository.custom.CustomUserRepository;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>, CustomUserRepository {

	void deleteByMerchantAndId(Merchant merchant, String id);

}
