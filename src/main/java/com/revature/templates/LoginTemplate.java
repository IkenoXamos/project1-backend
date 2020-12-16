package com.revature.templates;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoginTemplate {

	@NotNull
	private String username;
	
	@NotNull
	private String password;
}
