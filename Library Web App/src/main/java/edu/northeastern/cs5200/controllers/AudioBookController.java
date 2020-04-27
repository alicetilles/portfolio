package edu.northeastern.cs5200.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.northeastern.cs5200.daos.LibraryDao;
import edu.northeastern.cs5200.models.AudioBook;

@RestController
@CrossOrigin(origins = "*")
public class AudioBookController {

	@Autowired
	LibraryDao libraryDao;

	@PostMapping("api/audio-books")
	public AudioBook createAudioBook(@RequestBody AudioBook book) {
		return libraryDao.createAudioBook(book);
	}

	@PostMapping("api/audio-books/{id}")
	public AudioBook createAudioBookById(@PathVariable String id) {
		return libraryDao.addAudiobook(id);
	}

	@GetMapping("/api/audio-books")
	public List<AudioBook> findAllAudioBooks() {
		return libraryDao.findAllAudioBooks();
	}

	@GetMapping("/api/audio-books/{bookId}")
	public Set<AudioBook> findAudioBookByBookId(@PathVariable String bookId) {
		return libraryDao.findAudioBooksByBookId(bookId);
	}

	@DeleteMapping("/api/audio-books/{bookCopyId}")
	public boolean deleteAudiobook(@PathVariable Integer bookCopyId) {
		return libraryDao.deleteBookCopy(bookCopyId);
	}

}
