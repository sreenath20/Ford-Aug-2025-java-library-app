package com.example.library.author;

import com.example.library.book.Book;
import com.example.library.book.BookAlreadyExistsException;
import com.example.library.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {


    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        // validate or do some checks here on repo
        this.authorRepository = authorRepository;
    }

    @Override
    public Author registerNewAuther(Author newAuthor) throws AuthorAlreadyExistsException {
        Optional<Author> foundAuthorOpt = this.authorRepository.findByEmail(newAuthor.getEmail());
        if (foundAuthorOpt.isPresent())
            throw new AuthorAlreadyExistsException("Author by email alreaady registered.");

        return this.authorRepository.save(newAuthor);
    }

    @Override
    public Author addNewBookToAuthorByEmailId(String authorEmailID, Book newBook) throws AuthorNotFoundException,BookAlreadyExistsException {

        Optional<Author> foundAuthorOpt = this.authorRepository.findByEmail(authorEmailID);
        if (foundAuthorOpt.isEmpty())
            throw new AuthorNotFoundException("Author by email not found.");
        // check if isbn already exists
        Optional<Book> foundBookOpt = this.bookRepository.findByIsbn(newBook.getIsbn());
        if(foundBookOpt.isPresent())
            throw new BookAlreadyExistsException("Book already exists with ISBN:"+newBook.getIsbn());
        Author foundAuthor = foundAuthorOpt.get();
        // assign the author to book
        newBook.setAuthor(foundAuthor);
        // persist new book and add to author
        Book savedBook = this.bookRepository.save(newBook);
        foundAuthor.getBooks().add(savedBook);
        return foundAuthor;
    }

    @Override
    public List<Book> getAllBooksWrittenByAutherEmailId(String authorEmailId) throws AuthorNotFoundException, NoBooksFoundException {
        Optional<Author> foundAuthorOpt = this.authorRepository.findByEmail(authorEmailId);
        if (foundAuthorOpt.isEmpty())
            throw new AuthorNotFoundException("Author by email not found.");
        Author foundAuthor = foundAuthorOpt.get();
        // if needed handle size 0
        if (foundAuthor.getBooks().size() < 1)
            throw new NoBooksFoundException("No books found for given author");
        return foundAuthor.getBooks();
    }

    @Override
    public List<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }

    @Override
    public Author updateBookOfAuthorByEmailId(String authorEmailId, Book updateBook) throws AuthorNotFoundException, BookAlreadyExistsException {
        Optional<Author> foundAuthorOpt = this.authorRepository.findByEmail(authorEmailId);
        if (foundAuthorOpt.isEmpty())
            throw new AuthorNotFoundException("Author by email not found.");


        // check if isbn already exists if you enable isbn editable
        Optional<Book> foundBookOpt = this.bookRepository.findByIsbn(updateBook.getIsbn());
        if(foundBookOpt.isPresent())
            throw new BookAlreadyExistsException("Book already exists with ISBN:"+updateBook.getIsbn());

        Author foundAuthor = foundAuthorOpt.get();
       Book book= foundAuthor.getBooks().stream()
                .filter(b->b.getId().equals(updateBook.getId()))
                        .findFirst().orElseThrow(()->new RuntimeException("Book not found for this author"));
       // update author book in collection
       book.setTitle(updateBook.getTitle());
       book.setIsbn(updateBook.getIsbn());


        return  this.authorRepository.save(foundAuthor);
    }

    @Override
    public Author deleteBookOfAuthorByEmailId(String authorEmailId, Integer bookId) {
        Optional<Author> foundAuthorOpt = this.authorRepository.findByEmail(authorEmailId);
        if (foundAuthorOpt.isEmpty())
            throw new RuntimeException("Author by email not found.");

        Author foundAuthor = foundAuthorOpt.get();
        Book book= foundAuthor.getBooks().stream()
                .filter(b->b.getId().equals(bookId))
                .findFirst().orElseThrow(()->new RuntimeException("Book not found for this author"));
        foundAuthor.getBooks().remove(book);
return  this.authorRepository.save(foundAuthor);
    }
}
