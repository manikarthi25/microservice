package com.train.ticket.booking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.train.ticket.booking.entity.TicketBookingHistoryEO;

@Repository
public interface TicketBookingHistoryRepo extends JpaRepository<TicketBookingHistoryEO, Long> {

}
