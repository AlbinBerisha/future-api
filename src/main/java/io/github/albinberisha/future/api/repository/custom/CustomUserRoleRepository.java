package io.github.albinberisha.future.api.repository.custom;

import java.util.List;
import java.util.Optional;

import io.github.albinberisha.future.api.domain.Merchant;
import io.github.albinberisha.future.api.domain.UserRole;
import io.github.albinberisha.future.api.domain.enums.Scope;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface CustomUserRoleRepository extends CustomRepository<UserRole> {

	List<UserRole> findByMerchant(Merchant merchant, String entityGraphName);

	Optional<UserRole> findByName(String name, String entityGraphName);

	Optional<UserRole> findByIdAndMerchant(String id, Merchant merchant, String entityGraphName);

	List<UserRole> findByMerchantAndScope(Merchant merchant, Scope scope, String entityGraphName);

}
