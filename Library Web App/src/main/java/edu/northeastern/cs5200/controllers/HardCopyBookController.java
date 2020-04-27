package edu.northeastern.cs5200.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.northeastern.cs5200.daos.LibraryDao;
import edu.northeastern.cs5200.models.HardCopyBook;

@RestController
@CrossOrigin(origins = "*")
public class HardCopyBookController {

	@Autowired
	LibraryDao libraryDao;

	@PostMapping("api/hard-copy-books")
	public HardCopyBook createHardCopyBook(@RequestBody HardCopyBook book) {
		return libraryDao.createHardCopyBook(book);
	}

	@PostMapping("api/hard-copy-books/{id}")
	public HardCopyBook createHardCopyBookById(@PathVariable String id) {
		return libraryDao.addHardCopy(id);
	}

	@GetMapping("/api/hard-copy-books")
	public List<HardCopyBook> findAllHardCopyBooks() {
		return libraryDao.findAllHardCopyBooks();
	}

	@GetMapping("/api/hard-copy-books/{bookId}")
	public Set<HardCopyBook> getHardCopyBookBybookId(@PathVariable String bookId) {
		return libraryDao.findHardCopyBooksByBookId(bookId);
	}

	@DeleteMapping("/api/hard-copy-books/{bookCopyId}")
	public boolean deleteHardCopyBook(@PathVariable Integer bookCopyId) {
		return libraryDao.deleteBookCopy(bookCopyId);
	}

	@DeleteMapping("/api/book-copies/{bookCopyId}")
	public boolean deleteBookCopy(@PathVariable Integer bookCopyId) {
		return libraryDao.deleteBookCopy(bookCopyId);
	}

}
