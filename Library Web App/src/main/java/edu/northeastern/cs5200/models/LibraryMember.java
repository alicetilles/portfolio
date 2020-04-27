package edu.northeastern.cs5200.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

@Entity
public class LibraryMember extends User {


    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private LibraryCard libraryCard;

    @Column(name="sponsored_by", insertable=true, updatable=false)
    private Integer sponsoredBy;

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinColumn(name="sponsored_by")
    private Set<LibraryMember> recipientsOfSponsorship;

    public LibraryMember() {
        this.recipientsOfSponsorship = new HashSet<>();
    }

    public LibraryMember(Integer id, String firstName, String lastName, String username, String password, String email,
                         Timestamp dateOfBirth, Integer sponsoredBy, Set<LibraryMember> recipientsOfSponsorship) {
        super(id, firstName, lastName, username, password, email, dateOfBirth);
        this.sponsoredBy = sponsoredBy;
        this.recipientsOfSponsorship = recipientsOfSponsorship;
    }



    public LibraryCard getLibraryCard() {
        return libraryCard;
    }

    public void setLibraryCard(LibraryCard libraryCard) {
        this.libraryCard = libraryCard;
    }

    public Integer getSponsoredBy() {
        return sponsoredBy;
    }

    public void setSponsor(LibraryMember sponsor) {
        this.sponsoredBy = sponsor.getId();
    }

    public void setSponsoredBy(Integer sponsoredBy) {
        this.sponsoredBy = sponsoredBy;
    }

    public Set<LibraryMember> getRecipientsOfSponsorship() {
        return recipientsOfSponsorship;
    }

    public void setRecipientsOfSponsorship(Set<LibraryMember> recipientsOfSponsorship) {
        this.recipientsOfSponsorship = recipientsOfSponsorship;
    }

    public void addSponsorRecipient(LibraryMember recipient) {
        this.recipientsOfSponsorship.add(recipient);
    }

    /**
     * Figures out if the member is under 13 or not.
     * @return True if they're under thirteen, false otherwise.
     */
    public boolean isUnderThirteen() {

    	TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar minDOB = Calendar.getInstance(TimeZone.getDefault());
        minDOB.add(Calendar.YEAR, -13);

        // Just making null DOB be an adult, for convenience.
        if (this.getDateOfBirth() == null) {
            return false;
        }


        // If it is less than zero, than this date is before the minimum DOB
        // Therefore, they are at least 13 years old
        // If they  were born after the minimum DOB, they are indeed 13 years old
        return !(this.getDateOfBirth().compareTo((minDOB.getTime())) < 0);

    }


    @Override
    public String toString() {
        return "Member{" +
                "id=" + getId() +
                ", username=" + getUsername() +
                ", libraryCard=" + libraryCard +
                ", sponsoredBy=" + sponsoredBy +
                ", recipientsOfSponsorship=" + recipientsOfSponsorship +
                '}';
    }




}