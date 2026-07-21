package io.github.albinberisha.future.api.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Albin Berisha
 *
 */
public class ApiException extends RuntimeException {
	private static final long serialVersionUID = -7423747307563497584L;

	private final HttpStatus status;

	public ApiException(String message) {
		this(message, HttpStatus.BAD_REQUEST);
	}

	public ApiException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}
}
