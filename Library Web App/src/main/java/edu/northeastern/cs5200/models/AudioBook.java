package edu.northeastern.cs5200.models;

import javax.persistence.Entity;

@Entity(name="audiobooks")
public class AudioBook extends BookCopy {

    private String narratedBy;
    private Integer numMinutes;
    private Double fileSizeMb;
    private String fileType;

    public AudioBook() {
    }

    public AudioBook(Integer id, Book book, Boolean isAvailable, Integer edition,
                     String narratedBy, Integer numMinutes, Double fileSizeMb, String fileType){
            super(id, book, isAvailable, edition);
            this.narratedBy = narratedBy;
            this.numMinutes = numMinutes;
            this.fileSizeMb = fileSizeMb;
            this.fileType = fileType;
    }



    public String getNarratedBy() {
        return narratedBy;
    }

    public void setNarratedBy(String narratedBy) {
        this.narratedBy = narratedBy;
    }

    public Integer getNumMinutes() {
        return numMinutes;
    }

    public void setNumMinutes(Integer numMinutes) {
        this.numMinutes = numMinutes;
    }

    public Double getFileSizeMb() {
        return fileSizeMb;
    }

    public void setFileSizeMb(Double fileSizeMb) {
        this.fileSizeMb = fileSizeMb;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
