package io.github.albinberisha.future.api.mapper;

import java.util.Collection;
import java.util.Set;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import io.github.albinberisha.future.api.dto.StoreCreateRequest;
import io.github.albinberisha.future.api.dto.StoreDto;
import io.github.albinberisha.future.api.entity.Store;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface StoreMapper {

	@Mapping(target = "merchant", qualifiedByName = "toMerchantDtoSummary")
	@Named("toStoreDto")
	StoreDto toStoreDto(Store store);

	@IterableMapping(qualifiedByName = "toStoreDtoSummary")
	Collection<StoreDto> toStoreDtoCollection(Collection<Store> stores);

	@Mapping(target = "merchant", ignore = true)
	@Named("toStoreDtoSummary")
	StoreDto toStoreDtoSummary(Store store);

	default Store toStore(StoreCreateRequest dto) {
		Store store = new Store();
		store.setName(dto.getName());
		store.setDescription(dto.getDescription());
		return store;
	}

	Set<Store> toStoreSet(Collection<StoreCreateRequest> stores);

}
