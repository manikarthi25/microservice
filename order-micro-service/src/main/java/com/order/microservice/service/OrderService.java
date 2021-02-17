package com.order.microservice.service;

import java.util.List;

import javax.validation.Valid;

import com.order.microservice.dto.Order;

public interface OrderService {

	List<Order> getAllOrders();

	Order getOrderByOrderId(Long orderId);

	Order addNewOrder(@Valid Order order);

	Order updateOrderByOrderId(@Valid Order order);

	List<Order> deleteOrderByOrderId(Long orderId);

	Order getOrderByUserId(Long userId);

}
