package edu.northeastern.cs5200.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class LibraryCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @MapsId
    @JsonIgnore
    private LibraryMember user;


    private Timestamp expirationDate;

    public LibraryCard(Integer id, LibraryMember member, Timestamp expirationDate) {

        this.id = id;
        this.user = member;
        this.expirationDate = expirationDate;
    }


    public LibraryCard() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LibraryMember getUser() {
        return user;
    }

    public void setUser(LibraryMember user) {
        this.user = user;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

}