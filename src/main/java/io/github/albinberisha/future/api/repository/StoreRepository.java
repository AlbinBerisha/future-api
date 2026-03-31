package io.github.albinberisha.future.api.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.albinberisha.future.api.domain.Merchant;
import io.github.albinberisha.future.api.domain.Store;
import io.github.albinberisha.future.api.repository.custom.CustomStoreRepository;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Repository
public interface StoreRepository extends JpaRepository<Store, String>, CustomStoreRepository {

	@EntityGraph("Store.basic")
	Set<Store> findByIdIn(Collection<String> ids);

	long deleteByIdAndMerchant(String id, Merchant merchant);

}
