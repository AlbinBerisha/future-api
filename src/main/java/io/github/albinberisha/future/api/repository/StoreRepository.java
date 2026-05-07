package io.github.albinberisha.future.api.repository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.albinberisha.future.api.entity.Merchant;
import io.github.albinberisha.future.api.entity.Store;
import io.github.albinberisha.future.api.repository.custom.CustomStoreRepository;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Repository
public interface StoreRepository extends JpaRepository<Store, UUID>, CustomStoreRepository {

	@EntityGraph("Store.basic")
	Set<Store> findByIdIn(Collection<UUID> ids);

	long deleteByIdAndMerchant(UUID id, Merchant merchant);

}
