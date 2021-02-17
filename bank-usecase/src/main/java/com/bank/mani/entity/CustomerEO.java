package com.bank.mani.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "customer")
public class CustomerEO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "customer_id")
	private Long customerId;

	@NotNull
	private String firstName;

	private String lastName;

	@Column(unique = true)
	@Size(max = 12, min = 10)
	private Long mobileNumber;

	@NotNull
	@Email
	@Column(unique = true)
	private String email;

	@NotNull
	private String gender;

	private String doorNumber;

	private String street;

	@NotNull
	private String district;

	@NotNull
	private String state;

	@NotNull
	private String country;

	@NotNull
	private Long pincode;

	private String createdBy;

	@Column(name = "created_ts")
	private LocalDateTime createdTS;

	private String updatedBy;

	@Column(name = "updated_ts")
	private LocalDateTime updatedTS;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "customerEO")
	private AccountEO accountEO;

}

/*
 * 
 * A one-to-one relationship is defined using JPA’s @OneToOne annotation. It
 * accepts several attributes. Let’s understand what those attributes are meant
 * for -
 * 
 * fetch = FetchType.LAZY - Fetch the related entity lazily from the database.
 * 
 * cascade = CascadeType.ALL - Apply all cascading effects to the related
 * entity. That is, whenever we update/delete a CustomerEO entity, update/delete
 * the corresponding AccountEO as well.
 * 
 * mappedBy = “customerEO” - We use mappedBy attribute in the CustomerEO entity
 * to tell hibernate that the CustomerEO entity is not responsible for this
 * relationship and It should look for a field named customerEO in the AccountEO
 * entity to find the configuration for the JoinColumn/ForeignKey column.
 * 
 * The owner of the relationship contains a @JoinColumn annotation to specify
 * the foreign key column, and the inverse-side of the relationship contains a
 * mappedBy attribute to indicate that the relationship is mapped by the other
 * entity.
 */