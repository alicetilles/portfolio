package edu.northeastern.cs5200.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class LegerEntry {


    @EmbeddedId
    private LegerId id;

    private Timestamp dateBorrowed;
    private Timestamp dateReturned;

    public LegerEntry(){

    }


    public LegerEntry(LegerId id, Timestamp dateBorrowed, Timestamp dateReturned) {
        this.id = id;
        this.dateBorrowed = dateBorrowed;
        this.dateReturned = dateReturned;
    }

    public LegerEntry(Integer memberId, Integer bookCopyId, Timestamp dateBorrowed, Timestamp dateReturned) {
        LegerId newId = new LegerId(memberId, bookCopyId);
        this.id = newId;
        this.dateBorrowed = dateBorrowed;
        this.dateReturned = dateReturned;
    }

    public LegerId getId() {
        return id;
    }

    public void setId(LegerId id) {
        this.id = id;
    }

    public Timestamp getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(Timestamp dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public Timestamp getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Timestamp dateReturned) {
        this.dateReturned = dateReturned;
    }
}
