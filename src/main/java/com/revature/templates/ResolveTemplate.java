package com.revature.templates;

import javax.validation.constraints.NotNull;

import com.revature.models.ReimbursementStatus;
import com.revature.models.User;

import lombok.Data;

@Data
public class ResolveTemplate {

	@NotNull
	private ReimbursementStatus status;
	
	@NotNull
	private User resolver;
}
