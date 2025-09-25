package com.example.library.member;

import com.example.library.book.Book;
import com.example.library.book.BookNotFoundException;
import com.example.library.book.BorrowingLimitExceededException;
import com.example.library.book.DuplicateBorrowException;
import com.example.library.membershipcard.ExpiredMembershipException;

import java.util.List;

public interface MemberService {

    Member registerNewMember(Member newMember) throws MemberAlreadyExistsException;

    Member getMemberByEmailId(String email) throws MemberNotFoundException;

    Member barrowBookByMemberEmailIdAndBookIsbn(String memberEmailId, String bookIsbn) throws BookNotFoundException, MemberNotFoundException, ExpiredMembershipException, BorrowingLimitExceededException, DuplicateBorrowException;

    List<Book> getAllBarrowedBooks(String emailId) throws MemberNotFoundException;

    Member returnBookByIsbn(String memberEmailId, String bookIsbn) throws MemberNotFoundException, BookNotFoundException;
}
