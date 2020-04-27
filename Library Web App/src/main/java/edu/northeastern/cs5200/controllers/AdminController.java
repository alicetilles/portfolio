package edu.northeastern.cs5200.controllers;

import edu.northeastern.cs5200.daos.LibraryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import edu.northeastern.cs5200.models.*;
import edu.northeastern.cs5200.repositories.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AdminController {

  @Autowired
  LibraryDao libraryDao;


  @PostMapping("/api/admins")
  public Admin createAdmin(@RequestBody Admin admin) {
    return libraryDao.createAdmin(admin);
  }

  @GetMapping("/hello")
  public String test() {
    return "Hello World";
  }

  @GetMapping("/api/admins")
  public List<Admin> findAllAdmin() {
    return libraryDao.findAllAdmin();
  }

  @DeleteMapping(value = "/api/admins/{id}")
  public boolean deleteAdmin(@PathVariable Integer id) {
    return libraryDao.deleteAdmin(id);
  }


  @PutMapping(value = "/api/admins/{adminId}")
  public Admin updateAdmin(@PathVariable Integer adminId, @RequestBody Admin admin) {
    return libraryDao.updateAdmin(adminId, admin);
  }



}
