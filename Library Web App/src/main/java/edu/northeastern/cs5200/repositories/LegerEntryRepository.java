package edu.northeastern.cs5200.repositories;

import edu.northeastern.cs5200.models.LegerEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegerEntryRepository extends CrudRepository<LegerEntry, Integer> {


    @Query("SELECT legerEntry FROM LegerEntry legerEntry " +
            "WHERE legerEntry.id.memberId =:memberId " +
            "AND legerEntry.id.bookCopyId =:bookCopyId ")
    LegerEntry findByMemberIdAndBookCopyId(Integer memberId, Integer bookCopyId);
}
