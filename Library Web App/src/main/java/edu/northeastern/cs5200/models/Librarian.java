package edu.northeastern.cs5200.models;

import javax.persistence.Entity;
import java.sql.Timestamp;


@Entity
public class Librarian extends User {

    private Timestamp dateHired;
    private Boolean hasW2OnFile;

    public Librarian() {
       super();
    }

    public Librarian(Integer id, String firstName, String lastName, String username, String password, String email,
    		Timestamp dateOfBirth, Timestamp dateHired, Boolean hasW2OnFile) {
        super(id, firstName, lastName, username, password, email, dateOfBirth);
        this.dateHired = dateHired;
        this.hasW2OnFile = hasW2OnFile;
    }


    public Timestamp getDateHired() {
        return dateHired;
    }

    public void setDateHired(Timestamp dateHired) {
        this.dateHired = dateHired;
    }

    public Boolean getHasW2OnFile() {
        return hasW2OnFile;
    }

    public void setHasW2OnFile(Boolean hasW2OnFile) {
        this.hasW2OnFile = hasW2OnFile;
    }
}
