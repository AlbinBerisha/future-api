package io.github.albinberisha.future.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

/**
 * @author Albin Berisha
 *
 */
@Configuration
@ConditionalOnProperty(name = "storage.provider", havingValue = "S3")
public class AwsConfig {
	@Value("${aws.access.key}")
	private String accessKey;
	@Value("${aws.secret.access.key}")
	private String secretAccessKey;
	@Value("${aws.s3.region}")
	private String s3Region;

	@Bean
	S3Client s3Client() {
		AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretAccessKey);
		return S3Client.builder()
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				.region(Region.of(s3Region))
				.build();
	}

	@Bean
	S3Presigner s3Presigner() {
		AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretAccessKey);
		return S3Presigner.builder()
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				.region(Region.of(s3Region))
				.build();
	}

}
