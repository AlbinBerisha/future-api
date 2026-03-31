package io.github.albinberisha.future.api.exception;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public class ApiException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7423747307563497584L;

	public ApiException() {
		super();
	}

	public ApiException(String message) {
		super(message);
	}

}
