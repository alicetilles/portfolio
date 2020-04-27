package edu.northeastern.cs5200.repositories;

import edu.northeastern.cs5200.models.LibraryMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface MemberRepository extends CrudRepository<LibraryMember, Integer> {

    @Query("SELECT libraryMember FROM LibraryMember libraryMember  " +
            "where libraryMember.username=:username")
    LibraryMember findMemberByUsername(String username);


    @Query("SELECT libraryMember.sponsoredBy FROM LibraryMember libraryMember  " +
            "where libraryMember.id=:memberId")
    Integer findSponsorId(Integer memberId);


    @Query("SELECT libraryMember FROM LibraryMember libraryMember  " +
            "where libraryMember.id=:memberId")
    Set<LibraryMember> findRecipientsOfSponsorship(Integer memberId);

}
