package com.order.microservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.microservice.dto.Order;
import com.order.microservice.entity.OrderEO;
import com.order.microservice.repo.OrderRepo;
import com.order.microservice.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<Order> getAllOrders() {
		List<Order> orderList = new ArrayList<>();
		List<OrderEO> orderEOList = orderRepo.findAll();
		orderEOList.forEach(orderEO -> orderList.add(mapToDTO(orderEO)));
		return orderList;
	}

	@Override
	public Order getOrderByOrderId(Long orderId) {
		Optional<OrderEO> orderEO = orderRepo.findById(orderId);
		if (orderEO.isPresent()) {
			return mapToDTO(orderEO.get());
		} else {
			return null;
		}
	}

	@Override
	public Order addNewOrder(Order order) {
		OrderEO orderEO = orderRepo.save(mapToEO(order));
		return mapToDTO(orderEO);
	}

	@Override
	public Order updateOrderByOrderId(Order order) {
		Order orderResponse = getOrderByOrderId(order.getOrderId());
		if (null != orderResponse) {
			OrderEO orderEO = new OrderEO();
			orderEO.setOrderId(order.getOrderId());
			orderEO.setProductName(order.getProductName());
			orderEO.setProductPrice(order.getProductPrice());
			orderEO.setUserId(order.getUserId());
			return mapToDTO(orderRepo.saveAndFlush(orderEO));
		}
		return null;
	}

	@Override
	public List<Order> deleteOrderByOrderId(Long orderId) {
		Optional<OrderEO> orderEO = orderRepo.findById(orderId);
		if (orderEO.isPresent()) {
			orderRepo.deleteById(orderId);
			return getAllOrders();
		}
		return null;
	}

	@Override
	public Order getOrderByUserId(Long userId) {
		Optional<OrderEO> orderEO = orderRepo.findByUserId(userId);
		if (orderEO.isPresent()) {
			return mapToDTO(orderEO.get());
		} else {
			return null;
		}
	}

	private OrderEO mapToEO(Order order) {
		return modelMapper.map(order, OrderEO.class);
	}

	private Order mapToDTO(OrderEO orderEO) {
		return modelMapper.map(orderEO, Order.class);
	}

}
