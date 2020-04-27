package edu.northeastern.cs5200.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Book {

    @Id
    private String id;

    @Column(unique=true)
    private String isbn;

    private String title;

    private Timestamp yearPublished;

    private String genre;

    @ManyToOne(cascade=CascadeType.ALL)
    private Author author;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private Set<BookCopy> bookCopies;

    private String thumbnailURL;

    public Book(){
        this.bookCopies = new HashSet<>();
    }

    public Book(String id, String title, Author author, Timestamp yearPublished,
                String genre, String isbn, Set<BookCopy> bookCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.genre = genre;
        this.isbn = isbn;
        this.bookCopies = bookCopies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Timestamp getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(Timestamp yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String ISBN) {
        this.isbn = ISBN;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(Set<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public void addHardCoverCopy(){
        HardCopyBook newCopy = new HardCopyBook();
        newCopy.setBook(this);
        newCopy.setAvailable(true);
        newCopy.setEdition(null);
        newCopy.setCurrentCondition(CurrentCondition.NEW);
        this.bookCopies.add(newCopy);
    }

    public void addAudiobook(){
        AudioBook newCopy = new AudioBook();
        newCopy.setBook(this);
        newCopy.setAvailable(true);
        this.bookCopies.add(newCopy);
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", yearPublished=" + yearPublished +
                ", genre=" + genre +
                ", ISBN='" + isbn + '\'' +
                '}';
    }
}
