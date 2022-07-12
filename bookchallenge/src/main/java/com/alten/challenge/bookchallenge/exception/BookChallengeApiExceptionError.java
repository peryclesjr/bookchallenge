package com.alten.challenge.bookchallenge.exception;


import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookChallengeApiExceptionError {

    //http client response
    private HttpStatus status;

    // the custom message to client
    private String message;

    //for complete visibility of what happens
    private Throwable throwable;

    //clear indentify when and where, aroudn the  world,  it happens
    private LocalDateTime timestamp;
}

