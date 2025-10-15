package com.example.library.book;

import com.example.library.member.Member;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {

    List<Member> getAllBarrowersByIsbn(String isbn) throws BookNotFoundException;

    Page<Book> getBooks(int page, int size, String sortBy, String direction);

    List<Book> getAllBooks();
}
