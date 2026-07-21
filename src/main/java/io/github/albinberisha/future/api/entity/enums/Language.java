package io.github.albinberisha.future.api.entity.enums;

/**
 * @author Albin Berisha
 *
 */
public enum Language {
	ENGLISH("en"),
	ALBANIAN("sq");
	private final String code;

	private Language(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static Language getByCode(String code) {
		for (Language language : Language.values())
			if (language.code.equals(code))
				return language;
		return null;
	}
}
