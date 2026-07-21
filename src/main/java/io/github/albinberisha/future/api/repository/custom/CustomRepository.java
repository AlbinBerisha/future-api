package io.github.albinberisha.future.api.repository.custom;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Albin Berisha
 *
 */
public interface CustomRepository<T> {

	List<T> findAll(String entityGraphName);

	Optional<T> findById(UUID id, String entityGraphName);

}
