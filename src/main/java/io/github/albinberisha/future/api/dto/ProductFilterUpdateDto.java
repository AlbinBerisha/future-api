package io.github.albinberisha.future.api.dto;

import java.util.Map;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
public class ProductFilterUpdateDto {
	private Map<String, String> names;

	public Map<String, String> getNames() {
		return names;
	}

	public void setNames(Map<String, String> names) {
		this.names = names;
	}
}
