package edu.northeastern.cs5200.models;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LegerId implements Serializable {

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "book_copy_id")
    private Integer bookCopyId;

    public LegerId(){

    }

    public LegerId(Integer memberId, Integer bookCopyId){
        this.bookCopyId = bookCopyId;
        this.memberId = memberId;
    }


    public Integer getMemberId() {
        return memberId;
    }

    public Integer getBookCopyId() {
        return bookCopyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LegerId legerId = (LegerId) o;
        return memberId.equals(legerId.memberId) &&
                bookCopyId.equals(legerId.bookCopyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, bookCopyId);
    }
}
