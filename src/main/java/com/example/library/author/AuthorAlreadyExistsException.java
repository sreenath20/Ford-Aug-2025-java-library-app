package com.example.library.author;

public class AuthorAlreadyExistsException extends Exception {
    public AuthorAlreadyExistsException(String message) {
        super(message);
    }
}
