package com.alten.challenge.bookchallenge.exception;


import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookChallengeException extends Exception{

    BookChallengeApiExceptionError apiError;

}
