package com.user.microservice.feignclient;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.user.microservice.dto.Order;

//http://localhost:9002/microservice/order/getorder/1
@FeignClient(value = "order-service", url = "http://localhost:9002/microservice/order")
public interface OrderClient {

	@GetMapping("/getorder/{userId}")
	public ResponseEntity<Order> getOrderByUserId(@PathVariable Long userId);

	@PutMapping("/updateorder")
	public ResponseEntity<Order> updateOrderByOrderId(@Valid @RequestBody Order order);

}
