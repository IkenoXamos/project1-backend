package com.revature.dtos;

import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.revature.annotations.RegisterDTO;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@RegisterDTO(Reimbursement.class)
@JsonInclude(Include.NON_NULL)
public class ReimbursementDTO {

	@Id
	@Positive
	private int id;

	@PositiveOrZero
	private double amount;

//	private LocalDateTime timeSubmitted;
//	private LocalDateTime timeResolved;
	private String description;
	private byte[] receipt;

	@NotNull
	private ReimbursementStatus status;

	@NotNull
	private ReimbursementType type;

	@NotNull
	private UserDTO author;

	private UserDTO resolver;
}
