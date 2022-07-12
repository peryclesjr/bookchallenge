package com.alten.challenge.bookchallenge.services.serviceImpl;

import Utils.Converter;
import com.alten.challenge.bookchallenge.exception.BookChallengeException;
import com.alten.challenge.bookchallenge.model.Room;
import com.alten.challenge.bookchallenge.model.dto.RoomAvailabilityDTO;
import com.alten.challenge.bookchallenge.repository.RoomRepository;
import com.alten.challenge.bookchallenge.services.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static Utils.ReservationDTOValidator.validateFromToDatesForBooking;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }


    @Override
    public Page<RoomAvailabilityDTO> findAvailabilityByDateRange(LocalDate from, LocalDate to, Pageable pageable) throws BookChallengeException {

        
        if(validateFromToDatesForBooking(from,to)) {
            List<Room> rooms = roomRepository.findAvailabilityByFromTo(
                    from.atTime(0,0,0),
                    to.atTime(23,59,59),
                    pageable);
            List<RoomAvailabilityDTO> rooomAvailability =  Converter.roomAvailabilityDTOList(rooms);
            return new PageImpl<RoomAvailabilityDTO>(rooomAvailability, pageable,rooms.size());

        }
        return null;
    }

}
