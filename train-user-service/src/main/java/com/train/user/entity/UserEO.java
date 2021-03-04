package com.train.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "user")
public class UserEO implements Serializable {

	private static final long serialVersionUID = 6318005847106391949L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, length = 10)
	private String firstName;

	@Column(nullable = false, length = 10)
	private String lastName;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String userId;

	@Column(nullable = false)
	private String encryptedPassword;

}
