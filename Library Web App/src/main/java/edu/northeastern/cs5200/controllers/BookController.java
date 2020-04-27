package edu.northeastern.cs5200.controllers;

import edu.northeastern.cs5200.daos.LibraryDao;
import edu.northeastern.cs5200.models.*;
import edu.northeastern.cs5200.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    LibraryDao libraryDao;

    @PostMapping("api/books")
    public Book createBook(@RequestBody Book book) {
        return libraryDao.createBook(book);
    }

    @GetMapping("api/books")
    public List<Book> findAllBooks() {
        return libraryDao.findAllBooks();
    }

    @GetMapping("api/books/title/{bookTitle}")
    public Book findBookByTitle(@PathVariable String bookTitle) {
        return libraryDao.findBookByTitle(bookTitle);
    }

    @GetMapping("api/books/{id}")
    public Book findBookById(@PathVariable String id) {
        return libraryDao.findBookById(id);
    }

}
