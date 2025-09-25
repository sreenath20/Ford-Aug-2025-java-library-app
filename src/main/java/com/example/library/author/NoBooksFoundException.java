package com.example.library.author;

public class NoBooksFoundException extends Exception {
    public NoBooksFoundException(String message) {
        super(message);
    }
}
