package com.alten.challenge.bookchallenge.services.serviceImpl;

import Utils.Converter;
import Utils.ReservationDTOValidator;
import com.alten.challenge.bookchallenge.exception.BookChallengeApiExceptionError;
import com.alten.challenge.bookchallenge.exception.BookChallengeException;
import com.alten.challenge.bookchallenge.model.Reservation;
import com.alten.challenge.bookchallenge.model.ReservationRoom;
import com.alten.challenge.bookchallenge.model.dto.BookingDTO;
import com.alten.challenge.bookchallenge.model.dto.ReservationRoomDTO;
import com.alten.challenge.bookchallenge.repository.ReservationRepository;
import com.alten.challenge.bookchallenge.repository.ReservationRoomRepository;
import com.alten.challenge.bookchallenge.services.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    ReservationRoomRepository reservationRoomRepositoryepository;
    ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRoomRepository repository, ReservationRepository reservationRepository ){

        this.reservationRoomRepositoryepository = repository;
        this.reservationRepository = reservationRepository;
    }


    @Override
    public BookingDTO book(BookingDTO reservation) throws BookChallengeException {
        if(ReservationDTOValidator.validateReservationDto(reservation)){
            if(Objects.nonNull(reservation.getReservation().getId())){
                return this.modifyReservation(reservation);
            }else{
                List<ReservationRoomDTO> overlapRooms = reservation.getRooms().stream()
                        .map(reservationRoomDTO -> {
                            reservationRoomDTO.setId(null);
                            return reservationRoomDTO;
                        })
                        .collect(Collectors.toList());
                Reservation savedReservation = reservationRepository.saveAndFlush(Converter.reservationDtoToEntity(reservation.getReservation()));
                List<ReservationRoom> reservationRooms = Converter.createReservationRoomsFromDto(reservation.getRooms(), savedReservation);
                return Converter.reservationRoomsToBookingDto(reservationRoomRepositoryepository.saveAll(reservationRooms));

            }
        }
        return null;
    }

    @Override
    public BookingDTO modifyReservation(BookingDTO reservation) throws BookChallengeException {

        if(ReservationDTOValidator.validateReservationDto(reservation)){
            if(Objects.nonNull(reservation.getReservation().getId())){
                Optional<Reservation> reservationEntity = reservationRepository.findByIdAndStatus(reservation.getReservation().getId(), (short) 1);
                if(reservationEntity.isPresent()){
                    reservationEntity.get().setStatus(reservation.getReservation().getStatus());
                    reservationEntity = Optional.of(reservationRepository.saveAndFlush(reservationEntity.get()));
                    List<ReservationRoom> reservationRooms = Converter.createReservationRoomsFromDto(reservation.getRooms(),reservationEntity.get());
                    return Converter.reservationRoomsToBookingDto(reservationRoomRepositoryepository.saveAll(reservationRooms));

                }
            }
        }
        return null;
    }

    @Override
    public void cancelReservation(Long reservationId) {
        Optional<Reservation> reservationEntity = Optional.ofNullable(
                reservationRepository.findById(reservationId)
                                        .orElseThrow(() -> new EntityNotFoundException("Reservation not Found"))
                );

        if(reservationEntity.isPresent()){
            reservationEntity.get().setStatus((short) 0);
            reservationEntity.get()
                    .getReservationRooms().stream()
                    .forEach(reservationRoom -> {
                        reservationRoom.setStatus((short) 0);
                    });
            reservationRepository.saveAndFlush(reservationEntity.get());
        }
    }

    @Override
    public BookingDTO getReservation(Long reservationId) {
        Reservation reservation = reservationRoomRepositoryepository.findByReservationId(reservationId)
                .orElseThrow( () -> new EntityNotFoundException("Reservation not Found"));

        return Converter.reservationToBookingDTO(reservation);
    }

}