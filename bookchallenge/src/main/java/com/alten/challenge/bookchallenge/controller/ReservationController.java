package com.alten.challenge.bookchallenge.controller;

import com.alten.challenge.bookchallenge.exception.BookChallengeException;

import com.alten.challenge.bookchallenge.model.dto.BookingDTO;
import com.alten.challenge.bookchallenge.model.dto.ReservationRoomDTO;
import com.alten.challenge.bookchallenge.services.serviceImpl.ReservationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationServiceImpl reservationService;

    public ReservationController(ReservationServiceImpl reservationService){
        this.reservationService =  reservationService;
    }


    @Operation( description = "Retrieving a room Avaible.",
            summary = "Create Reservation")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "201", description = "OK", content = @Content(mediaType ="application/json",
                    schema = @Schema(implementation =  BookingDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid reservationId provided", content = @Content(mediaType ="application/json",
                    schema = @Schema(implementation =  BookChallengeException.class)))})
    @PostMapping("/book")
    public ResponseEntity<BookingDTO> createReservation(@RequestBody BookingDTO reservation) throws BookChallengeException {
        return new ResponseEntity<>(reservationService.book(reservation), HttpStatus.OK);
    }


    @Operation( description = "Modifying a reservation.",
            summary = "Modifies an existing reservation. Modifications supported are: Room cancellation, Room Stay Dates.")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "OK", content = @Content(mediaType ="application/json",
                    schema = @Schema(implementation =  BookingDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Object / Data provided", content = @Content(mediaType ="application/json",
                    schema = @Schema(implementation =  BookChallengeException.class)))})
    @PatchMapping("/modify")
    public ResponseEntity<BookingDTO> modifyReservation(@RequestBody BookingDTO reservation) throws BookChallengeException {

        return new ResponseEntity<>(reservationService.modifyReservation(reservation), HttpStatus.OK);
            }

    @Operation( description = "Retrieving a reservation.",
            summary = "Retrieves an existing and active reservation")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "OK", content = @Content(mediaType ="application/json",
                    schema = @Schema(implementation =  ReservationRoomDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid reservationId provided", content = @Content(mediaType ="application/json",
                    schema = @Schema(implementation =  BookChallengeException.class)))})
    @GetMapping("/searchReservation")
    public ResponseEntity<BookingDTO> getReservation(@RequestParam Long idReservation) {

            return new ResponseEntity<>( reservationService.getReservation(idReservation), HttpStatus.OK);
    }


    @Operation( description = "Cancelling a reservation.",
            summary = "Cancel an existing and active reservation")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Reservation Cancelled successfully", content = @Content(mediaType ="text/plan",
                    schema = @Schema(implementation =  String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid reservationId provided", content = @Content(mediaType ="application/json",
                    schema = @Schema(implementation =  BookChallengeException.class)))})
    @DeleteMapping("/cancel")
    public ResponseEntity<String> cancelReservation(@RequestParam Long id){

            reservationService.cancelReservation(id);
            return new ResponseEntity<>("Reservation Cancelled successfully", HttpStatus.OK);
    }
}