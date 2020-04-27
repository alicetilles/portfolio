package edu.northeastern.cs5200.repositories;

import edu.northeastern.cs5200.models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BookCopyRepository extends CrudRepository<BookCopy, Integer> {


    @Query("SELECT bookCopy, books.title AS title, legerEntry.dateBorrowed AS dateBorrowed " +
            "FROM LegerEntry legerEntry, Book books, BookCopy bookCopy " +
            "WHERE legerEntry.id.memberId = :memberId " +
            "AND legerEntry.id.bookCopyId = bookCopy.id " +
            "AND bookCopy.book = books")
    Set<Object[]> findCheckedOutBooksAllTime(Integer memberId);

    @Query("SELECT bookCopy, books.title AS title, legerEntry.dateBorrowed AS dateBorrowed " +
            "FROM LegerEntry legerEntry, Book books, BookCopy bookCopy " +
            "WHERE legerEntry.id.memberId = :memberId " +
            "AND legerEntry.id.bookCopyId = bookCopy.id " +
            "AND bookCopy.book = books " +
            "AND legerEntry.dateReturned IS null")
    Set<Object[]> findCheckedOutBooksCurrently(Integer memberId);


//    @Query("SELECT bookCopy, books.title AS title, legerEntry.dateBorrowed AS dateBorrowed " +
//            "FROM LegerEntry legerEntry, Book books, BookCopy bookCopy " +
//            "WHERE legerEntry.id.memberId = :memberId " +
//            "AND legerEntry.id.bookCopyId = bookCopy.id " +
//            "AND bookCopy.book = books")

}
