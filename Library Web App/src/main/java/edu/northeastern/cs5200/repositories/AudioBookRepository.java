package edu.northeastern.cs5200.repositories;

import edu.northeastern.cs5200.models.AudioBook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AudioBookRepository extends CrudRepository<AudioBook, Integer> {

    @Query("SELECT audioBook FROM BookCopy audioBook, Book book " +
            "WHERE audioBook.book = book AND audioBook.class = 'audiobooks' AND audioBook.isAvailable=true " +
            "AND book.id = :id")
    Set<AudioBook> findAvailableBooksById(String id);

    @Query("SELECT audioBook FROM BookCopy audioBook, Book book " +
            "WHERE audioBook.book = book AND audioBook.class = 'audiobooks' AND book.id =:bookId")
    Set<AudioBook> findByBookId(String bookId);

}
