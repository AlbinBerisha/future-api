package io.github.albinberisha.future.api.exception;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<JSONObject> handleApiException(ApiException e) {
		JSONObject response = new JSONObject()
				.appendField("message", e.getMessage());
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<JSONObject> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		JSONObject response = new JSONObject()
				.appendField("message", "Wrong or missing parameters")
				.appendField("errors", e.getBindingResult().getFieldErrors().stream().map(fieldError -> new JSONObject()
						.appendField("parameter", fieldError.getField())
						.appendField("message", fieldError.getDefaultMessage()))
						.collect(Collectors.toCollection(JSONArray::new)));
		return ResponseEntity.badRequest().body(response);
	}
}
