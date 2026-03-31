package io.github.albinberisha.future.api.repository.custom;

import java.util.List;

import io.github.albinberisha.future.api.domain.Merchant;
import io.github.albinberisha.future.api.domain.Product;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface CustomProductRepository extends CustomRepository<Product> {

	List<Product> findByMerchant(Merchant merchant, String entityGraphName);

}
