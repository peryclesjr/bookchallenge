package com.alten.challenge.bookchallenge.repository;

import com.alten.challenge.bookchallenge.model.Reservation;
import com.alten.challenge.bookchallenge.model.ReservationRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Optional<Reservation> findByIdAndStatus(Long id, Short status);

    Optional<Reservation> findById(Long reservationId);


    //Optional<ReservationRoom> findByReservationIdAndRoomId(UUID reservation, Long roomId);
    @Query ("select r from ReservationRoom r" +
            " where  r.reservation.id = :reservation and r.room = :id")
    Optional<ReservationRoom> findByReservationIdAndRoomId(Long reservation,  Long id);

}
