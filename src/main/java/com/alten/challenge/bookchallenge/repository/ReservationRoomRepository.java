package com.alten.challenge.bookchallenge.repository;

import com.alten.challenge.bookchallenge.model.Reservation;
import com.alten.challenge.bookchallenge.model.ReservationRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRoomRepository extends JpaRepository<ReservationRoom, Long> {

    @Query("Select rr From ReservationRoom rr JOIN rr.room r where r.id = :roomId " +
            "AND (:initialDate BETWEEN rr.stayFrom AND rr.stayTo " +
            "OR :endDate BETWEEN rr.stayFrom AND rr.stayTo) " +
            "AND rr.status = 1")
    List<ReservationRoom> findByRoomsAndDates(@Param("roomId") Long roomId, @Param("initialDate")LocalDateTime initialDate, @Param("endDate") LocalDateTime endDate);

    @Query("Select rr From ReservationRoom rr JOIN rr.room r where r.id = :roomId " +
            "AND (:initialDate BETWEEN rr.stayFrom AND rr.stayTo " +
            "OR :endDate BETWEEN rr.stayFrom AND rr.stayTo) " +
            "AND rr.status = 1 AND rr.id != :resevRoomId")
    List<ReservationRoom> findByIdAndRoomsAndDates(@Param("resevRoomId")Long reservationRoomId, @Param("roomId") Long roomId,
                                                   @Param("initialDate")LocalDateTime initialDate, @Param("endDate") LocalDateTime endDate);


    //to keep the code easy to read and better maintainable
    @Query("Select rr From Reservation rr " +
            "where rr.id = :reservation ")
    Optional<Reservation> findByReservationId(Long reservation);
}
