package io.github.albinberisha.future.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.albinberisha.future.api.domain.Merchant;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Repository
public interface MerchantRepository extends JpaRepository<Merchant, String> {

}
