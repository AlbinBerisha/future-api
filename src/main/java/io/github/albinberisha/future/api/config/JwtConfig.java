package io.github.albinberisha.future.api.config;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Configuration
public class JwtConfig {
	@Value("${jwt.secret}")
	private String secret;

	@Bean
	SecretKey secretKey() {
		return new SecretKeySpec(Base64.getDecoder().decode(secret), "HmacSHA256");
	}

}
