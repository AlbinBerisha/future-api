package io.github.albinberisha.future.api.mapper;

import java.util.Collection;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import io.github.albinberisha.future.api.dto.MerchantCreateRequest;
import io.github.albinberisha.future.api.dto.MerchantDto;
import io.github.albinberisha.future.api.entity.Merchant;

/**
 * @author Albin Berisha
 *
 */
public interface MerchantMapper {

	@Mapping(target = "mainUser", source = "mainUser", qualifiedByName = "toUserDtoSummary")
	@Named("toMerchantDto")
	MerchantDto toMerchantDto(Merchant merchant);

	@IterableMapping(qualifiedByName = "toMerchantDto")
	Collection<MerchantDto> toMerchantDtoList(Collection<Merchant> merchants);

	@Mapping(target = "mainUser", ignore = true)
	@Named("toMerchantDtoSummary")
	MerchantDto toMerchantDtoSummary(Merchant merchant);

	default Merchant toMerchant(MerchantCreateRequest merchantCreateDto) {
		Merchant merchant = new Merchant();
		merchant.setName(merchantCreateDto.getName());
		merchant.setDescription(merchantCreateDto.getDescription());
		return merchant;
	}

}