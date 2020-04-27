package edu.northeastern.cs5200.daos;

import edu.northeastern.cs5200.models.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Component
public interface LibraryDao {


    void truncateDatabase();
    void dropBooks();
    void dropUsers();


    // Finder methods -> find all
    List<Admin> findAllAdmin();
    List<AudioBook> findAllAudioBooks();
    List<Author> findAllAuthors();
    List<Book> findAllBooks();
    List<BookCopy> findAllBookCopies();
    List<HardCopyBook> findAllHardCopyBooks();
    List<LegerEntry> findAllLegerEntries();
    List<Librarian> findAllLibrarians();
    List<LibraryCard> findAllLibraryCards();
    List<LibraryMember> findAllMembers();
    List<User> findAllUsers();

    // Finder methods -> find by ID
    Book findBookById(String id);
    LibraryMember findMemberById(int id);
    Librarian findLibrarianById(int id);
    LibraryCard findLibraryCardByMemberId(int memberId);
    Author findAuthorById(Integer authorId);


    // Finder methods -> find by some other attribute
    // >> Find single object
    LibraryMember findMemberByUsername(String username);
    Librarian findLibrarianByUsername(String username);
    LibraryCard findLibraryCardByMemberUsername(String memberUsername);
    Book findBookByTitle(String title);
    LibraryMember findSponsor(Integer memberId);

    // >> Find a set of objects
    Set<HardCopyBook> findHardCopyBooksByBookId(String id);
    Set<AudioBook> findAudioBooksByBookId(String id);
    Set<Book> findBooksByAuthor(String authorName);
    Set<LibraryMember> findRecipientsOfSponsorship(Integer memberId);
    List<Author> findAuthorsByFullName(String first, String last);

    // Create a single object methods
    Admin createAdmin(Admin admin);
    AudioBook createAudioBook(AudioBook audioBook);
    Author createAuthor(Author author);
    Book createBook(Book book);
    HardCopyBook createHardCopyBook(HardCopyBook book);
    LegerEntry createLegerEntry(LegerEntry entry);
    Librarian createLibrarian(Librarian librarian);
    LibraryCard createLibraryCard(LibraryCard card);
    LibraryMember createMember(LibraryMember member);
    User createUser(User user);

    // Specialized single-create methods
    HardCopyBook addHardCopy(String bookId);
    AudioBook addAudiobook(String bookId);


    // Delete by ID methods
    boolean deleteAdmin(Integer id);
    boolean deleteLibrarian(Integer id);
    boolean deleteMember(Integer id);
    boolean deleteBookCopy(Integer bookCopyId);
    boolean deleteAuthor(Integer id);

    // More advanced methods
    boolean hasInvalidLibraryCard(LibraryMember member);
    boolean returnBook(Integer memberId, Integer bookCopyId);

    // To check out books
    LegerEntry checkOutBookHardCopy(Integer memberId, String bookId);
    boolean checkOutAudiobook(Integer memberId, String bookId);
    Set<HardCopyBook> findAvailableHardCopies(Book book);
    Set<AudioBook> findAvailableAudiobooks(Book book);
    Set<Object[]> seeCheckedOutBooksAllTime(Integer memberId);
    Set<Object[]> seeCheckedOutBooksCurrently(Integer memberId);


    // Update methods
    Admin updateAdmin(Integer adminId, @RequestBody Admin admin);
    Librarian updateLibrarian(Integer librarianId, @RequestBody Librarian librarian);
    LibraryMember updateMember(Integer memberId, @RequestBody LibraryMember member);



}


