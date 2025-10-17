package com.example.library.book;


import com.example.library.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<Book> getBooks(int pageNumber, int pageSize, String sortByField, String sortingOrder) {
        Sort sort = sortingOrder.equalsIgnoreCase("desc") ? Sort.by(sortByField).descending() : Sort.by(sortByField).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return this.bookRepository.findAll(pageable);
    }

    @Override
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @Override
    public Book getBookById(Integer id) {
        return this.bookRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Book not found for Id"+id));

    }
}
