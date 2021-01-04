package com.revature.dtos;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.revature.annotations.RegisterDTO;
import com.revature.models.User;
import com.revature.models.UserRole;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@RegisterDTO(User.class)
@JsonInclude(Include.NON_NULL)
public class UserDTO {

	@Id
	@Positive
	private int id;

	@NotBlank
	private String username;

	private String firstName;
	private String lastName;
	private String email;

	@NotNull
	private UserRole role;
}
