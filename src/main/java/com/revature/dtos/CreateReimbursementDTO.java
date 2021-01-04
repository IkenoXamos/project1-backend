package com.revature.dtos;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReimbursementDTO {

	@NotNull
	private UserDTO author;
	
	@JsonProperty(required = false)
	private String description;
	
	@JsonProperty(required = false)
	private byte[] receipt;
	
	@PositiveOrZero
	private double amount;
	
	@NotNull
	private ReimbursementType type;
	
	@JsonIgnore
	private ReimbursementStatus status = ReimbursementStatus.Pending;
	
	@JsonIgnore
	private LocalDateTime timesubmitted = LocalDateTime.now();
}
