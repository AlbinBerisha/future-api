package io.github.albinberisha.future.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.albinberisha.future.api.domain.Merchant;
import io.github.albinberisha.future.api.domain.UserRole;
import io.github.albinberisha.future.api.repository.custom.CustomUserRoleRepository;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String>, CustomUserRoleRepository {

	void deleteByIdAndMerchant(String id, Merchant merchant);

}
