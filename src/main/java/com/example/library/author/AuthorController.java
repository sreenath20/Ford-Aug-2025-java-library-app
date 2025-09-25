package com.example.library.author;

import com.example.library.book.Book;
import com.example.library.book.BookAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    public Author addNewAuthor(@RequestBody Author newAuthor) throws AuthorAlreadyExistsException {
        return this.authorService.registerNewAuther(newAuthor);

    }

    // API / REST end point to add one book to existing author
    @PostMapping("/{authorEmailId}/books")
    public Author addBooksToAuthorByEmailId(@PathVariable String authorEmailId, @RequestBody Book newBook) throws AuthorNotFoundException, BookAlreadyExistsException {
        return this.authorService.addNewBookToAuthorByEmailId(authorEmailId, newBook);
    }

    @GetMapping("{emailId}/books")
    public List<Book> getAllBooksByAuthorEmailId(@PathVariable String emailId) throws AuthorNotFoundException, NoBooksFoundException {
        return this.authorService.getAllBooksWrittenByAutherEmailId(emailId);
    }
}
