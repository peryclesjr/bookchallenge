package com.alten.challenge.bookchallenge.controller;


import com.alten.challenge.bookchallenge.exception.BookChallengeApiExceptionError;
import com.alten.challenge.bookchallenge.exception.BookChallengeException;
import com.alten.challenge.bookchallenge.model.dto.BookingDTO;
import com.alten.challenge.bookchallenge.model.dto.RoomAvailabilityDTO;
import com.alten.challenge.bookchallenge.services.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {

        this.roomService = roomService;
    }

    @Operation( description = "Retrieving a room Avaible.",
            summary = "Retrieving a room Avaible.")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "OK", content = @Content(mediaType ="application/json",
                    schema = @Schema(implementation =  RoomAvailabilityDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid reservationId provided", content = @Content(mediaType ="application/json",
                    schema = @Schema(implementation =  BookChallengeException.class)))})
    @GetMapping("/availability")
    public ResponseEntity<Page<RoomAvailabilityDTO>> getAvailabilityRoom(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate stayFrom,
                                                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate stayTo,
                                                                         @RequestParam(required = false) Optional<Integer> pageNumberOpt,
                                                                         @RequestParam(required = false) Optional<Integer> pageSizeOpt
    ) throws BookChallengeException {

        Integer pageNumber = pageNumberOpt.orElse(0);
        Integer pageSize = pageSizeOpt.orElse(10);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return new ResponseEntity<>(
                roomService.findAvailabilityByDateRange(stayFrom, stayTo, pageable),
                HttpStatus.OK);
    }
}



