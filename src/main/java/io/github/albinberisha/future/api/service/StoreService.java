package io.github.albinberisha.future.api.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.albinberisha.future.api.domain.Merchant;
import io.github.albinberisha.future.api.domain.Store;
import io.github.albinberisha.future.api.dto.StoreCreateDto;
import io.github.albinberisha.future.api.dto.StoreUpdateDto;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.mapper.ObjectMapper;
import io.github.albinberisha.future.api.repository.StoreRepository;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Service
public class StoreService {
	private static final String STORE_WITH_ALL = "Store.withAll";
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private ObjectMapper objectMapper;

	public Optional<Store> findById(@NotBlank String id) {
		return storeRepository.findById(id, STORE_WITH_ALL);
	}

	public Store save(@NotNull Merchant merchant, @Valid @NotNull StoreCreateDto storeCreateDto) {
		Store store = objectMapper.toStore(storeCreateDto);
		store.setMerchant(merchant);
		return storeRepository.save(store);
	}

	@Transactional
	public long deleteByIdAndMerchant(@NotBlank String id, @NotNull Merchant merchant) {
		return storeRepository.deleteByIdAndMerchant(id, merchant);
	}

	@Transactional
	public Store update(@NotBlank String id, @Valid @NotNull StoreUpdateDto storeUpdateDto) {
		Store store = storeRepository.findById(id).orElseThrow(() -> new ApiException("Store not found"));
		store.setName(storeUpdateDto.getName());
		store.setDescription(storeUpdateDto.getDescription());
		return storeRepository.save(store);
	}

	public Set<Store> findByIdIn(@NotEmpty Collection<String> ids) {
		return storeRepository.findByIdIn(ids);
	}

	public List<Store> findByMerchant(Merchant merchant) {
		return storeRepository.findByMerchant(merchant, STORE_WITH_ALL);
	}
}
