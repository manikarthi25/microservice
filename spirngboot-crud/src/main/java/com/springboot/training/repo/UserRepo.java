package com.springboot.training.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.training.dto.UserNamesDTO;
import com.springboot.training.eo.UserEO;

@Repository
public interface UserRepo extends JpaRepository<UserEO, Long> {

	UserEO findByFirstName(String firstName);
	
	List<UserEO> findByFirstName(String firstName, Pageable pageable);
	
	@Query("select new com.springboot.training.dto.UserNamesDTO(firstName, lastName) from UserEO")
	public List<UserNamesDTO> getUserFirstNameAndLastName();
	
	/*
	UserEO findByFirstNameIs(String firstName);
	
	UserEO findByFirstNameEquals(String firstName);
	
	List<UserEO> findDistinctByFirstName(String firstName);
	
	List<UserEO> findByFirstNameAndLastName(String firstName, String lastName);
	
	List<UserEO> findByFirstNameOrLastName(String firstName, String lastName);
	
	List<UserEO> findByAgeBetween(Long startingAge, Long endingAge);
	
	List<UserEO> findByAgeLessThan(Long age);
	
	List<UserEO> findByAgeLessThanEqual(Long age);
	
	List<UserEO> findByAgeGreaterThan(Long age);
	
	List<UserEO> findByAgeGreaterThanEqual(Long age);
	
	List<UserEO> findByAgeAfter(Long age);
	
	List<UserEO> findByAgeBefore(Long age);
	
	List<UserEO> findByAgeIs(Long age);
	
	List<UserEO> findByAgeIsNull(Long age);
	
	List<UserEO> findByAgeIsNotNull(Long age);
	
	List<UserEO> findByFirstNameStartingWith(String startingWith);
	
	List<UserEO> findByFirstNameEndingWith(String endingWith);
	
	List<UserEO> findByAgeNotIn(List<Long> ages);

	List<UserEO> findByAgeIn(List<Long> ages);
	
	List<UserEO> findByFirstNameIgnoreCase(String firstName);
	
	List<UserEO> findByAgeOrderByLastNameDesc(Long age);
	
	List<UserEO> findByLastNameNot(String lastName);
	
	List<UserEO> findByFirstNameContaining(String firstName);
	
	List<UserEO> findByFirstNameLike(String firstName);
	
	List<UserEO> findByFirstNameNotLike(String firstName);
	*/
	
	//findByActiveTrue()
	
	//findByActiveFalse()
	
}
