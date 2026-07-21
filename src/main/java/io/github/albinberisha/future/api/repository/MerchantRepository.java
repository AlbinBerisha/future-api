package io.github.albinberisha.future.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.albinberisha.future.api.entity.Merchant;

/**
 * @author Albin Berisha
 *
 */
@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {

}
