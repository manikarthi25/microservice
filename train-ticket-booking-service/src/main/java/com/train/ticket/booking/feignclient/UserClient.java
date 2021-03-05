package com.train.ticket.booking.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.train.ticket.booking.model.UserReponse;

//localhost:9001/user/40
//@FeignClient(value = "ticket-booking-ms",  url = "http://localhost:9001/user")
@FeignClient(name = "http://USER-MS/user")
public interface UserClient {

	@GetMapping("/{userId}")
	public ResponseEntity<UserReponse> getUserDetailsByUserId(@PathVariable Long userId);

}
