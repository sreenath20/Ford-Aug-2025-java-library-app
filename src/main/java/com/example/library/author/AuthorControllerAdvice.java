package com.example.library.author;

import com.example.library.book.BookAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthorControllerAdvice {

    @ExceptionHandler(AuthorAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAuthorAlreadyExistsException(AuthorAlreadyExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAuthorNotFoundException(AuthorNotFoundException e) {
        return e.getMessage();
    }

    // BookAlreadyExistsException
    @ExceptionHandler(BookAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAuthorNotFoundException(BookAlreadyExistsException e) {
        return e.getMessage();
    }

}
