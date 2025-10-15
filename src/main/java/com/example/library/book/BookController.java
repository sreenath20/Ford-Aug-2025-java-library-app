package com.example.library.book;

import com.example.library.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin("http://localhost:4200/") // allowing requests from crossorigin requests
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{isbn}/members")
    public List<Member> getAllBarrowedMembers(@PathVariable String isbn) throws BookNotFoundException {

        return this.bookService.getAllBarrowersByIsbn(isbn);
    }

//    Key Takeaway :
//
//    Never rely on “unordered” results when paginating.
//
//    Always apply a default sorting strategy (like id ASC).
//
//    This avoids duplicates/missing records across pages when data changes.

    @GetMapping
    public Page<Book> getBooks(
            @RequestParam(defaultValue = "0") int pageNumber,        // page number
            @RequestParam(defaultValue = "5") int pageSize,        // page size
            @RequestParam(defaultValue = "id") String sortByField,  // field to sort
            @RequestParam(defaultValue = "asc") String sortingOrder // asc/desc
    ) {
        return bookService.getBooks(pageNumber, pageSize, sortByField, sortingOrder);
    }
    @GetMapping("/all")
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }
}
