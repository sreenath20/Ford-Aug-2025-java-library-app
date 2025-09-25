package com.example.library.book;


import com.example.library.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;


    @Override
    public List<Member> getAllBarrowersByIsbn(String isbn) throws BookNotFoundException {

        Optional<Book> bookOptional = this.bookRepository.findByIsbn(isbn);
        if (bookOptional.isEmpty())
            throw new BookNotFoundException("Book ISBN not found please check and retry.");
        return bookOptional.get().getBorrowedByMembers();
    }
}
