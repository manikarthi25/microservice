package com.bank.mani.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.bank.mani.entity.AccountEO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDTO {

	private Long customerId;

	@NotNull
	private String firstName;

	private String lastName;

	@NotNull
	@Positive
	@Size(min = 10, max = 12)
	private Long mobileNumber;

	@NotNull
	@Email
	private String email;

	@NotNull
	private String gender;

	private String doorNumber;

	private String street;

	private String district;

	private String state;

	private String country;

	@NotNull
	private Long pincode;

	private String createdBy;

	private LocalDateTime createdTS;

	private String updatedBy;

	private LocalDateTime updatedTS;

	private AccountEO accountEO;

}
