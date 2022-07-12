package com.alten.challenge.bookchallenge.services;

import com.alten.challenge.bookchallenge.exception.BookChallengeException;
import com.alten.challenge.bookchallenge.model.dto.RoomAvailabilityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface RoomService {

    Page<RoomAvailabilityDTO> findAvailabilityByDateRange(LocalDate from, LocalDate to, Pageable pageable) throws BookChallengeException;

}