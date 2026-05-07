package io.github.albinberisha.future.api.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.albinberisha.future.api.entity.FileResource;
import io.github.albinberisha.future.api.entity.enums.ResourceOwnerType;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Repository
public interface FileResourceRepository extends JpaRepository<FileResource, UUID> {

	Set<FileResource> findByIdIn(Collection<UUID> ids);

	List<FileResource> findByOwnerTypeAndOwnerId(ResourceOwnerType ownerType, UUID ownerId);
}
