package com.example.library.book;

import com.example.library.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{isbn}/members")
    public List<Member> getAllBarrowedMembers(@PathVariable String isbn) throws BookNotFoundException {

        return this.bookService.getAllBarrowersByIsbn(isbn);
    }

}
