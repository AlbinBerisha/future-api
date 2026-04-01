package io.github.albinberisha.future.api.repository.custom;

import java.util.List;
import java.util.Optional;

import io.github.albinberisha.future.api.entity.Merchant;
import io.github.albinberisha.future.api.entity.Store;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface CustomStoreRepository extends CustomRepository<Store> {

	Optional<Store> findByIdAndMerchant(String id, Merchant merchant, String entityGraphName);

	List<Store> findByMerchant(Merchant merchant, String entityGraphName);

}
