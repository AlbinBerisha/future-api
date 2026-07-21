package io.github.albinberisha.future.api.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.github.albinberisha.future.api.dto.StoreCreateRequest;
import io.github.albinberisha.future.api.dto.StoreUpdateRequest;
import io.github.albinberisha.future.api.entity.Merchant;
import io.github.albinberisha.future.api.entity.Store;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.mapper.ObjectMapper;
import io.github.albinberisha.future.api.repository.StoreRepository;

/**
 * @author Albin Berisha
 *
 */
@Service
@Validated
public class StoreService {
	private static final String STORE_WITH_ALL = "Store.withAll";
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private ObjectMapper objectMapper;

	public Optional<Store> findById(@NotNull UUID id) {
		return storeRepository.findById(id, STORE_WITH_ALL);
	}

	public Store save(@NotNull Merchant merchant, @Valid @NotNull StoreCreateRequest storeCreateRequest) {
		Store store = objectMapper.toStore(storeCreateRequest);
		store.setMerchant(merchant);
		return storeRepository.save(store);
	}

	@Transactional
	public long deleteByIdAndMerchant(@NotNull UUID id, @NotNull Merchant merchant) {
		return storeRepository.deleteByIdAndMerchant(id, merchant);
	}

	@Transactional
	public Store update(@NotNull UUID id, @Valid @NotNull StoreUpdateRequest storeUpdateRequest) {
		Store store = storeRepository.findById(id).orElseThrow(() -> new ApiException("Store not found"));
		store.setName(storeUpdateRequest.getName());
		store.setDescription(storeUpdateRequest.getDescription());
		return storeRepository.save(store);
	}

	public Set<Store> findByIdIn(@NotEmpty Collection<UUID> ids) {
		return storeRepository.findByIdIn(ids);
	}

	public List<Store> findByMerchant(Merchant merchant) {
		return storeRepository.findByMerchant(merchant, STORE_WITH_ALL);
	}
}
