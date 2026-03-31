package io.github.albinberisha.future.api.mapper;

import java.util.Collection;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import io.github.albinberisha.future.api.domain.Merchant;
import io.github.albinberisha.future.api.dto.MerchantCreateDto;
import io.github.albinberisha.future.api.dto.MerchantDto;

/**
 * @author Albin Berisha <albin199915@gmail.com>
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

	default Merchant toMerchant(MerchantCreateDto merchantCreateDto) {
		Merchant merchant = new Merchant();
		merchant.setName(merchantCreateDto.getName());
		merchant.setDescription(merchantCreateDto.getDescription());
		return merchant;
	}

}