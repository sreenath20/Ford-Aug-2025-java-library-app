package com.example.library.member;

import com.example.library.book.Book;
import com.example.library.book.BookNotFoundException;
import com.example.library.book.BorrowingLimitExceededException;
import com.example.library.book.DuplicateBorrowException;
import com.example.library.membershipcard.ExpiredMembershipException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public Member registerNewMember(@RequestBody Member newMember) throws MemberAlreadyExistsException {
        return this.memberService.registerNewMember(newMember);
    }

    @GetMapping("/{email}")
    Member getMemberByEmail(@PathVariable String email) throws MemberNotFoundException {
        return this.memberService.getMemberByEmailId(email);
    }

    @PostMapping("{memberEmailId}/borrow/{bookIsbn}")
    public Member barrowBookByMember(@PathVariable String memberEmailId, @PathVariable String bookIsbn) throws ExpiredMembershipException, BookNotFoundException, MemberNotFoundException, BorrowingLimitExceededException, DuplicateBorrowException {
        return this.memberService.barrowBookByMemberEmailIdAndBookIsbn(memberEmailId, bookIsbn);
    }

    @GetMapping("/{emailId}/books")
    public List<Book> getAllBarrowedBooksByMemberEmailId(@PathVariable String emailId) throws MemberNotFoundException {
        return this.memberService.getAllBarrowedBooks(emailId);
    }

    @DeleteMapping("/{memberEmailId}/return/{bookIsbn}")
    public Member returnBookByIsbnForMember(@PathVariable String memberEmailId,@PathVariable String bookIsbn) throws BookNotFoundException, MemberNotFoundException {
        return this.memberService.returnBookByIsbn(memberEmailId,bookIsbn);
    }

}
