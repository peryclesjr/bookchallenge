package com.alten.challenge.bookchallenge.repository;

import com.alten.challenge.bookchallenge.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    //native query to find avaible rooms
    @Query(value = "select distinct room.* from room " +
            "left join reservation_room on room.id = reservation_room.room_id " +
            "where (" +
            "  room.status = 1" +
            "  and reservation_room.status = 1" +
            "  and ((reservation_room.stay_from between :initialDate and :endDate" +
            "          and reservation_room.stay_to between :initialDate and :endDate)" +
            "        or (reservation_room.stay_from < :initialDate and reservation_room.stay_to < :endDate))" +
            " ) " +
            "  or (room.status = 1 and reservation_room.status is null)",
            nativeQuery = true)
    List<Room> findAvailabilityByFromTo(@Param("initialDate") LocalDateTime initialDate,
                                        @Param("endDate") LocalDateTime endDate,
                                        Pageable pageable);
}
