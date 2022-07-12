package Utils;

import com.alten.challenge.bookchallenge.exception.BookChallengeApiExceptionError;
import com.alten.challenge.bookchallenge.exception.BookChallengeException;
import com.alten.challenge.bookchallenge.model.dto.BookingDTO;
import com.alten.challenge.bookchallenge.model.dto.ReservationRoomDTO;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

/**
 * Validator Class that handle date validations to avoid bad data to be persisted
 */
public class ReservationDTOValidator {
    private static final String errorMessage = "There was an issue in the request";

    private ReservationDTOValidator(){

    }


    public static boolean validateReservationDto(BookingDTO reservationDTO) throws BookChallengeException {
        if(Objects.nonNull(reservationDTO)){
            if(validateReservationObject(reservationDTO)) {
                List<ReservationRoomDTO> rooms = reservationDTO.getRooms();
                if (Objects.nonNull(rooms) && !rooms.isEmpty()) {
                    for (int i = 0; i < rooms.size(); i++) {
                        if (Objects.nonNull(rooms.get(i).getRoom().getId())) {
                            validateFromToDatesForBooking(rooms.get(i).getStayFrom(), rooms.get(i).getStayTo());
                        } else {
                            throw new BookChallengeException(BookChallengeApiExceptionError.builder()
                                    .message("Some of the provided rooms are invalid")
                                    .status(HttpStatus.BAD_REQUEST)
                                    .timestamp(LocalDateTime.now())
                                    .build()
                            );
                        }
                    }
                }else{
                    throw  new BookChallengeException(BookChallengeApiExceptionError.builder()
                            .message("You need to provide at least a room to book")
                            .status(HttpStatus.BAD_REQUEST)
                            .timestamp(LocalDateTime.now())
                            .build()
                    );
                }
            }
        }else{
            throw  new BookChallengeException(BookChallengeApiExceptionError.builder()
                    .message("No reservation object provided")
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.BAD_REQUEST)
                    .build()
            );
        }
        return true;
    }


    public static boolean validateFromToDatesForBooking(LocalDate from, LocalDate to) throws BookChallengeException {
        if(validateFromToDates(from,to)){
            LocalDate now = LocalDate.now();
            if(ChronoUnit.DAYS.between(from,to) > 3){
                throw  new BookChallengeException(BookChallengeApiExceptionError.builder()
                        .message("Invalid 'to' date provided for booking: Stay period for reservation cannot be more than 3 days")
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            }
            if(ChronoUnit.DAYS.between(now,from) > 30){
                throw  new BookChallengeException(BookChallengeApiExceptionError.builder()
                        .message("Invalid 'From' date provided for booking: You cannot book for more than 30 days in advance")
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            }
        }
        return true;
    }


    public static boolean validateFromToDates(LocalDate from, LocalDate to) throws BookChallengeException {
        if(Objects.nonNull(from) && Objects.nonNull(to)){
            LocalDate now = LocalDate.now();
            if(from.isBefore(now))
                throw  new BookChallengeException(BookChallengeApiExceptionError.builder()
                        .message("Invalid 'from' date provided: 'From' date cannot be before than today")
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            if(from.isEqual(now))
                throw  new BookChallengeException(BookChallengeApiExceptionError.builder()
                        .message("Invalid 'from' date provided: 'From' date has to be at least tomorrow")
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            if(to.isBefore(now))
                throw  new BookChallengeException(BookChallengeApiExceptionError.builder()
                        .message("Invalid 'to' date provided: 'To' date cannot be before than today")
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            if(to.isBefore(from)){
                throw  new BookChallengeException(BookChallengeApiExceptionError.builder()
                        .message("Invalid 'to' date provided: 'To' date cannot be before than 'From'")
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            }
        }else {
            throw  new BookChallengeException(BookChallengeApiExceptionError.builder()
                    .message("Invalid date range provided: You need to provide both 'From' and 'To' for this action")
                    .status(HttpStatus.BAD_REQUEST)
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
        return true;
    }


    public static boolean validateReservationObject(BookingDTO reservation) throws BookChallengeException {

        if(Objects.nonNull(reservation.getReservation())){
            if(Objects.isNull(reservation.getReservation().getGuestEmail())
                    || reservation.getReservation().getGuestEmail().isEmpty()){
                throw  new BookChallengeException(BookChallengeApiExceptionError.builder()
                        .message("Invalid Reservation: You need to provide the 'visitorEmail'")
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            }
            if(Objects.isNull(reservation.getReservation().getGuestName())
                    || reservation.getReservation().getGuestName().isEmpty()){
                throw  new BookChallengeException(BookChallengeApiExceptionError.builder()
                        .message("Invalid Reservation: You need to provide the 'visitorName'")
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            }
            if(Objects.isNull(reservation.getReservation().getGuestPhone())
                    || reservation.getReservation().getGuestPhone().isEmpty()){
                throw  new BookChallengeException(BookChallengeApiExceptionError.builder()
                        .message("Invalid Reservation: You need to provide the 'visitorPhone'")
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            }
            return true;
        }

        throw  new BookChallengeException(BookChallengeApiExceptionError.builder()
                .message("Invalid Reservation: You need to provide Reservation object containing Visitor Information")
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
}
