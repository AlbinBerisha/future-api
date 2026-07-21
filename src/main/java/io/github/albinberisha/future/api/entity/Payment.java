package io.github.albinberisha.future.api.entity;

import java.util.UUID;

import io.github.albinberisha.future.api.entity.enums.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Albin Berisha
 *
 */
@Entity
@Table(name = "payment")
public class Payment {
	@Id
	@Column(name = "id", nullable = false)
	private UUID id;
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method", nullable = false)
	private PaymentMethod paymentMethod;
}
