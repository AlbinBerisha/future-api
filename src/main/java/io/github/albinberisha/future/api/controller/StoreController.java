package io.github.albinberisha.future.api.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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

import io.github.albinberisha.future.api.domain.Store;
import io.github.albinberisha.future.api.domain.User;
import io.github.albinberisha.future.api.dto.PaginatedResponseDto;
import io.github.albinberisha.future.api.dto.StoreCreateDto;
import io.github.albinberisha.future.api.dto.StoreDto;
import io.github.albinberisha.future.api.dto.StoreUpdateDto;
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
	public ResponseEntity<PaginatedResponseDto<StoreDto>> listStores(Authentication auth) {
		User authorizedUser = (User) auth.getPrincipal();
		List<Store> stores = storeService.findByMerchant(authorizedUser.getMerchant());
		PaginatedResponseDto<StoreDto> response = new PaginatedResponseDto<>();
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
	public ResponseEntity<StoreDto> createStore(Authentication auth, @Valid @RequestBody StoreCreateDto storeCreateDto) {
		User authUser = (User) auth.getPrincipal();
		Store store = storeService.save(authUser.getMerchant(), storeCreateDto);
		return new ResponseEntity<>(objectMapper.toStoreDto(store), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('UPDATE_STORE')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateStore(@PathVariable String id, @Valid @RequestBody StoreUpdateDto storeUpdateDto) {
		storeService.update(id, storeUpdateDto);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasAuthority('DELETE_STORE')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStore(Authentication auth, @PathVariable String id) {
		try {
			User authUser = (User) auth.getPrincipal();
			storeService.deleteByIdAndMerchant(id, authUser.getMerchant());
			return ResponseEntity.noContent().build();
		} catch (EmptyResultDataAccessException e) {
			throw new ApiException("Store not found");
		} catch (DataIntegrityViolationException e) {
			throw new ApiException("Store cannot be deleted");
		}
	}
}
