package io.github.albinberisha.future.api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public class AuthenticationRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
