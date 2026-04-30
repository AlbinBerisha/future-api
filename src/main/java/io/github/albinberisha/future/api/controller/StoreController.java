package io.github.albinberisha.future.api.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.albinberisha.future.api.dto.PaginatedResponse;
import io.github.albinberisha.future.api.dto.StoreCreateRequest;
import io.github.albinberisha.future.api.dto.StoreDto;
import io.github.albinberisha.future.api.dto.StoreUpdateRequest;
import io.github.albinberisha.future.api.entity.Store;
import io.github.albinberisha.future.api.entity.User;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.mapper.ObjectMapper;
import io.github.albinberisha.future.api.service.StoreService;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@RequestMapping("/api/stores")
@RestController
public class StoreController {
	@Autowired
	private StoreService storeService;
	@Autowired
	private ObjectMapper objectMapper;

	@PreAuthorize("hasAuthority('VIEW_STORE')")
	@GetMapping
	public ResponseEntity<PaginatedResponse<StoreDto>> listStores(Authentication auth) {
		User authorizedUser = (User) auth.getPrincipal();
		List<Store> stores = storeService.findByMerchant(authorizedUser.getMerchant());
		PaginatedResponse<StoreDto> response = new PaginatedResponse<>();
		response.setContent(objectMapper.toStoreDtoCollection(stores));
		response.setSize(stores.size());
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAuthority('VIEW_STORE')")
	@GetMapping("/{id}")
	public ResponseEntity<StoreDto> getStore(@PathVariable String id) {
		Store store = storeService.findById(id).orElseThrow(() -> new ApiException("Store not found"));
		return ResponseEntity.ok(objectMapper.toStoreDto(store));
	}

	@PreAuthorize("hasAuthority('CREATE_STORE')")
	@PostMapping
	public ResponseEntity<StoreDto> createStore(Authentication auth, @Valid @RequestBody StoreCreateRequest storeCreateRequest) {
		User authUser = (User) auth.getPrincipal();
		Store store = storeService.save(authUser.getMerchant(), storeCreateRequest);
		return new ResponseEntity<>(objectMapper.toStoreDto(store), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('UPDATE_STORE')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateStore(@PathVariable String id, @Valid @RequestBody StoreUpdateRequest storeUpdateRequest) {
		storeService.update(id, storeUpdateRequest);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasAuthority('DELETE_STORE')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStore(Authentication auth, @PathVariable String id) {
		User authUser = (User) auth.getPrincipal();
		storeService.deleteByIdAndMerchant(id, authUser.getMerchant());
		return ResponseEntity.noContent().build();
	}
}
