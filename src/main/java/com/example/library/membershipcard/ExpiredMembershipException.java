package com.example.library.membershipcard;

public class ExpiredMembershipException extends Exception {
    public ExpiredMembershipException(String message) {
        super(message);
    }
}
