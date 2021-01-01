package com.revature.dtos;

import javax.validation.constraints.NotNull;

import com.revature.models.ReimbursementStatus;
import com.revature.models.User;

import lombok.Data;

@Data
public class ResolveReceiptDTO {

	@NotNull
	private ReimbursementStatus status;
	
	@NotNull
	private User resolver;
}
