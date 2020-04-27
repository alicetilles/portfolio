package edu.northeastern.cs5200.repositories;

import edu.northeastern.cs5200.models.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {

    @Query("SELECT book " +
            "FROM Book book, Author author " +
            "WHERE book.author = author " +
            "AND author.lastName =:authorLastName")
    Set<Book> findBooksByAuthor(String authorLastName);

    @Query("SELECT book FROM Book AS book where book.title=:title")
    Book findBookByTitle(String title);


}
