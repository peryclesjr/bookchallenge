package com.alten.challenge.bookchallenge.serviceImpl;



import com.alten.challenge.bookchallenge.exception.BookChallengeException;
import com.alten.challenge.bookchallenge.model.ReservationRoom;
import com.alten.challenge.bookchallenge.model.Room;
import com.alten.challenge.bookchallenge.model.RoomType;
import com.alten.challenge.bookchallenge.model.RoomTypeBed;
import com.alten.challenge.bookchallenge.model.dto.RoomAvailabilityDTO;
import com.alten.challenge.bookchallenge.model.enums.Bed;
import com.alten.challenge.bookchallenge.repository.RoomRepository;
import com.alten.challenge.bookchallenge.services.serviceImpl.RoomServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
public class RoomServiceImplTest {

    @InjectMocks
    private RoomServiceImpl roomService;

    @Mock
    private RoomRepository roomRepository;

    @Test
    void givenAnResearchMoreThanThreeDays_whenThrowsException() throws BookChallengeException {

        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate to = LocalDate.now().plusDays(11);
        final String expectedMessageError = "Invalid 'to' date provided for booking: Stay period for reservation cannot be more than 3 days" ;

        Room room = Room.builder()
                .id(12L)
                .roomView("Sea View")
                .roomType(RoomType.builder()
                        .id(2L)
                        .name("Senior")
                        .beds(List.of(RoomTypeBed.builder()
                                                .bedType(Bed.QUEEN)
                                                .quantity((short)1)
                                                .build(),
                                        RoomTypeBed.builder()
                                                .bedType(Bed.SINGLE)
                                                .quantity((short)1)
                                                .build()
                                )
                        )
                        .build()
                )
                .reservationRooms(new ArrayList<>())
                .build();
        Pageable pageable = PageRequest.of(0, 25);
        PageImpl page = new PageImpl(List.of(room),pageable,1);



        BDDMockito.when(roomRepository.findAvailabilityByFromTo(BDDMockito.any(), BDDMockito.any() , BDDMockito.any()))
                .thenReturn(page.getContent());
        final var actualException  = Assertions.assertThrows(BookChallengeException.class, ()-> roomService.findAvailabilityByDateRange(from, to, pageable));
        Assertions.assertEquals(expectedMessageError, actualException.getApiError().getMessage());

    }

    @Test
    void givenAnResearchMoreThan_30_DaysBefore_whenThrowsException() throws BookChallengeException {

        LocalDate from = LocalDate.now().plusDays(32);
        LocalDate to = LocalDate.now().plusDays(33);
        final String expectedMessageError = "Invalid 'From' date provided for booking: You cannot book for more than 30 days in advance" ;

        Room room = Room.builder()
                .id(12L)
                .roomView("Sea View")
                .roomType(RoomType.builder()
                        .id(2L)
                        .name("Senior")
                        .beds(List.of(RoomTypeBed.builder()
                                                .bedType(Bed.QUEEN)
                                                .quantity((short)1)
                                                .build(),
                                        RoomTypeBed.builder()
                                                .bedType(Bed.SINGLE)
                                                .quantity((short)1)
                                                .build()
                                )
                        )
                        .build()
                )
                .reservationRooms(new ArrayList<>())
                .build();
        Pageable pageable = PageRequest.of(0, 25);
        PageImpl page = new PageImpl(List.of(room),pageable,1);



        BDDMockito.when(roomRepository.findAvailabilityByFromTo(BDDMockito.any(), BDDMockito.any() , BDDMockito.any()))
                .thenReturn(page.getContent());
        final var actualException  = Assertions.assertThrows(BookChallengeException.class, ()-> roomService.findAvailabilityByDateRange(from, to, pageable));
        Assertions.assertEquals(expectedMessageError, actualException.getApiError().getMessage());

    }

    @Test
    void givenATwoDatesCorrect_whenSuccess() throws BookChallengeException {

        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate to = LocalDate.now().plusDays(2);
        Room room = Room.builder()
                .id(12L)
                .roomView("Sea View")
                .roomType(RoomType.builder()
                        .id(2L)
                        .name("Senior")
                        .beds(List.of(RoomTypeBed.builder()
                                                .bedType(Bed.QUEEN)
                                                .quantity((short)1)
                                                .build(),
                                        RoomTypeBed.builder()
                                                .bedType(Bed.SINGLE)
                                                .quantity((short)1)
                                                .build()
                                )
                        )
                        .build()
                )
                .status((short)1)
                .reservationRooms(List.of(
                                ReservationRoom.builder()
                                        .id(2L)
                                        .status((short) 1)
                                        .stayFrom(LocalDateTime.from(LocalDate.now().plusDays(3).atTime(0,0,0)))
                                        .stayTo(LocalDateTime.from(LocalDate.now().plusDays(5).atTime(23,59,59)))
                                        .build(),
                                ReservationRoom.builder()
                                        .id(2L)
                                        .status((short) 1)
                                        .stayFrom(LocalDateTime.from(LocalDate.now().plusDays(7).atTime(0,0,0)))
                                        .stayTo(LocalDateTime.from(LocalDate.now().plusDays(10).atTime(23,59,59)))
                                        .build()
                        )
                )
                .build();
        Pageable pageable = PageRequest.of(0, 25);
        PageImpl page = new PageImpl(List.of(room),pageable,1);

        List<LocalDate> availability = from.datesUntil(to.plusDays(1))
                .collect(Collectors.toList());
        availability.removeAll(room.getReservationRooms().get(0).getStayFrom().toLocalDate()
                .datesUntil(room.getReservationRooms().get(0).getStayTo().toLocalDate().plusDays(1))
                .collect(Collectors.toList())
        );

        availability.removeAll(room.getReservationRooms().get(1).getStayFrom().toLocalDate()
                .datesUntil(room.getReservationRooms().get(1).getStayTo().toLocalDate().plusDays(1))
                .collect(Collectors.toList())
        );

        BDDMockito.when(roomRepository.findAvailabilityByFromTo(BDDMockito.any(), BDDMockito.any() , BDDMockito.any()))
                .thenReturn(page.getContent());
        Page<RoomAvailabilityDTO> response = roomService.findAvailabilityByDateRange(from, to, pageable);
        Assertions.assertEquals(1, response.get().count());

    }

}
