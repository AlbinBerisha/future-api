package io.github.albinberisha.future.api.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Entity
@Table(name = "\"order\"")
public class Order {
	@Id
	@Column(name = "id", length = 36, nullable = false)
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
