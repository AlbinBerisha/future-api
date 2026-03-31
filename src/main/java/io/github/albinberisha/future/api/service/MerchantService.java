package io.github.albinberisha.future.api.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.albinberisha.future.api.domain.Merchant;
import io.github.albinberisha.future.api.domain.Store;
import io.github.albinberisha.future.api.domain.User;
import io.github.albinberisha.future.api.dto.MerchantCreateDto;
import io.github.albinberisha.future.api.dto.MerchantUpdateDto;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.mapper.ObjectMapper;
import io.github.albinberisha.future.api.repository.MerchantRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Service
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

	public Optional<Merchant> findById(@NotBlank String id) {
		return merchantRepository.findById(id);
	}

	@Transactional
	public Merchant save(@Valid @NotNull MerchantCreateDto merchantCreateDto) {
		Merchant merchant = objectMapper.toMerchant(merchantCreateDto);
		User mainUser = userService.save(merchantCreateDto.getMainUser(), null, null);
		merchant.setMainUser(mainUser);
		mainUser.setMerchant(merchant);
		Set<Store> stores = objectMapper.toStoreSet(merchantCreateDto.getStores());
		stores.stream().forEach(store -> store.setMerchant(merchant));
		merchant.setStores(stores);
		return merchantRepository.save(merchant);
	}

	public void deleteById(@NotBlank String id) {
		merchantRepository.deleteById(id);
	}

	public Merchant update(@NotBlank String id, @Valid @NotNull MerchantUpdateDto merchantUpdateDto) {
		Merchant merchant = merchantRepository.findById(id).orElseThrow(() -> new ApiException("Merchant not found"));
		merchant.setName(merchantUpdateDto.getName());
		merchant.setDescription(merchantUpdateDto.getDescription());
		return merchantRepository.save(merchant);
	}
}
