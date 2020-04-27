package edu.northeastern.cs5200.repositories;

import edu.northeastern.cs5200.models.Author;
import edu.northeastern.cs5200.models.LibraryMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {

    @Query("SELECT author FROM Author author  " +
            "where author.firstName=:firstName" +
            " AND author.lastName=:lastName")
    List<Author> findAuthorByFullName(String firstName, String lastName);


}
