package io.github.albinberisha.future.api.mapper;

import java.util.Collection;

import io.github.albinberisha.future.api.dto.FileResourceDto;
import io.github.albinberisha.future.api.entity.FileResource;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface FileResourceMapper {

	FileResourceDto toFileResourceDto(FileResource fileResource);

	default Collection<FileResourceDto> toFileResourceDtoList(Collection<FileResource> resources) {
		return resources.stream().map(this::toFileResourceDto).toList();
	}
}
