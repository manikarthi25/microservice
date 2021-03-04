package com.train.ticket.booking.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.train.ticket.booking.model.TicketBooking;
import com.train.ticket.booking.repo.PassengerRepo;
import com.train.ticket.booking.repo.TicketBookingHistoryRepo;
import com.train.ticket.booking.repo.TrainRepo;
import com.train.ticket.booking.service.TicketBookingService;

@Service
public class TicketBookingServiceImpl implements TicketBookingService {

	@Autowired
	private TicketBookingHistoryRepo ticketBookingHistoryRepo;

	@Autowired
	private PassengerRepo passengerRepo;

	@Autowired
	private TrainRepo trainRepo;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public LastTicketBookingHistory bookTrainTicket(TicketBooking ticketBooking) throws TicketBookingBusinessException {
		TrainEO trainEO = checkTrainAvailability(ticketBooking);
		Double totalTicketFare = ticketBooking.getTicketCount() * trainEO.getTicketFare();
		TicketBookingHistoryEO ticketBookingHistoryEO = getTicketBookingDetails(ticketBooking, trainEO,
				totalTicketFare);

		trainRepo.saveAndFlush(trainEO);
		TicketBookingHistoryEO ticketBookingHistoryEORes = ticketBookingHistoryRepo.save(ticketBookingHistoryEO);

		List<PassengerEO> passengerEOList = new ArrayList<>();
		List<PassengerDTO> passengerDTOList = ticketBooking.getPassengerList();
		passengerDTOList.forEach(passengerDTO -> passengerEOList.add(mapToEO(passengerDTO, ticketBookingHistoryEORes)));

		for (PassengerEO passengerEO : passengerEOList) {
			passengerRepo.save(passengerEO);
		}
		return getLastTicketBookingHistory(ticketBooking.getUserId());
	}

	private TicketBookingHistoryEO getTicketBookingDetails(TicketBooking ticketBooking, TrainEO trainEO,
			Double totalTicketFare) {
		TicketBookingHistoryEO ticketBookingHistoryEO = new TicketBookingHistoryEO();
		ticketBookingHistoryEO.setTrainNumber(ticketBooking.getTrainNumber());
		ticketBookingHistoryEO.setUserId(ticketBooking.getUserId());
		ticketBookingHistoryEO.setTravelDateTime(ticketBooking.getTravelDateTime());
		ticketBookingHistoryEO.setBookingDateTime(LocalDateTime.now());
		ticketBookingHistoryEO.setTicketCount(ticketBooking.getTicketCount());
		ticketBookingHistoryEO.setTotalTicketFare(totalTicketFare);
		ticketBookingHistoryEO.setTrainEO(trainEO);
		return ticketBookingHistoryEO;
	}

	private TrainEO checkTrainAvailability(TicketBooking ticketBooking) throws TicketBookingBusinessException {
		Optional<TrainEO> optionalTrainEO = trainRepo.findById(ticketBooking.getTrainNumber());
		if (optionalTrainEO.isPresent()) {
			TrainEO trainEO = optionalTrainEO.get();
			return checkSeatAvailability(ticketBooking, trainEO);
		} else {
			throw new TicketBookingBusinessException(ticketBooking.getTrainNumber() + " : Invalid train number");
		}
	}

	private TrainEO checkSeatAvailability(TicketBooking ticketBooking, TrainEO trainEO)
			throws TicketBookingBusinessException {
		if (ticketBooking.getDeparturePlace().equals(trainEO.getDeparturePlace())
				&& ticketBooking.getArrivalPlace().equals(trainEO.getArrivalPlace())) {
			if (ticketBooking.getTicketCount() <= trainEO.getAvailableSeat()) {
				Long updateAvailableSeat = trainEO.getAvailableSeat() - ticketBooking.getTicketCount();
				trainEO.setAvailableSeat(updateAvailableSeat);
			} else {
				throw new TicketBookingBusinessException("Seats are not available");
			}
		} else {
			throw new TicketBookingBusinessException("Check your Departure Place and Arrival Place. "
					+ trainEO.getTrainNumber() + " : This train Departure Place is : " + trainEO.getDeparturePlace()
					+ " This train Arrival Place is " + trainEO.getArrivalPlace());
		}
		return trainEO;
	}

	@Override
	public LastTicketBookingHistory getLastTicketBookingHistory(Long userId) throws TicketBookingBusinessException {
		List<TicketBookingHistoryEO> ticketBookingHistoryEOList = ticketBookingHistoryRepo
				.findByUserIdOrderByBookingDateTimeDesc(userId);
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

	private PassengerEO mapToEO(PassengerDTO passengerDTO, TicketBookingHistoryEO ticketBookingHistoryEO) {
		PassengerEO passengerEO = modelMapper.map(passengerDTO, PassengerEO.class);
		passengerEO.setTicketBookingId(ticketBookingHistoryEO.getTicketBookingId());
		passengerEO.setTicketBookingHistoryEO(ticketBookingHistoryEO);
		return passengerEO;
	}

}