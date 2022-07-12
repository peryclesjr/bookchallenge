package com.alten.challenge.bookchallenge.serviceImpl;

import Utils.Converter;
import com.alten.challenge.bookchallenge.exception.BookChallengeException;
import com.alten.challenge.bookchallenge.model.Reservation;
import com.alten.challenge.bookchallenge.model.ReservationRoom;
import com.alten.challenge.bookchallenge.model.dto.*;
import com.alten.challenge.bookchallenge.model.enums.Bed;
import com.alten.challenge.bookchallenge.repository.ReservationRepository;
import com.alten.challenge.bookchallenge.repository.ReservationRoomRepository;
import com.alten.challenge.bookchallenge.services.serviceImpl.ReservationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class ReservationServiceImplTest {
    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationRoomRepository reservationRoomRepository;

    @Test
    void givenACorrectInformation_thenSucess() throws BookChallengeException {
        BookingDTO bookingDTO = BookingDTO.builder()
                .reservation(ReservationDTO.builder()
                        .guestPhone("5561981554958")
                        .guestEmail("perycles@gmail.com")
                        .guestName("Perycles Junior")
                        .status((short) 1)
                        .build()
                )
                .rooms(List.of(ReservationRoomDTO.builder()
                        .id(6L)
                        .room(RoomDTO.builder()
                                .id(12L)
                                .roomView("Sea View")
                                .roomType(RoomTypeDTO.builder()
                                        .id(2L)
                                        .name("Senior")
                                        .beds(List.of(RoomTypeBedDTO.builder()
                                                                .bedType(Bed.QUEEN)
                                                                .quantity((short)1)
                                                                .build(),
                                                        RoomTypeBedDTO.builder()
                                                                .bedType(Bed.SINGLE)
                                                                .quantity((short)1)
                                                                .build()
                                                )
                                        )
                                        .build()
                                )
                                .build()
                        )
                        .stayFrom(LocalDate.now().plusDays(1))
                        .stayTo(LocalDate.now().plusDays(3))
                        .status((short)1)
                        .build()
                ))
                .build();
        Reservation reservation = Converter.reservationDtoToEntity(bookingDTO.getReservation());
        reservation.setId(1L);
        List<ReservationRoom> reservationRooms = Converter.createReservationRoomsFromDto(bookingDTO.getRooms(), reservation);
        BDDMockito.when(reservationRepository.saveAndFlush(BDDMockito.any())).thenReturn(reservation);
        BDDMockito.when(reservationRoomRepository.findByRoomsAndDates(BDDMockito.any(),BDDMockito.any(), BDDMockito.any())).thenReturn(Collections.emptyList());
        BDDMockito.when(reservationRoomRepository.saveAll(BDDMockito.any())).thenReturn(reservationRooms);

        BookingDTO response = reservationService.book(bookingDTO);

        Assertions.assertEquals(reservation.getId(),response.getReservation().getId());
        Assertions.assertEquals(reservationRooms.size(), response.getRooms().size());
    }

    @Test
    void givenABookRoomNull_thenReceiveException() {

        BookChallengeException exception = Assertions.assertThrows(BookChallengeException.class, () -> {
            BookingDTO response = reservationService.book(null);
        });

        Assertions.assertEquals("No reservation object provided",exception.getApiError().getMessage());

    }

    @Test
    void givenBookVisitorNameNull_thenReceiveException() {
        BookingDTO bookingDTO = BookingDTO.builder()
                .reservation(ReservationDTO.builder()
                        .guestPhone("5561981554958")
                        .guestEmail("perycles@gmail.com")
                        .guestName(null)
                        .status((short) 1)
                        .build()
                )
                .rooms(List.of(ReservationRoomDTO.builder()
                        .id(5L)
                        .room(RoomDTO.builder()
                                .id(3L)
                                .roomView("Sea View")
                                .roomType(RoomTypeDTO.builder()
                                        .id(2L)
                                        .name("Senior")
                                        .beds(List.of(RoomTypeBedDTO.builder()
                                                                .bedType(Bed.QUEEN)
                                                                .quantity((short)1)
                                                                .build(),
                                                        RoomTypeBedDTO.builder()
                                                                .bedType(Bed.SINGLE)
                                                                .quantity((short)1)
                                                                .build()
                                                )
                                        )
                                        .build()
                                )
                                .build()
                        )
                        .stayFrom(LocalDate.now().plusDays(1))
                        .stayTo(LocalDate.now().plusDays(3))
                        .status((short)1)
                        .build()
                ))
                .build();
        BookChallengeException exception = Assertions.assertThrows(BookChallengeException.class, () -> reservationService.book(bookingDTO));


        Assertions.assertEquals("Invalid Reservation: You need to provide the 'visitorName'",exception.getApiError().getMessage());

    }

    @Test
    void givenBookVisitorNameEmpty_thenReceiveException() {
        BookingDTO bookingDTO = BookingDTO.builder()
                .reservation(ReservationDTO.builder()
                        .guestPhone("5561981554958")
                        .guestEmail("perycles@gmail.com")
                        .guestName("")
                        .status((short) 1)
                        .build()
                )
                .rooms(List.of(ReservationRoomDTO.builder()
                        .id(5L)
                        .room(RoomDTO.builder()
                                .id(3L)
                                .roomView("Sea View")
                                .roomType(RoomTypeDTO.builder()
                                        .id(2L)
                                        .name("Senior")
                                        .beds(List.of(RoomTypeBedDTO.builder()
                                                                .bedType(Bed.QUEEN)
                                                                .quantity((short)1)
                                                                .build(),
                                                        RoomTypeBedDTO.builder()
                                                                .bedType(Bed.SINGLE)
                                                                .quantity((short)1)
                                                                .build()
                                                )
                                        )
                                        .build()
                                )
                                .build()
                        )
                        .stayFrom(LocalDate.now().plusDays(1))
                        .stayTo(LocalDate.now().plusDays(3))
                        .status((short)1)
                        .build()
                ))
                .build();
        BookChallengeException exception = Assertions.assertThrows(BookChallengeException.class, () -> reservationService.book(bookingDTO));


        Assertions.assertEquals("Invalid Reservation: You need to provide the 'visitorName'",exception.getApiError().getMessage());

    }


    @Test
    void givenBookVisitorEmailEmpty_thenReceiveException() {
        BookingDTO bookingDTO = BookingDTO.builder()
                .reservation(ReservationDTO.builder()
                        .guestPhone("5561981554958")
                        .guestEmail("")
                        .guestName("Perycles Jr")
                        .status((short) 1)
                        .build()
                )
                .rooms(List.of(ReservationRoomDTO.builder()
                        .id(5L)
                        .room(RoomDTO.builder()
                                .id(3L)
                                .roomView("Sea View")
                                .roomType(RoomTypeDTO.builder()
                                        .id(2L)
                                        .name("Senior")
                                        .beds(List.of(RoomTypeBedDTO.builder()
                                                                .bedType(Bed.QUEEN)
                                                                .quantity((short)1)
                                                                .build(),
                                                        RoomTypeBedDTO.builder()
                                                                .bedType(Bed.SINGLE)
                                                                .quantity((short)1)
                                                                .build()
                                                )
                                        )
                                        .build()
                                )
                                .build()
                        )
                        .stayFrom(LocalDate.now().plusDays(1))
                        .stayTo(LocalDate.now().plusDays(3))
                        .status((short)1)
                        .build()
                ))
                .build();
        BookChallengeException exception = Assertions.assertThrows(BookChallengeException.class, () -> reservationService.book(bookingDTO));


        Assertions.assertEquals("Invalid Reservation: You need to provide the 'visitorEmail'",exception.getApiError().getMessage());

    }

    @Test
    void givenBookVisitorEmailNull_thenReceiveException() {
        BookingDTO bookingDTO = BookingDTO.builder()
                .reservation(ReservationDTO.builder()
                        .guestPhone("5561981554958")
                        .guestEmail(null)
                        .guestName("Perycles jr")
                        .status((short) 1)
                        .build()
                )
                .rooms(List.of(ReservationRoomDTO.builder()
                        .id(5L)
                        .room(RoomDTO.builder()
                                .id(3L)
                                .roomView("Sea View")
                                .roomType(RoomTypeDTO.builder()
                                        .id(2L)
                                        .name("Senior")
                                        .beds(List.of(RoomTypeBedDTO.builder()
                                                                .bedType(Bed.QUEEN)
                                                                .quantity((short)1)
                                                                .build(),
                                                        RoomTypeBedDTO.builder()
                                                                .bedType(Bed.SINGLE)
                                                                .quantity((short)1)
                                                                .build()
                                                )
                                        )
                                        .build()
                                )
                                .build()
                        )
                        .stayFrom(LocalDate.now().plusDays(1))
                        .stayTo(LocalDate.now().plusDays(3))
                        .status((short)1)
                        .build()
                ))
                .build();
        BookChallengeException exception = Assertions.assertThrows(BookChallengeException.class, () -> reservationService.book(bookingDTO));


        Assertions.assertEquals("Invalid Reservation: You need to provide the 'visitorEmail'",exception.getApiError().getMessage());
    }

    @Test
    void givenBookReservationNull_thenReceiveException() {
        BookingDTO bookingDTO = BookingDTO.builder()
                .reservation(null)
                .rooms(List.of(ReservationRoomDTO.builder()
                        .id(5L)
                        .room(RoomDTO.builder()
                                .id(3L)
                                .roomView("Sea View")
                                .roomType(RoomTypeDTO.builder()
                                        .id(2L)
                                        .name("Senior")
                                        .beds(List.of(RoomTypeBedDTO.builder()
                                                                .bedType(Bed.QUEEN)
                                                                .quantity((short)1)
                                                                .build(),
                                                        RoomTypeBedDTO.builder()
                                                                .bedType(Bed.SINGLE)
                                                                .quantity((short)1)
                                                                .build()
                                                )
                                        )
                                        .build()
                                )
                                .build()
                        )
                        .stayFrom(LocalDate.now().plusDays(1))
                        .stayTo(LocalDate.now().plusDays(3))
                        .status((short)1)
                        .build()
                ))
                .build();
        BookChallengeException exception = Assertions.assertThrows(BookChallengeException.class, () -> reservationService.book(bookingDTO));


        Assertions.assertEquals("Invalid Reservation: You need to provide Reservation object containing Visitor Information",exception.getApiError().getMessage());

    }

    @Test
    void givenBookFromMore30Days_thenReceiveInAdvanceException() {
        BookingDTO bookingDTO = BookingDTO.builder()
                .reservation(ReservationDTO.builder()
                        .guestPhone("5561981554958")
                        .guestEmail("perycles@gmail.com")
                        .guestName("Perycles jr")
                        .status((short) 1)
                        .build()
                )
                .rooms(List.of(ReservationRoomDTO.builder()
                        .id(6L)
                        .room(RoomDTO.builder()
                                .id(12L)
                                .roomView("Sea View")
                                .roomType(RoomTypeDTO.builder()
                                        .id(2L)
                                        .name("Senior")
                                        .beds(List.of(RoomTypeBedDTO.builder()
                                                                .bedType(Bed.QUEEN)
                                                                .quantity((short)1)
                                                                .build(),
                                                        RoomTypeBedDTO.builder()
                                                                .bedType(Bed.SINGLE)
                                                                .quantity((short)1)
                                                                .build()
                                                )
                                        )
                                        .build()
                                )
                                .build()
                        )
                        .stayFrom(LocalDate.now().plusDays(31))
                        .stayTo(LocalDate.now().plusDays(33))
                        .status((short)1)
                        .build()
                ))
                .build();
        BookChallengeException exception = Assertions.assertThrows(BookChallengeException.class, () ->reservationService.book(bookingDTO));


        Assertions.assertEquals("Invalid 'From' date provided for booking: You cannot book for more than 30 days in advance",exception.getApiError().getMessage());
    }

    @Test
    void givenBookFor10DaysTotalPeriod_thenReceiveInAdvanceException() {
        BookingDTO bookingDTO = BookingDTO.builder()
                .reservation(ReservationDTO.builder()
                        .guestPhone("5561981554958")
                        .guestEmail("perycles@gmail.com")
                        .guestName("Perycles jr")
                        .status((short) 1)
                        .build()
                )
                .rooms(List.of(ReservationRoomDTO.builder()
                        .id(6L)
                        .room(RoomDTO.builder()
                                .id(12L)
                                .roomView("Sea View")
                                .roomType(RoomTypeDTO.builder()
                                        .id(2L)
                                        .name("Senior")
                                        .beds(List.of(RoomTypeBedDTO.builder()
                                                                .bedType(Bed.QUEEN)
                                                                .quantity((short)1)
                                                                .build(),
                                                        RoomTypeBedDTO.builder()
                                                                .bedType(Bed.SINGLE)
                                                                .quantity((short)1)
                                                                .build()
                                                )
                                        )
                                        .build()
                                )
                                .build()
                        )
                        .stayFrom(LocalDate.now().plusDays(1))
                        .stayTo(LocalDate.now().plusDays(10))
                        .status((short)1)
                        .build()
                ))
                .build();
        BookChallengeException exception = Assertions.assertThrows(BookChallengeException.class, () ->reservationService.book(bookingDTO));


        Assertions.assertEquals("Invalid 'to' date provided for booking: Stay period for reservation cannot be more than 3 days",exception.getApiError().getMessage());
    }

}
