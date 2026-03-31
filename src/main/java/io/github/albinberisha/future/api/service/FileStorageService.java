package io.github.albinberisha.future.api.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public interface FileStorageService {

	String upload(@NotBlank String path, @NotNull MultipartFile file) throws IOException;

}
