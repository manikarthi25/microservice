package com.order.microservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.order.microservice.entity.OrderEO;

@Repository
public interface OrderRepo extends JpaRepository<OrderEO, Long> {

	Optional<OrderEO> findByUserId(Long userId);

}
