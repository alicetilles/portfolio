package edu.northeastern.cs5200.controllers;

import edu.northeastern.cs5200.daos.LibraryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


import edu.northeastern.cs5200.models.*;
import edu.northeastern.cs5200.repositories.*;

@RestController
@CrossOrigin(origins = "*")
public class AuthorController {

  @Autowired
  LibraryDao libraryDao;

  @PostMapping("api/authors")
  public Author createAuthor(@RequestBody Author author) {
    return libraryDao.createAuthor(author);
  }

  @GetMapping("/api/authors")
  public List<Author> findAllAuthors() {
    return libraryDao.findAllAuthors();
  }

  @GetMapping("/api/authors/{authorLastName}/books")
  public Set<Book> findBooksByAuthor(@PathVariable String authorLastName) {
    return libraryDao.findBooksByAuthor(authorLastName);
  }

  @GetMapping("/api/authors/{authorId}")
  public Author findAuthorById(@PathVariable Integer authorId) {
    return libraryDao.findAuthorById(authorId);
  }



}
