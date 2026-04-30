package io.github.albinberisha.future.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {
	private String message;
	private List<FieldError> errors;

	public ErrorResponse(String message) {
		this.message = message;
	}

	public ErrorResponse(String message, List<FieldError> errors) {
		this.message = message;
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public List<FieldError> getErrors() {
		return errors;
	}

	public static class FieldError {
		private String parameter;
		private String message;

		public FieldError(String parameter, String message) {
			this.parameter = parameter;
			this.message = message;
		}

		public String getParameter() {
			return parameter;
		}

		public String getMessage() {
			return message;
		}
	}
}
