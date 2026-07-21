package io.github.albinberisha.future.api.service.impl;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import io.github.albinberisha.future.api.service.FileStorageService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

/**
 * @author Albin Berisha
 *
 */
@Service
@Validated
@Qualifier("s3FileStorageService")
@ConditionalOnProperty(name = "storage.provider", havingValue = "S3")
public class S3FileStorageService implements FileStorageService {
	@Autowired
	private S3Client s3Client;
	@Autowired
	private S3Presigner s3Presigner;
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
		return path;
	}

	@Override
	public String generateDownloadUrl(String path) {
		GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
				.getObjectRequest(b -> b.bucket(bucket).key(path))
				.signatureDuration(java.time.Duration.ofMinutes(15))
				.build();
		return s3Presigner.presignGetObject(presignRequest).url().toString();
	}
}
