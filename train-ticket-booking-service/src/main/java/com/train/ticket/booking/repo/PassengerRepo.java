package com.train.ticket.booking.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.train.ticket.booking.entity.PassengerEO;

@Repository
public interface PassengerRepo extends JpaRepository<PassengerEO, Long> {

	List<PassengerEO> findByTicketBookingId(Long ticketBookingId);

}
