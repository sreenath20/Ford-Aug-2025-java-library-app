package com.example.library.member;

import com.example.library.book.DuplicateBorrowException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler(MemberNotFoundException.class)
    public String handleMemberAlreadyExistsException(MemberNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MemberAlreadyExistsException.class)
    public String handleMemberAlreadyExistsException(MemberAlreadyExistsException e) {
        return e.getMessage();
    }

    //DuplicateBorrowException
    @ExceptionHandler(DuplicateBorrowException.class)
    public String handleMemberAlreadyExistsException(DuplicateBorrowException e) {
        return e.getMessage();
    }

    // add exception generic handler
    @ExceptionHandler(Exception.class)
    public String handleMemberAlreadyExistsException(Exception e) {
        return e.getMessage();
    }
}
