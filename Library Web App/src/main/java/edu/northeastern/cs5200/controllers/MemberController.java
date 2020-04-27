package edu.northeastern.cs5200.controllers;

import edu.northeastern.cs5200.daos.LibraryDao;
import edu.northeastern.cs5200.models.LegerEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.northeastern.cs5200.models.LibraryMember;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
public class MemberController {

  @Autowired
  LibraryDao libraryDao;

  @PostMapping("api/members")
  public LibraryMember createMember(@RequestBody LibraryMember member) {
    return libraryDao.createMember(member);
  }

  @GetMapping("/api/members")
  public List<LibraryMember> findAllMembers() {
    return libraryDao.findAllMembers();
  }

  @GetMapping("/api/members/id/{id}")
  public LibraryMember getById(@PathVariable("id") int id) {
    return libraryDao.findMemberById(id);
  }

  @GetMapping("/api/members/username/{username}")
  public LibraryMember getByUsername(@PathVariable("username") String username) {
    return libraryDao.findMemberByUsername(username);
  }

  @DeleteMapping(value = "/api/members/{id}")
  public boolean deleteMember(@PathVariable Integer id) {
    return libraryDao.deleteMember(id);
  }

  @PostMapping("/api/members/{memberId}/check-out/{bookId}/hard-copy")
  public LegerEntry checkOutBookHardCopy(@PathVariable Integer memberId, @PathVariable String bookId) {
    return libraryDao.checkOutBookHardCopy(memberId, bookId);
  }

  @PostMapping("/api/members/{memberId}/check-out/{bookId}/audiobook-copy")
  public boolean checkOutBookAudio(@PathVariable Integer memberId, @PathVariable String bookId) {
    return libraryDao.checkOutAudiobook(memberId, bookId);
  }

  @PostMapping("/api/members/{memberId}/return/{bookCopyId}")
  public boolean returnBook(@PathVariable Integer memberId, @PathVariable Integer bookCopyId) {
    return libraryDao.returnBook(memberId, bookCopyId);
  }

  @GetMapping("/api/members/{memberId}/checked-out-all-time")
  public Set<Object[]> seeMyCheckedOutBooksAllTime(@PathVariable Integer memberId) {
    return libraryDao.seeCheckedOutBooksAllTime(memberId);
  }

  @GetMapping("/api/members/{memberId}/checked-out-currently")
  public Set<Object[]> seeMyCheckedOutBooksCurrently(@PathVariable Integer memberId) {
    return libraryDao.seeCheckedOutBooksCurrently(memberId);
  }


  @GetMapping("/api/members/{memberId}/sponsor-recipients")
  public Set<LibraryMember> findRecipientsOfSponsorship(@PathVariable Integer memberId) {
    return libraryDao.findRecipientsOfSponsorship(memberId);
  }


  @PutMapping(value = "/api/members/{memberId}")
  public LibraryMember updateMember(@PathVariable Integer memberId, @RequestBody LibraryMember member) {
    return libraryDao.updateMember(memberId, member);
  }

  @GetMapping("/api/members/{memberId}/sponsor")
  public LibraryMember findSponsor(@PathVariable Integer memberId) {
    return libraryDao.findSponsor(memberId);
  }




}
