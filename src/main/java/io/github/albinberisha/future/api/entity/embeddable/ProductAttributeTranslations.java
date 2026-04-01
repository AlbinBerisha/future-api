package io.github.albinberisha.future.api.entity.embeddable;

import java.util.Objects;

import io.github.albinberisha.future.api.entity.enums.Language;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * @author Albin Berisha <albin.berisha@asseco-see.com>
 *
 */
@Embeddable
public class ProductAttributeTranslations {
	@Column(name = "name", length = 32, nullable = false)
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(name = "language", length = 16, nullable = false)
	private Language language;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@Override
	public int hashCode() {
		return Objects.hash(language, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductAttributeTranslations other = (ProductAttributeTranslations) obj;
		return language == other.language && Objects.equals(name, other.name);
	}
}
