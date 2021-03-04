package com.train.ticket.booking.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.train.ticket.booking.dto.PassengerDTO;
import com.train.ticket.booking.dto.TicketBookingHistoryDTO;
import com.train.ticket.booking.dto.TrainDTO;
import com.train.ticket.booking.entity.PassengerEO;
import com.train.ticket.booking.entity.TicketBookingHistoryEO;
import com.train.ticket.booking.entity.TrainEO;
import com.train.ticket.booking.exception.TicketBookingBusinessException;
import com.train.ticket.booking.model.LastTicketBookingHistory;
import com.train.ticket.booking.repo.PassengerRepo;
import com.train.ticket.booking.repo.TicketBookingHistoryRepo;
import com.train.ticket.booking.service.TicketBookingService;

@Service
public class TicketBookingServiceImpl implements TicketBookingService {

	@Autowired
	private TicketBookingHistoryRepo ticketBookingHistoryRepo;

	@Autowired
	private PassengerRepo passengerRepo;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public LastTicketBookingHistory getLastTicketBookingHistory(Long userId) throws TicketBookingBusinessException {
		List<TicketBookingHistoryEO> ticketBookingHistoryEOList = ticketBookingHistoryRepo
				.findByUserIdOrderByTravelDateTimeDesc(userId);
		if (!CollectionUtils.isEmpty(ticketBookingHistoryEOList)) {
			return getLastTicketBookingHistory(mapToDTO(ticketBookingHistoryEOList.get(0)));
		}
		throw new TicketBookingBusinessException(userId + " : This user is not booking any ticket sofar");
	}

	private LastTicketBookingHistory getLastTicketBookingHistory(TicketBookingHistoryDTO ticketBookingHistoryDTO) {
		LastTicketBookingHistory lastTicketBookingHistory = new LastTicketBookingHistory();
		List<PassengerDTO> passengerList = new ArrayList<>();
		lastTicketBookingHistory.setTicketBookingId(ticketBookingHistoryDTO.getTicketBookingId());
		lastTicketBookingHistory.setUserId(ticketBookingHistoryDTO.getUserId());
		lastTicketBookingHistory.setDeparturePlace(ticketBookingHistoryDTO.getTrainDTO().getDeparturePlace());
		lastTicketBookingHistory.setArrivalPlace(ticketBookingHistoryDTO.getTrainDTO().getArrivalPlace());
		lastTicketBookingHistory.setViaPlace(ticketBookingHistoryDTO.getTrainDTO().getViaPlace());
		lastTicketBookingHistory.setBookingDateTime(ticketBookingHistoryDTO.getBookingDateTime());
		lastTicketBookingHistory.setTravelDateTime(ticketBookingHistoryDTO.getTravelDateTime());
		lastTicketBookingHistory.setTrainNumber(ticketBookingHistoryDTO.getTrainNumber());
		lastTicketBookingHistory.setTotalTicketFare(ticketBookingHistoryDTO.getTotalTicketFare());
		lastTicketBookingHistory.setTicketCount(ticketBookingHistoryDTO.getTicketCount());

		List<PassengerEO> passengerEOList = passengerRepo
				.findByTicketBookingId(ticketBookingHistoryDTO.getTicketBookingId());

		passengerEOList.forEach(passengerEO -> passengerList.add(mapToDTO(passengerEO)));

		lastTicketBookingHistory.setPassengerList(passengerList);
		return lastTicketBookingHistory;
	}

	private TicketBookingHistoryDTO mapToDTO(TicketBookingHistoryEO ticketBookingHistoryEO) {
		TicketBookingHistoryDTO ticketBookingHistoryDTO = modelMapper.map(ticketBookingHistoryEO,
				TicketBookingHistoryDTO.class);
		ticketBookingHistoryDTO.setTrainDTO(mapToDTO(ticketBookingHistoryEO.getTrainEO()));
		return ticketBookingHistoryDTO;
	}

	private TrainDTO mapToDTO(TrainEO trainEO) {
		return modelMapper.map(trainEO, TrainDTO.class);
	}

	private PassengerDTO mapToDTO(PassengerEO passengerEO) {
		return modelMapper.map(passengerEO, PassengerDTO.class);
	}

}
