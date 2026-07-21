package io.github.albinberisha.future.api.entity.embeddable;

import java.util.Objects;

import io.github.albinberisha.future.api.entity.enums.Language;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * @author Albin Berisha
 *
 */
@Embeddable
public class ProductTranslations {
	@Column(name = "name", length = 32, nullable = false)
	private String name;
	@Column(name = "description", length = 255, nullable = false)
	private String description;
	@Enumerated(EnumType.STRING)
	@Column(name = "language", length = 16, nullable = false)
	private Language language;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, language, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductTranslations other = (ProductTranslations) obj;
		return Objects.equals(description, other.description) && language == other.language
				&& Objects.equals(name, other.name);
	}
}
