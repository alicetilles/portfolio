package edu.northeastern.cs5200.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name="hard_copy")
public class HardCopyBook extends BookCopy {

    private Integer numPages;

    @Enumerated(EnumType.STRING)
    private CurrentCondition currentCondition;

    public HardCopyBook() {
        super();
    }

    public HardCopyBook(Integer id, Book book, Boolean isAvailable, Integer edition, CurrentCondition currentCondition,
                        Integer numPages) {
        super(id, book, isAvailable, edition);
        this.currentCondition = currentCondition;
        this.numPages = numPages;
    }


    public Integer getNumPages() {
        return numPages;
    }

    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
    }

    public CurrentCondition getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(CurrentCondition currentCondition) {
        this.currentCondition = currentCondition;
    }


    @Override
    public String toString() {
        return "HardCopyBook{" +
                "book=" + getBook() +
                ", numPages=" + numPages +
                ", currentCondition=" + currentCondition +
                '}';
    }
}
