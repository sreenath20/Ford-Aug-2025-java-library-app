package com.example.library.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {


    Optional<Book> findByIsbn(String isbn);
}
