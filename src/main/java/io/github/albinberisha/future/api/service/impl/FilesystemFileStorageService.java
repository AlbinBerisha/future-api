package io.github.albinberisha.future.api.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import io.github.albinberisha.future.api.service.FileStorageService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha
 *
 */
@Service
@Validated
@Qualifier("filesystemFileStorageService")
@ConditionalOnProperty(name = "storage.provider", havingValue = "FILESYSTEM")
public class FilesystemFileStorageService implements FileStorageService {
	@Value("${storage.filesystem.base-dir}")
	private String baseDir;

	@Override
	public String upload(@NotBlank String path, @NotNull MultipartFile file) throws IOException {
		Path target = resolve(path);
		Files.createDirectories(target.getParent());
		try (InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
		}
		return path;
	}

	@Override
	public InputStream download(String remoteFile) throws IOException {
		return Files.newInputStream(resolve(remoteFile));
	}

	private Path resolve(String path) throws IOException {
		Path basePath = Paths.get(baseDir).toAbsolutePath().normalize();
		Path target = basePath.resolve(path).normalize();
		if (!target.startsWith(basePath)) {
			throw new IOException("Resolved path escapes the storage base directory: " + path);
		}
		return target;
	}

}
