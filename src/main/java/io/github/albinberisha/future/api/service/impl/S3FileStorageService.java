package io.github.albinberisha.future.api.service.impl;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.github.albinberisha.future.api.service.FileStorageService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Service
public class S3FileStorageService implements FileStorageService {
	@Autowired
	private S3Client s3Client;
	@Value("${aws.s3.bucket}")
	private String bucket;
	@Value("${aws.s3.region}")
	private String region;

	@Override
	public String upload(@NotBlank String path, @NotNull MultipartFile file) throws IOException {
		try (InputStream inputStream = file.getInputStream()) {
			PutObjectRequest request = PutObjectRequest.builder()
					.bucket(bucket)
					.key(path)
					.contentType(file.getContentType())
					.contentDisposition("inline")
					.build();
			s3Client.putObject(request, RequestBody.fromInputStream(inputStream, file.getSize()));
		}
		return getUrl(path);
	}

	private String getUrl(String path) {
		return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + path;
	}
}
