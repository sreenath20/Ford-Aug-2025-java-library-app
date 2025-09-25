package com.example.library.member;

import com.example.library.book.*;
import com.example.library.membershipcard.ExpiredMembershipException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Member registerNewMember(Member newMember) throws MemberAlreadyExistsException {
        Optional<Member> memberOptional = this.memberRepository.findByEmail(newMember.getEmail());
        if (memberOptional.isPresent())
            throw new MemberAlreadyExistsException("Member already present with given email!");
        return this.memberRepository.save(newMember);
    }

    @Override
    public Member getMemberByEmailId(String email) throws MemberNotFoundException {

        Optional<Member> memberOptional = this.memberRepository.findByEmail(email);
        if (memberOptional.isEmpty())
            throw new MemberNotFoundException("Member does not exists for given email!");
        return memberOptional.get();
    }

    @Override
    @Transactional
    public Member barrowBookByMemberEmailIdAndBookIsbn(String memberEmailId, String bookIsbn) throws MemberNotFoundException, BookNotFoundException, ExpiredMembershipException, BorrowingLimitExceededException, DuplicateBorrowException {
        // check if member and book exists
        Optional<Member> memberOptional = this.memberRepository.findByEmail(memberEmailId);
        if (memberOptional.isEmpty())
            throw new MemberNotFoundException("Member does not exists for given email!");

        Optional<Book> bookOptional = this.bookRepository.findByIsbn(bookIsbn);
        if (bookOptional.isEmpty())
            throw new BookNotFoundException("Book ISBN not found please check and retry.");
        // member card should valid
        Member foundMember = memberOptional.get();
        if (foundMember.getMembershipCard().getExpiryDate().isBefore(LocalDate.now()))
            throw new ExpiredMembershipException("Membership card expired, please renew it to.");
        //A book can’t be borrowed if already borrowed by 5 members.
        Book foundBook = bookOptional.get();
        if (foundBook.getBorrowedByMembers().size() >= 5)
            throw new BorrowingLimitExceededException("Borrowing limit of 5 already reached for Book, retry later.");
        // A member cannot borrow the same book twice.

        if (foundMember.getBorrowedBooks().contains(foundBook))
            throw new DuplicateBorrowException("Member have already barrowed the book:" + foundBook.getIsbn());
        //
        foundMember.getBorrowedBooks().add(foundBook);

        return this.memberRepository.save(foundMember);
    }

    @Override
    public List<Book> getAllBarrowedBooks(String emailId) throws MemberNotFoundException {
        // check if member and book exists
        Optional<Member> memberOptional = this.memberRepository.findByEmail(emailId);
        if (memberOptional.isEmpty())
            throw new MemberNotFoundException("Member does not exists for given email!");

        return memberOptional.get().getBorrowedBooks();
    }

    @Override
    public Member returnBookByIsbn(String memberEmailId, String bookIsbn) throws MemberNotFoundException, BookNotFoundException {
        // check if member and book exists
        Optional<Member> memberOptional = this.memberRepository.findByEmail(memberEmailId);
        if (memberOptional.isEmpty())
            throw new MemberNotFoundException("Member does not exists for given email!");
        Optional<Book> bookOptional = this.bookRepository.findByIsbn(bookIsbn);
        if (bookOptional.isEmpty())
            throw new BookNotFoundException("Book ISBN not found in library, please check and retry.");

        Member foundMember = memberOptional.get();
        Book findBook = bookOptional.get();
        if (foundMember.getBorrowedBooks().indexOf(findBook) == -1)
            throw new BookNotFoundException("Book not found in your Borrowed list");
        // return by removing it from member collection
        foundMember.getBorrowedBooks().remove(findBook);

        return this.memberRepository.save(foundMember);
    }
}
