package io.github.albinberisha.future.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.albinberisha.future.api.dto.MerchantCreateRequest;
import io.github.albinberisha.future.api.dto.MerchantDto;
import io.github.albinberisha.future.api.dto.MerchantUpdateRequest;
import io.github.albinberisha.future.api.dto.PaginatedResponse;
import io.github.albinberisha.future.api.entity.Merchant;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.mapper.ObjectMapper;
import io.github.albinberisha.future.api.service.MerchantService;
import jakarta.validation.Valid;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@RequestMapping("/api/merchants")
@RestController
public class MerchantController {
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private ObjectMapper objectMapper;

	@PreAuthorize("hasAuthority('VIEW_MERCHANT')")
	@GetMapping
	public ResponseEntity<PaginatedResponse<MerchantDto>> listMerchants() {
		List<Merchant> merchants = merchantService.findAll();
		PaginatedResponse<MerchantDto> response = new PaginatedResponse<>();
		response.setContent(objectMapper.toMerchantDtoList(merchants));
		response.setSize(merchants.size());
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAuthority('VIEW_MERCHANT')")
	@GetMapping("/{id}")
	public ResponseEntity<MerchantDto> getMerchant(@PathVariable String id) {
		Merchant merchant = merchantService.findById(id).orElseThrow(() -> new ApiException("Merchant not found"));
		return ResponseEntity.ok(objectMapper.toMerchantDto(merchant));
	}

	@PreAuthorize("hasAuthority('CREATE_MERCHANT')")
	@PostMapping
	public ResponseEntity<MerchantDto> createMerchant(@Valid @RequestBody MerchantCreateRequest merchantCreateRequest) {
		Merchant merchant = merchantService.save(merchantCreateRequest);
		return new ResponseEntity<>(objectMapper.toMerchantDto(merchant), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('UPDATE_MERCHANT')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateMerchant(@PathVariable String id, @Valid @RequestBody MerchantUpdateRequest merchantUpdateRequest) {
		merchantService.update(id, merchantUpdateRequest);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasAuthority('DELETE_MERCHANT')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMerchant(@PathVariable String id) {
		merchantService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
