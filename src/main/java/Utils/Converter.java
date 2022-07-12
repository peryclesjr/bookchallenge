package Utils;

import com.alten.challenge.bookchallenge.model.*;
import com.alten.challenge.bookchallenge.model.dto.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Converter {

    public Converter() {

    }

    public static Room roomDtoToRoom(RoomDTO roomDTO) {
        if (Objects.nonNull(roomDTO)) {
            return Room.builder()
                    .roomView(roomDTO.getRoomView())
                    .roomType(Converter.roomTypeDtoToRoomType(roomDTO.getRoomType()))
                    .id(roomDTO.getId())
                    .build();
        }
        return null;
    }

    public static RoomDTO roomDtoToRoom(Room room) {
        if (Objects.nonNull(room)) {
            return RoomDTO.builder()
                    .roomView(room.getRoomView())
                    .roomType(Converter.roomTypeToRoomTypeDTO(room.getRoomType()))
                    .id(room.getId())
                    .build();
        }
        return null;
    }

    public static RoomType roomTypeDtoToRoomType(RoomTypeDTO roomTypeDTO) {
//        if (Objects.nonNull(roomTypeDTO)) {
//            return RoomType.builder()
//                    .beds(roomTypeDTO.getBeds().stream()
//                            .map(Converter::roomTypeBedDtoToRoomTypeBed)
//                            .collect(Collectors.toList())
//                    )
//                    .id(roomTypeDTO.getId())
//                    .name(roomTypeDTO.getName())
//                    .occupants(roomTypeDTO.getOccupants())
//                    .build();
//        }
        return null;
    }

    public static RoomTypeBed roomTypeBedDtoToRoomTypeBed(RoomTypeBedDTO roomTypeBedDTO) {
        if (Objects.nonNull(roomTypeBedDTO)) {
            return RoomTypeBed.builder()
                    .bedType(roomTypeBedDTO.getBedType())
                    .id(roomTypeBedDTO.getId())
                    .quantity(roomTypeBedDTO.getQuantity())
                    .build();
        }
        return null;
    }

    public static RoomTypeDTO roomTypeToRoomTypeDTO(RoomType roomType) {
//        if (Objects.nonNull(roomType)) {
//            return RoomTypeDTO.builder()
//                    .beds(roomType.getBeds().stream()
//                            .map(Converter::roomTypeBedToRoomTypeBedDto)
//                            .collect(Collectors.toList())
//                    )
//                    .name(roomType.getName())
//                    .occupants(roomType.getOccupants())
//                    .id(roomType.getId())
//                    .build();
//        }
        return null;
    }


    public static List<ReservationRoom> createReservationRoomsFromDto(List<ReservationRoomDTO> reservationRoomDTOS, Reservation reservation) {
        if (Objects.nonNull(reservationRoomDTOS) && !reservationRoomDTOS.isEmpty()) {
            return reservationRoomDTOS.stream()
                    .map(reservationRoomDTO -> Converter.reservationRoomDtoToEntity(reservationRoomDTO, reservation))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public static ReservationRoom reservationRoomDtoToEntity(ReservationRoomDTO reservationRoomDTO, Reservation reservation) {
        if (Objects.nonNull(reservationRoomDTO)) {
            return ReservationRoom.builder()
                    .reservation(reservation)
                    .room(Converter.roomDtoToRoom(reservationRoomDTO.getRoom()))
                    .status(Objects.nonNull(reservationRoomDTO.getStatus()) ? reservationRoomDTO.getStatus() : (short) 1)
                    .id(reservationRoomDTO.getId())
                    .stayFrom(reservationRoomDTO.getStayFrom().atTime(0, 0, 0))
                    .stayTo(reservationRoomDTO.getStayTo().atTime(23, 59, 59))
                    .build();
        }
        return null;
    }

    public static ReservationRoomDTO reservationRoomToDto(ReservationRoom reservationRoom) {
        if (Objects.nonNull(reservationRoom)) {
            return ReservationRoomDTO.builder()
                    .room(Converter.roomDtoToRoom(reservationRoom.getRoom()))
                    .status(Objects.nonNull(reservationRoom.getStatus()) ? reservationRoom.getStatus() : (short) 1)
                    .id(reservationRoom.getId())
                    .stayFrom(reservationRoom.getStayFrom().toLocalDate())
                    .stayTo(reservationRoom.getStayTo().toLocalDate())
                    .build();
        }
        return null;
    }

    public static Reservation reservationDtoToEntity(ReservationDTO reservationDTO) {
        if (Objects.nonNull(reservationDTO)) {
            return Reservation.builder()
                    .status(reservationDTO.getStatus())
                    .guestPhone(reservationDTO.getGuestPhone())
                    .guestEmail(reservationDTO.getGuestEmail())
                    .guestName(reservationDTO.getGuestName())
                    .id(reservationDTO.getId())
                    .build();
        }
        return null;
    }

    public static BookingDTO reservationToBookingDTO(Reservation reservation) {
        if (Objects.nonNull(reservation)) {
            return BookingDTO.builder()
                    .reservation(ReservationDTO.builder()
                            .id(reservation.getId())
                            .status(reservation.getStatus())
                            .guestPhone(reservation.getGuestPhone())
                            .guestEmail(reservation.getGuestEmail())
                            .guestName(reservation.getGuestName())
                            .build()
                    )
                    .rooms(reservation.getReservationRooms().stream()
                            .map(Converter::reservationRoomToDto)
                            .collect(Collectors.toList())
                    )
                    .build();
        }
        return null;
    }

    public static BookingDTO reservationRoomsToBookingDto(List<ReservationRoom> reservedRooms) {
        if (Objects.nonNull(reservedRooms) && !reservedRooms.isEmpty()) {
            List<ReservationRoomDTO> rooms = reservedRooms.stream()
                    .map(Converter::reservationRoomToDto)
                    .collect(Collectors.toList());
            return BookingDTO.builder()
                    .reservation(ReservationDTO.builder()
                            .guestName(reservedRooms.get(0).getReservation().getGuestName())
                            .guestEmail(reservedRooms.get(0).getReservation().getGuestEmail())
                            .guestPhone(reservedRooms.get(0).getReservation().getGuestPhone())
                            .id(reservedRooms.get(0).getReservation().getId())
                            .status(reservedRooms.get(0).getReservation().getStatus())
                            .build()
                    )
                    .rooms(rooms)
                    .build();
        }
        return null;
    }

    public static List<RoomAvailabilityDTO> roomAvailabilityDTOList(List<Room> rooms){
        if (Objects.nonNull(rooms) && !rooms.isEmpty()) {
            List<RoomAvailabilityDTO> roomAvailabilityDTOList = rooms.stream()
                    .map(room -> roomAvailabilityDTO(room))
                    .collect(Collectors.toList());

            return roomAvailabilityDTOList;
        }
        return null;
    }

    public static RoomAvailabilityDTO roomAvailabilityDTO(Room room){

        return  RoomAvailabilityDTO.builder().room(roomDtoToRoom(room)).build();

    }

}
