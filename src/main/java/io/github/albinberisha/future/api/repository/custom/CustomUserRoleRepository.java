package io.github.albinberisha.future.api.repository.custom;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.github.albinberisha.future.api.entity.Merchant;
import io.github.albinberisha.future.api.entity.UserRole;
import io.github.albinberisha.future.api.entity.enums.Scope;

/**
 * @author Albin Berisha
 *
 */
public interface CustomUserRoleRepository extends CustomRepository<UserRole> {

	List<UserRole> findByMerchant(Merchant merchant, String entityGraphName);

	Optional<UserRole> findByName(String name, String entityGraphName);

	Optional<UserRole> findByIdAndMerchant(UUID id, Merchant merchant, String entityGraphName);

	List<UserRole> findByMerchantAndScope(Merchant merchant, Scope scope, String entityGraphName);

}
