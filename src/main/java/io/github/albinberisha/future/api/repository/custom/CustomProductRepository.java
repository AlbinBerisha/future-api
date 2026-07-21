package io.github.albinberisha.future.api.repository.custom;

import java.util.List;

import io.github.albinberisha.future.api.entity.Merchant;
import io.github.albinberisha.future.api.entity.Product;

/**
 * @author Albin Berisha
 *
 */
public interface CustomProductRepository extends CustomRepository<Product> {

	List<Product> findByMerchant(Merchant merchant, String entityGraphName);

}
