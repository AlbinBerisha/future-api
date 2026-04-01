package io.github.albinberisha.future.api.mapper;

import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import io.github.albinberisha.future.api.dto.UserCreateRequest;
import io.github.albinberisha.future.api.dto.UserDto;
import io.github.albinberisha.future.api.entity.User;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface UserMapper {

	@Mapping(target = "password", ignore = true)
	@Mapping(target = "merchant", source = "merchant", qualifiedByName = "toMerchantDtoSummary")
	@Named("toUserDto")
	UserDto toUserDto(User user);

	@IterableMapping(qualifiedByName = "toUserDto")
	List<UserDto> toUserDtoList(List<User> users);

	@Mapping(target = "password", ignore = true)
	@Mapping(target = "merchant", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Named("toUserDtoSummary")
	UserDto toUserDtoSummary(User user);

	default User toUser(UserCreateRequest userCreateDto) {
		User user = new User();
		user.setFirstName(userCreateDto.getFirstName());
		user.setLastName(userCreateDto.getLastName());
		user.setEmail(userCreateDto.getEmail());
		user.setUsername(userCreateDto.getUsername());
		user.setPassword(userCreateDto.getPassword());
		user.setEnabled(BooleanUtils.isNotFalse(userCreateDto.getEnabled()));
		return user;
	}

}
