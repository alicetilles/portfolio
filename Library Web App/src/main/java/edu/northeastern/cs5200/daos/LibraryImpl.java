package edu.northeastern.cs5200.daos;

import edu.northeastern.cs5200.models.*;
import edu.northeastern.cs5200.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class LibraryImpl implements LibraryDao {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AudioBookRepository audioBookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookCopyRepository bookCopyRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    HardCopyBookRepository hardCopyBookRepository;

    @Autowired
    LegerEntryRepository legerEntryRepository;

    @Autowired
    LibrarianRepository librarianRepository;

    @Autowired
    LibraryCardRepository libraryCardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    UserRepository userRepository;


    /**
     * Deletes all data from the database.
     */
    @Override
    public void truncateDatabase() {
        adminRepository.deleteAll();
        audioBookRepository.deleteAll();
        bookCopyRepository.deleteAll();
        bookRepository.deleteAll();
        hardCopyBookRepository.deleteAll();
        legerEntryRepository.deleteAll();
        librarianRepository.deleteAll();
        libraryCardRepository.deleteAll();
        memberRepository.deleteAll();
        authorRepository.deleteAll();
    }

    /**
     * Deletes just the books and book copies from the database.
     */
    @Override
    public void dropBooks(){

        bookCopyRepository.deleteAll();
        hardCopyBookRepository.deleteAll();
        audioBookRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();

    }


    /**
     * Deletes just the users from the database.
     */
    @Override
    public void dropUsers(){

        libraryCardRepository.deleteAll();
        memberRepository.deleteAll();
        adminRepository.deleteAll();
        librarianRepository.deleteAll();

    }

    @Override
    public List<Admin> findAllAdmin() {
        return (List<Admin>) adminRepository.findAll();
    }

    @Override
    public List<AudioBook> findAllAudioBooks() {
        return (List<AudioBook>)audioBookRepository.findAll();
    }

    @Override
    public List<Author> findAllAuthors() {
        return (List<Author>)authorRepository.findAll();
    }

    @Override
    public List<Book> findAllBooks() {
        return (List<Book>)bookRepository.findAll();
    }

    @Override
    public List<BookCopy> findAllBookCopies() {
        return (List<BookCopy>)bookCopyRepository.findAll();
    }

    @Override
    public List<HardCopyBook> findAllHardCopyBooks() {
        return (List<HardCopyBook>)hardCopyBookRepository.findAll();
    }

    @Override
    public List<LegerEntry> findAllLegerEntries() {
        return (List<LegerEntry>)legerEntryRepository.findAll();
    }

    @Override
    public List<Librarian> findAllLibrarians() {
        return (List<Librarian>)librarianRepository.findAll();
    }

    @Override
    public List<LibraryCard> findAllLibraryCards() {
        return (List<LibraryCard>)libraryCardRepository.findAll();
    }

    @Override
    public List<LibraryMember> findAllMembers() {
        return (List<LibraryMember>)memberRepository.findAll();
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>)userRepository.findAll();
    }

    @Override
    public Book findBookById(String id) {
        var foundBook = bookRepository.findById(id);
        if (foundBook.isPresent()) {
            return foundBook.get();
        }
        return new Book();

    }

    @Override
    public LibraryMember findMemberById(int id) {

        var foundMember = memberRepository.findById(id);
        if (foundMember.isPresent()) {
            return foundMember.get();
        }
        return new LibraryMember();

    }

    @Override
    public Librarian findLibrarianById(int id) {
        var librarianInDb = librarianRepository.findById(id);
        if (librarianInDb.isPresent()) {
            return librarianInDb.get();
        }
        return new Librarian();
    }

    @Override
    public LibraryCard findLibraryCardByMemberId(int memberId) {

        // Make sure member exists
    	var foundMember = memberRepository.findById(memberId);
    	if (foundMember.isPresent()) {
    	    LibraryMember member = foundMember.get();

    	    // Make sure the library card exists
            var foundCard = libraryCardRepository.findById(member.getLibraryCard().getId());
            if (foundCard.isPresent()) {
                return foundCard.get();
            }
            return new LibraryCard();
    	}

    	return new LibraryCard();

    }

    @Override
    public Author findAuthorById(Integer authorId) {
        var foundInDb = authorRepository.findById(authorId);
        if (foundInDb.isPresent()) {
            return foundInDb.get();
        }

        return new Author();

    }

    @Override
    public Set<HardCopyBook> findHardCopyBooksByBookId(String id) {
        return hardCopyBookRepository.findByBookId(id);
    }

    @Override
    public Set<AudioBook> findAudioBooksByBookId(String id) {
        return audioBookRepository.findByBookId(id);
    }

    @Override
    public Set<Book> findBooksByAuthor(String authorLastName) {
        return bookRepository.findBooksByAuthor(authorLastName);
    }

    @Override
    public Set<LibraryMember> findRecipientsOfSponsorship(Integer memberId) {
        return memberRepository.findRecipientsOfSponsorship(memberId);
    }

    @Override
    public LibraryMember findMemberByUsername(String username) {

        LibraryMember found = memberRepository.findMemberByUsername(username);
        if (found == null) {
            return new LibraryMember();
        }

        return found;

    }

    @Override
    public Librarian findLibrarianByUsername(String username) {

        Librarian found = librarianRepository.findLibrarianByUsername(username);
        if (found == null) {
            return new Librarian();
        }
        return found;

    }
    
    @Override
    public LibraryCard findLibraryCardByMemberUsername(String memberUsername) {

        LibraryCard found = libraryCardRepository.findByMemberUsername(memberUsername);
        if (found == null) {
            return new LibraryCard();
        }
        return found;

    }

    @Override
    public Book findBookByTitle(String title) {
        Book foundBook = bookRepository.findBookByTitle(title);
        if (foundBook==null) {

            return new Book();

        }
        return foundBook;
    }

    @Override
    public LibraryMember findSponsor(Integer memberId) {
        Integer sponsorId = memberRepository.findSponsorId(memberId);
        var sponsorInDb = memberRepository.findById(sponsorId);
        if (sponsorInDb.isPresent()) {
            return sponsorInDb.get();
        }
        return new LibraryMember();
    }


    @Override
    public Admin createAdmin(Admin admin) {
        adminRepository.save(admin);
        return admin;
    }

    @Override
    public AudioBook createAudioBook(AudioBook audioBook) {
        audioBookRepository.save(audioBook);
        return audioBook;
    }


    @Override
    public List<Author> findAuthorsByFullName(String first, String last) {
        return authorRepository.findAuthorByFullName(first, last);
    }


    @Override
    public Author createAuthor(Author author) {

        //  Try to find this author in the DB (just finding duplicates based on full name, it's not perfect)
        List<Author> authorsInDb = authorRepository.findAuthorByFullName(author.getFirstName(), author.getLastName());
        System.out.println("Found authors matching " + author.getFirstName() +
                " " + author.getLastName() + " :" + authorsInDb);

        // If none are  found with this name, then save a new one and return it.
        if (authorsInDb.size() == 0) {
            System.out.println("0 found, saving: " + author);
            authorRepository.save(author);
            return author;
        }

        // If one is found, then just return that one.
        System.out.println("One was found. So didn't save  a new one");
        return author;

    }

    @Override
    public Book createBook(Book book) {
        bookRepository.save(book);
        bookCopyRepository.saveAll(book.getBookCopies());
        return book;

    }

    @Override
    public HardCopyBook createHardCopyBook(HardCopyBook book) {
        hardCopyBookRepository.save(book);
        return book;
    }

    @Override
    public LegerEntry createLegerEntry(LegerEntry entry) {
        legerEntryRepository.save(entry);
        return entry;
    }

    @Override
    public Librarian createLibrarian(Librarian librarian) {
        librarianRepository.save(librarian);
        return librarian;
    }

    @Override
    public LibraryCard createLibraryCard(LibraryCard card) {
        libraryCardRepository.save(card);
        return card;
    }

    @Override
    public LibraryMember createMember(LibraryMember member) {

        // Validate sponsorship - if age is < 13, must have valid sponsor
        if (member.isUnderThirteen() ) {

            // When creating a new member <13 years old, they must have a sponsor.
            if (member.getSponsoredBy() == null) {
                return new LibraryMember();
            }

            // Find the sponsor in the database
            var sponsorFound = memberRepository.findById(member.getSponsoredBy());

            // Make sure the sponsor exists in the DB
            if (sponsorFound.isPresent()) {

                LibraryMember sponsor = sponsorFound.get();

                // Make sure the sponsor is over 13
                if (!sponsor.isUnderThirteen()) {
                    member.setSponsoredBy(sponsor.getId());

                    // Create the library card that will expire in 5 years

                    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
                    Calendar today = Calendar.getInstance(TimeZone.getDefault());
                    today.add(Calendar.YEAR, 5);
                    java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTimeInMillis());

                    LibraryCard card = new LibraryCard(member.getId(), member, timestamp);
            		member.setLibraryCard(card);
                    
                    // If so, save new member who is under 13 to db
                    memberRepository.save(member);
                    return member;
                }
                else {
                    return new LibraryMember();
                }
            }
            else {
                return new LibraryMember();
            }
        }
        else {
            java.sql.Timestamp timestamp = getTimestamp(5);
            LibraryCard card = new LibraryCard(member.getId(), member, timestamp);
    		member.setLibraryCard(card);
            memberRepository.save(member);
            return member;
        }
        
    }

    @Override
    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public HardCopyBook addHardCopy(String bookId) {
        var foundBookInDb = bookRepository.findById(bookId);

        // Make sure book ID is valid
        if (foundBookInDb.isPresent()) {
            HardCopyBook hardCopyBook = new HardCopyBook();
            hardCopyBook.setCurrentCondition(CurrentCondition.NEW);
            hardCopyBook.setAvailable(true);
            hardCopyBook.setBook(foundBookInDb.get());
            hardCopyBookRepository.save(hardCopyBook);
            return hardCopyBook;
        }

        return new HardCopyBook();

    }

    @Override
    public AudioBook addAudiobook(String bookId) {
        var foundBookInDb = bookRepository.findById(bookId);

        // Make sure book ID is valid
        if (foundBookInDb.isPresent()) {
            AudioBook newBookCopy = new AudioBook();
            newBookCopy.setAvailable(true);
            newBookCopy.setBook(foundBookInDb.get());
            audioBookRepository.save(newBookCopy);
            return newBookCopy;
        }

        return new AudioBook();
    }

    @Override
    public boolean deleteAdmin(Integer id) {

        var foundAdmin = adminRepository.findById(id);
        if (foundAdmin.isPresent()) {
            Admin admin = foundAdmin.get();
            adminRepository.delete(admin);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteLibrarian(Integer id) {
        var foundLibrarian = librarianRepository.findById(id);
        if (foundLibrarian.isPresent()) {
            Librarian librarian = foundLibrarian.get();
            librarianRepository.delete(librarian);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteMember(Integer id) {

        var foundMember = memberRepository.findById(id);

        if (foundMember.isPresent()) {
            memberRepository.delete(foundMember.get());

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteBookCopy(Integer bookCopyId) {
        var foundBookCopy = bookCopyRepository.findById(bookCopyId);

        if (foundBookCopy.isPresent()) {
            BookCopy bookCopy = foundBookCopy.get();
            bookCopyRepository.delete(bookCopy);
            return true;
        }

        return false;

    }

    @Override
    public boolean deleteAuthor(Integer id) {

        var found = authorRepository.findById(id);
        if (found.isPresent()) {
            authorRepository.deleteById(id);
            return true;
        }

        return false;
    }


    @Override
    public boolean hasInvalidLibraryCard(LibraryMember member) {

    	// Make sure the library card exists
        var foundCard = libraryCardRepository.findById(member.getLibraryCard().getId());
        if (foundCard.isPresent()) {

        	// Check expiration date
        	TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    		Calendar cardDate = Calendar.getInstance(TimeZone.getDefault());
    		cardDate.setTime(foundCard.get().getExpirationDate());
    		Calendar today = Calendar.getInstance(TimeZone.getDefault());

        	return (cardDate.get(Calendar.YEAR) <= today.get(Calendar.YEAR)) &&
                    ((cardDate.get(Calendar.YEAR) != today.get(Calendar.YEAR)) ||
                            (cardDate.get(Calendar.DAY_OF_YEAR) < today.get(Calendar.DAY_OF_YEAR)));

        }
        else {
        	// No card
        	return true;
        }

    }

    @Override
    public boolean returnBook(Integer memberId, Integer bookCopyId) {

        var foundMemberInDb = memberRepository.findById(memberId);
        var foundBookInDb = bookCopyRepository.findById(bookCopyId);
        LegerEntry legerEntryInDb = legerEntryRepository.findByMemberIdAndBookCopyId(memberId, bookCopyId);

        if (foundMemberInDb.isPresent() && foundBookInDb.isPresent() && legerEntryInDb != null ) {

            // Find the book copy in the database, mark it as available, and save it
            BookCopy bookCopy = foundBookInDb.get();
            bookCopy.setAvailable(true);
            bookCopyRepository.save(bookCopy);

            // Find the leger entry with these two values
            java.sql.Timestamp timestamp = getTimestamp(0);
            legerEntryInDb.setDateReturned(timestamp);
            legerEntryRepository.save(legerEntryInDb);
            return true;
        }

        return false;

    }


    @Override
    public LegerEntry checkOutBookHardCopy(Integer memberId, String bookId) {

        var foundMemberInDb = memberRepository.findById(memberId);
        var foundBookInDb = bookRepository.findById(bookId);

        // Make sure the member ID and book ID are valid
        if (foundMemberInDb.isPresent() && foundBookInDb.isPresent()) {
            System.out.println("Member ID: " + memberId + " or book ID: " + bookId + " are  valid.");
            LibraryMember member = foundMemberInDb.get();
            Book book = foundBookInDb.get();

            // Check that user's library card is active
            if (hasInvalidLibraryCard(member)) {
                System.out.println("Member has invalid library card.");
                return new LegerEntry();
            }

            // Check that we have at least one copy of the book, and it is available
            Iterator<HardCopyBook> availableBooks = findAvailableHardCopies(book).iterator();
            if (!availableBooks.hasNext()) {
                System.out.println("No copies are available.");
                return new LegerEntry();
            }

            HardCopyBook bookToBorrow = availableBooks.next();

            // Finally, check out the book by creating a leger entry
            java.sql.Timestamp timestamp = getTimestamp(0);
            LegerEntry newEntry = new LegerEntry(member.getId(), bookToBorrow.getId(), timestamp, null);
            legerEntryRepository.save(newEntry);

            // Mark book copy as not available
            bookToBorrow.setAvailable(false);
            bookCopyRepository.save(bookToBorrow);

            return newEntry;
        }

        System.out.println("Member ID: " + memberId + " or book ID: " + bookId + " are NOT valid.");
        return new LegerEntry();

    }

    private java.sql.Timestamp getTimestamp(int yearOffset) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar today = Calendar.getInstance(TimeZone.getDefault());
        today.add(Calendar.YEAR, yearOffset);
        return new java.sql.Timestamp(today.getTimeInMillis());
    }

    @Override
    public boolean checkOutAudiobook(Integer memberId, String bookId) {

        var foundMemberInDb = memberRepository.findById(memberId);
        var foundBookInDb = bookRepository.findById(bookId);

        // Make sure the member ID and book ID are valid
        if (foundMemberInDb.isPresent() && foundBookInDb.isPresent()) {

            LibraryMember member = foundMemberInDb.get();
            Book book = foundBookInDb.get();

            // Check that user's library card is active
            if (hasInvalidLibraryCard(member)) {
                System.out.println("That member doesn't have a good library card ");
                return false;
            }

            // Check that we have at least one copy of the book, and it is available
            Iterator<AudioBook> availableBooks = findAvailableAudiobooks(book).iterator();
            if (!availableBooks.hasNext()) {
                System.out.println("doesn't have any available copies");
                return false;
            }

            // Take whatever one floats up first that is available
            AudioBook bookToBorrow = availableBooks.next();

            // Finally, check out the book by creating a leger entry
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
            Calendar today = Calendar.getInstance(TimeZone.getDefault());
            java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTimeInMillis());
            LegerEntry newEntry = new LegerEntry(member.getId(), bookToBorrow.getId(), timestamp, null);
            legerEntryRepository.save(newEntry);

            // Mark book copy as not available
            bookToBorrow.setAvailable(false);
            bookCopyRepository.save(bookToBorrow);

            return true;
        }
        System.out.println("That member/book can't be found " + memberId + " " + bookId);
        return false;

    }

    @Override
    public Set<HardCopyBook> findAvailableHardCopies(Book book) {
        return hardCopyBookRepository.findAvailableBooksById(book.getId());
    }

    @Override
    public Set<AudioBook> findAvailableAudiobooks(Book book) {
        return audioBookRepository.findAvailableBooksById(book.getId());
    }

    @Override
    public Set<Object[]> seeCheckedOutBooksAllTime(Integer memberId) {
        return bookCopyRepository.findCheckedOutBooksAllTime(memberId);
    }

    @Override
    public Set<Object[]> seeCheckedOutBooksCurrently(Integer memberId) {
        return bookCopyRepository.findCheckedOutBooksCurrently(memberId);
    }

    @Override
    public Admin updateAdmin(Integer adminId, Admin admin) {
        var foundUser = adminRepository.findById(adminId);
        if (foundUser.isPresent()) {
            Admin user = foundUser.get();
            user.setPassword(admin.getPassword());
            user.setUsername(admin.getUsername());
            user.setEmail(admin.getEmail());
            user.setLastName(admin.getLastName());
            user.setFirstName(admin.getFirstName());
            user.setDateOfBirth(admin.getDateOfBirth());
            return adminRepository.save(user);
        }

        return new Admin();
    }

    @Override
    public Librarian updateLibrarian(Integer librarianId, Librarian librarian) {
        var foundUser = librarianRepository.findById(librarianId);
        if (foundUser.isPresent()) {
            Librarian user = foundUser.get();
            user.setPassword(librarian.getPassword());
            user.setUsername(librarian.getUsername());
            user.setEmail(librarian.getEmail());
            user.setLastName(librarian.getLastName());
            user.setFirstName(librarian.getFirstName());
            user.setDateOfBirth(librarian.getDateOfBirth());
            user.setDateHired(librarian.getDateHired());
            user.setHasW2OnFile(librarian.getHasW2OnFile());
            return librarianRepository.save(user);
        }

        return new Librarian();
    }

    @Override
    public LibraryMember updateMember(Integer memberId,  LibraryMember libraryMember) {
        var foundUser = memberRepository.findById(memberId);
        if (foundUser.isPresent()) {
            LibraryMember user = foundUser.get();
            user.setPassword(libraryMember.getPassword());
            user.setUsername(libraryMember.getUsername());
            user.setEmail(libraryMember.getEmail());
            user.setLastName(libraryMember.getLastName());
            user.setFirstName(libraryMember.getFirstName());
            user.setDateOfBirth(libraryMember.getDateOfBirth());
            user.setLibraryCard(libraryMember.getLibraryCard());
            return memberRepository.save(user);
        }

        return new LibraryMember();
    }


}
