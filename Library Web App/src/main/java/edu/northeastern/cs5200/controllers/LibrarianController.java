package edu.northeastern.cs5200.controllers;

import edu.northeastern.cs5200.daos.LibraryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.northeastern.cs5200.models.Librarian;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class LibrarianController {


  @Autowired
  LibraryDao libraryDao;

  @PostMapping("api/librarians")
  public Librarian createLibrarian(@RequestBody Librarian librarian) {
    return libraryDao.createLibrarian(librarian);
  }

  @GetMapping("/api/librarians")
  public List<Librarian> findAllLibrarians() {
    return libraryDao.findAllLibrarians();
  }

  @GetMapping("/api/librarians/id/{id}")
  public Librarian getById(@PathVariable("id") int id) {
    return libraryDao.findLibrarianById(id);
  }

  @GetMapping("/api/librarians/username/{username}")
  public Librarian getByUsername(@PathVariable("username") String username) {
    return libraryDao.findLibrarianByUsername(username);
  }

  @DeleteMapping(value = "/api/librarians/{id}")
  public boolean deleteLibrarian(@PathVariable Integer id) {
    return libraryDao.deleteLibrarian(id);
  }


  @PutMapping(value = "/api/librarians/{librarianId}")
  public Librarian updateLibrarian(@PathVariable Integer librarianId, @RequestBody Librarian librarian)
  {
    return libraryDao.updateLibrarian(librarianId, librarian);
  }



}
