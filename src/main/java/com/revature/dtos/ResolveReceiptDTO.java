package com.revature.dtos;

import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.models.ReimbursementStatus;
import com.revature.models.User;

import lombok.Data;

@Data
public class ResolveReceiptDTO {
	
	@Id
	@Positive
	private int id;

	@NotNull
	private ReimbursementStatus status;
	
	@NotNull
	private User resolver;
	
	@JsonIgnore
	private LocalDateTime timeResolved = LocalDateTime.now();
}
