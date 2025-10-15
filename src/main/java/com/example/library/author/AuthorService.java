package com.example.library.author;

import com.example.library.book.Book;
import com.example.library.book.BookAlreadyExistsException;

import java.util.List;

public interface AuthorService {

    Author registerNewAuther(Author newAuthor)throws AuthorAlreadyExistsException;

    Author addNewBookToAuthorByEmailId(String authorEmailID, Book newBook)throws AuthorNotFoundException, BookAlreadyExistsException;

    List<Book> getAllBooksWrittenByAutherEmailId(String authorEmailId)throws AuthorNotFoundException,NoBooksFoundException  ;

    List<Author> getAllAuthors();
}
