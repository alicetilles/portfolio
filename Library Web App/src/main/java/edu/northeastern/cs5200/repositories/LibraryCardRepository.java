package edu.northeastern.cs5200.repositories;

import edu.northeastern.cs5200.models.LibraryCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryCardRepository extends CrudRepository<LibraryCard, Integer> {


    @Query("SELECT libraryCard FROM LibraryCard libraryCard, LibraryMember libraryMember " +
            "WHERE libraryCard.user = libraryMember " +
            "AND libraryMember.username=:memberUsername")
    LibraryCard findByMemberUsername(@Param("memberUsername")String memberUsername);
}
