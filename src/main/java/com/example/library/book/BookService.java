package com.example.library.book;

import com.example.library.member.Member;

import java.util.List;

public interface BookService {

    List<Member> getAllBarrowersByIsbn(String isbn) throws BookNotFoundException;
}
