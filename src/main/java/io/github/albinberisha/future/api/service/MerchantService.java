package io.github.albinberisha.future.api.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.github.albinberisha.future.api.dto.MerchantCreateRequest;
import io.github.albinberisha.future.api.dto.MerchantUpdateRequest;
import io.github.albinberisha.future.api.entity.Merchant;
import io.github.albinberisha.future.api.entity.Store;
import io.github.albinberisha.future.api.entity.User;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.mapper.ObjectMapper;
import io.github.albinberisha.future.api.repository.MerchantRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha
 *
 */
@Service
@Validated
public class MerchantService {
	@Autowired
	private MerchantRepository merchantRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ObjectMapper objectMapper;

	public List<Merchant> findAll() {
		return merchantRepository.findAll();
	}

	public Optional<Merchant> findById(@NotNull UUID id) {
		return merchantRepository.findById(id);
	}

	@Transactional
	public Merchant save(@Valid @NotNull MerchantCreateRequest merchantCreateRequest) {
		Merchant merchant = objectMapper.toMerchant(merchantCreateRequest);
		User mainUser = userService.save(merchantCreateRequest.getMainUser(), null, null);
		merchant.setMainUser(mainUser);
		mainUser.setMerchant(merchant);
		Set<Store> stores = objectMapper.toStoreSet(merchantCreateRequest.getStores());
		stores.stream().forEach(store -> store.setMerchant(merchant));
		merchant.setStores(stores);
		return merchantRepository.save(merchant);
	}

	public void deleteById(@NotNull UUID id) {
		merchantRepository.deleteById(id);
	}

	public Merchant update(@NotNull UUID id, @Valid @NotNull MerchantUpdateRequest merchantUpdateRequest) {
		Merchant merchant = merchantRepository.findById(id).orElseThrow(() -> new ApiException("Merchant not found"));
		merchant.setName(merchantUpdateRequest.getName());
		merchant.setDescription(merchantUpdateRequest.getDescription());
		return merchantRepository.save(merchant);
	}
}
