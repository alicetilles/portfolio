package edu.northeastern.cs5200.dataloader;

import edu.northeastern.cs5200.daos.LibraryDao;
import edu.northeastern.cs5200.models.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SeedDatabase {

    LibraryDao libraryDao;

    public SeedDatabase(LibraryDao libraryDao){
        this.libraryDao = libraryDao;
    }


    private Long stringDateToLong(String stringDate) throws ParseException {
        try {
            DateFormat formatter;
            java.util.Date date;
            formatter = new SimpleDateFormat("dd-MMM-yy");
            date = formatter.parse(stringDate);
            return date.getTime();
        }
        catch (ParseException e){
            stringDate = "01-January-1900";
            DateFormat formatter;
            java.util.Date date;
            formatter = new SimpleDateFormat("dd-MMM-yy");
            date = formatter.parse(stringDate);
            return date.getTime();
        }
    }

    public void createUsers() throws ParseException {



        LibraryMember navya = new LibraryMember();
        navya.setDateOfBirth(new Timestamp(stringDateToLong("01-April-1995")));
        navya.setFirstName("Navya");
        navya.setLastName("Reddy");
        navya.setEmail("reddy.na@husky.neu.edu");
        navya.setUsername("navya12");
        navya.setPassword("1234");
        navya.setId(1);
        libraryDao.createMember(navya);

        LibraryMember bob = new LibraryMember();
        bob.setDateOfBirth(new Timestamp(stringDateToLong("01-April-1995")));
        bob.setFirstName("bob");
        bob.setLastName("bob");
        bob.setEmail("bob@husky.neu.edu");
        bob.setUsername("bob");
        bob.setPassword("bob");
        bob.setId(2);
        libraryDao.createMember(bob);

        LibraryMember charlie = new LibraryMember();
        charlie.setDateOfBirth(new Timestamp(stringDateToLong("01-April-2019")));
        charlie.setFirstName("charlie");
        charlie.setLastName("charlie");
        charlie.setEmail("charlie@husky.neu.edu");
        charlie.setUsername("charlie");
        charlie.setPassword("charlie");
        charlie.setSponsor(bob);
        charlie.setId(3);
        libraryDao.createMember(charlie);

        LibraryMember nesara = new LibraryMember();
        nesara.setDateOfBirth(new Timestamp(stringDateToLong("26-May-1985")));
        nesara.setFirstName("Nesara");
        nesara.setLastName("Mahav");
        nesara.setEmail("madhav.n@husky.neu.edu");
        nesara.setUsername("nesara_loves_to_code");
        nesara.setPassword("1234");
        nesara.setId(4);
        libraryDao.createMember(nesara);

        LibraryMember childMember = new LibraryMember();
        childMember.setDateOfBirth(new Timestamp(stringDateToLong("26-May-2013")));
        childMember.setFirstName("Junior");
        childMember.setLastName("McGee");
        childMember.setEmail("cutiepie2013@hotmail.com");
        childMember.setUsername("ilikecookies123");
        childMember.setPassword("1234");
        childMember.setSponsor(navya);
        childMember.setId(5);
        libraryDao.createMember(childMember);


        Admin jose = new Admin();
        jose.setDateOfBirth(new Timestamp(stringDateToLong("09-October-1980")));
        jose.setFirstName("Jose");
        jose.setLastName("Annunziato");
        jose.setEmail("j.annunziato@northeastern.edu");
        jose.setUsername("TheProfessor");
        jose.setPassword("1234");
        jose.setId(6);
        libraryDao.createAdmin(jose);

        Admin siddhesh = new Admin();
        siddhesh.setDateOfBirth(new Timestamp(stringDateToLong("09-October-1980")));
        siddhesh.setFirstName("Siddhesh");
        siddhesh.setLastName("Latkar");
        siddhesh.setEmail("latkar.s@husky.neu.edu");
        siddhesh.setUsername("857IlovetoTA");
        siddhesh.setPassword("1234");
        siddhesh.setId(7);
        libraryDao.createAdmin(siddhesh);

        Admin admin = new Admin();
        admin.setDateOfBirth(new Timestamp(stringDateToLong("09-October-1980")));
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setEmail("admin@husky.neu.edu");
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setId(8);
        libraryDao.createAdmin(admin);



        Librarian sameer = new Librarian();
        sameer.setDateOfBirth(new Timestamp(stringDateToLong("16-December-1990")));
        sameer.setFirstName("Sameer");
        sameer.setLastName("Desai");
        sameer.setEmail("desai.sam@husky.neu.edu");
        sameer.setUsername("424databaseLuvr");
        sameer.setPassword("1234");
        sameer.setId(9);
        libraryDao.createLibrarian(sameer);

        Librarian sanju = new Librarian();
        sanju.setDateOfBirth(new Timestamp(stringDateToLong("22-February-1992")));
        sanju.setFirstName("Sanju");
        sanju.setLastName("Vasisht");
        sanju.setEmail("malavallisatheesh.s@husky.neu.edu");
        sanju.setUsername("mongoose123");
        sanju.setPassword("1234");
        sanju.setId(10);
        libraryDao.createLibrarian(sanju);

        Librarian alice = new Librarian();
        alice.setDateOfBirth(new Timestamp(stringDateToLong("22-February-1992")));
        alice.setFirstName("alice");
        alice.setLastName("alice");
        alice.setEmail("alice@husky.neu.edu");
        alice.setUsername("alice");
        alice.setPassword("alice");
        alice.setId(11);
        libraryDao.createLibrarian(alice);

    }


    public void rentBooks() throws ParseException {
        LibraryMember sb = new LibraryMember();
        sb.setDateOfBirth(new Timestamp(stringDateToLong("05-July-1622")));
        sb.setFirstName("Hester");
        sb.setLastName("Prynne");
        sb.setEmail("x_scarlet_babe_x@yahoo.com");
        sb.setUsername("Xx_scarlet_babe_xX");
        sb.setPassword("1234");
        libraryDao.createMember(sb);

        LibraryMember foundInDb = libraryDao.findMemberByUsername("Xx_scarlet_babe_xX");
        Book book = libraryDao.findBookByTitle("The Great Gatsby");
        libraryDao.checkOutBookHardCopy(foundInDb.getId(), book.getId());
        book = libraryDao.findBookByTitle("What to Expect When You're Expecting");
        libraryDao.checkOutBookHardCopy(foundInDb.getId(), book.getId());
        book = libraryDao.findBookByTitle("The Very Hungry Caterpillar");
        libraryDao.checkOutBookHardCopy(foundInDb.getId(), book.getId());
        book = libraryDao.findBookByTitle("You Can Heal Your Life");
        libraryDao.checkOutBookHardCopy(foundInDb.getId(), book.getId());
        book = libraryDao.findBookByTitle("Things Fall Apart");
        libraryDao.checkOutBookHardCopy(foundInDb.getId(), book.getId());

    }

}
