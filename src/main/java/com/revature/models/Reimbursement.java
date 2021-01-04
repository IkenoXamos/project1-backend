package com.revature.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "author", "resolver" })
@EqualsAndHashCode(exclude = { "author", "resolver" })
public class Reimbursement {

	@Id
//	@PositiveOrZero
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

//	@PositiveOrZero
	private double amount;

	@JsonProperty(required = false)
	private LocalDateTime timeSubmitted;

	@Column(insertable = false)
	@JsonProperty(required = false)
	private LocalDateTime timeResolved;

	@JsonProperty(required = false)
	private String description;
	
	@Lob
	@JsonProperty(required = false)
	private byte[] receipt;

//	@NotNull
	private ReimbursementStatus status;

//	@NotNull
	private ReimbursementType type;

//	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private User author;

	@JsonProperty(required = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private User resolver;

	public void resolve(ReimbursementStatus status, User resolver) {
		this.timeResolved = LocalDateTime.now();
		this.status = status;
		this.resolver = resolver;
	}
}