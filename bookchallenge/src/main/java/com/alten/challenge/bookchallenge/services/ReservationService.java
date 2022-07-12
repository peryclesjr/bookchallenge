package com.alten.challenge.bookchallenge.services;

import com.alten.challenge.bookchallenge.exception.BookChallengeException;
import com.alten.challenge.bookchallenge.model.dto.BookingDTO;
import com.alten.challenge.bookchallenge.model.dto.ReservationRoomDTO;

import java.util.Optional;
import java.util.UUID;

public interface ReservationService {
        BookingDTO book(BookingDTO reservation) throws BookChallengeException  ;
        BookingDTO modifyReservation(BookingDTO reservation) throws BookChallengeException;
        void cancelReservation(Long reservationId) ;
        BookingDTO getReservation(Long reservationId) ;
}
