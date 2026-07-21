package io.github.albinberisha.future.api.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha
 *
 */
public interface FileStorageService {

	String upload(@NotBlank String path, @NotNull MultipartFile file) throws IOException;

	default InputStream download(String remoteFile) throws IOException {
		throw new UnsupportedOperationException();
	}

	default String generateDownloadUrl(String path) {
		throw new UnsupportedOperationException();
	}

}
