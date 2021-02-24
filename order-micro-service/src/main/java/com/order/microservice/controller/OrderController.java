package com.order.microservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.microservice.dto.Order;
import com.order.microservice.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/getorders")
	public ResponseEntity<List<Order>> getAllOrders() {
		return getOrdersRespone(orderService.getAllOrders());
	}

	@GetMapping("/search/{orderId}")
	public ResponseEntity<Order> getOrderByOrderId(@PathVariable Long orderId) {
		return getOrderRespone(orderService.getOrderByOrderId(orderId));
	}

	@PostMapping("/addorder")
	public ResponseEntity<Order> addNewOrder(@Valid @RequestBody Order order) {
		return getOrderRespone(orderService.addNewOrder(order));
	}

	@PutMapping("/updateorder")
	public ResponseEntity<Order> updateOrderByOrderId(@Valid @RequestBody Order order) {
		return getOrderRespone(orderService.updateOrderByOrderId(order));
	}

	@DeleteMapping("/deleteorder/{orderId}")
	public ResponseEntity<List<Order>> deleteOrderByOrderId(@PathVariable Long orderId) {
		return getOrdersRespone(orderService.deleteOrderByOrderId(orderId));
	}
	
	@GetMapping("/getorder/{userId}")
	public ResponseEntity<Order> getOrderByUserId(@PathVariable Long userId) {
		return getOrderRespone(orderService.getOrderByUserId(userId));
	}

	private ResponseEntity<Order> getOrderRespone(Order order) {
		if (ObjectUtils.isNotEmpty(order)) {
			return new ResponseEntity<Order>(order, HttpStatus.OK);
		} else {
			return new ResponseEntity<Order>(order, HttpStatus.NO_CONTENT);
		}
	}

	private ResponseEntity<List<Order>> getOrdersRespone(List<Order> orderList) {
		if (!CollectionUtils.isEmpty(orderList)) {
			return new ResponseEntity<List<Order>>(orderList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Order>>(orderList, HttpStatus.NO_CONTENT);
		}
	}

}
