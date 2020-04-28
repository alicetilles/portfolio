package edu.northeastern.cs5200.dataloader;


import edu.northeastern.cs5200.daos.LibraryDao;
import edu.northeastern.cs5200.models.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.javatuples.Triplet;
import java.sql.Timestamp;
import java.util.*;

/**
 * This class queries the GoogleBooks API and converts it into our data model, and saves the data.
 */
@Component
public class GoogleBooksAPI {

    LibraryDao libraryDao;
    List<String> famousBooks;

    public GoogleBooksAPI(LibraryDao libraryDao){
        this.libraryDao = libraryDao;
        this.famousBooks = new ArrayList<>();

    }

    public void loadFamousBooks() throws FileNotFoundException {

        File bookList = new File("./src/main/resources/static/famousBookList.txt");
        Scanner sc = new Scanner(bookList);
        while (sc.hasNextLine()) {
            String bookName = sc.nextLine();
            this.famousBooks.add(replaceSpacesWithDashes(bookName));
        }

    }

    public void seedDatabaseWithPopularBooks(int limit) throws ParseException, XPathExpressionException, IOException {

        int count = 0;
        String googleKey  = "";
        for (String bookTitle : famousBooks) {
            System.out.println("adding: " + bookTitle);
            loadBooksFromAPI("https://www.googleapis.com/books/v1/volumes?q="+bookTitle+"&key=" + googleKey + "&orderBy=relevance&maxResults=1");
            count++;
            if (count == limit) {
                return;
            }
        }

        pruneDuplicateAuthors();

    }


    /**
     * Loads into the database 0 to 6 copies of a book to the library. Up to 3 each of audiobook vs hard copy.
     * Randomly chooses a condition for hard copy.
     * Seeds the library!
     * @param book The book to make copies of
     */
    private void generateBookCopiesRandomly(Book book, JSONObject bookCopyDetails) {

        int max = 3;
        int min = 0;

        // Hard copies
        int numCopies = (int)(Math.random()*((max-min)+1))+min;
        for (int i = 0; i < numCopies; i++) {

            HardCopyBook newCopy = new HardCopyBook();
            newCopy.setBook(book);
            newCopy.setAvailable(true);
            newCopy.setCurrentCondition(CurrentCondition.getRandomCondition());
            newCopy.setNumPages((Integer) bookCopyDetails.get("pageCount"));
            libraryDao.createHardCopyBook(newCopy);
        }

        // Audio books
        numCopies = (int)(Math.random()*((max-min)+1))+min;
        for (int i = 0; i < numCopies; i++) {

            AudioBook newCopy = new AudioBook();
            newCopy.setBook(book);
            newCopy.setAvailable(true);
            libraryDao.createAudioBook(newCopy);

        }

    }

    public void pruneDuplicateAuthors() {

        List<Author> allAuthors = libraryDao.findAllAuthors();

        // For each author in the DB,
        for (Author a : allAuthors) {

            // see if there is more than one.
            List<Author> duplicateAuthors = libraryDao.findAuthorsByFullName(a.getFirstName(), a.getLastName());

            // If not, continue.
            if (duplicateAuthors.size() == 1) {
                continue;
            }

            else {
                // If so, get the set.
                duplicateAuthors.remove(a);
                for (Author duplicate : duplicateAuthors) {
                    // Iterate through each of their books, assigning it to this original one.
                    Set<Book> books = duplicate.getBooksWritten();

                    for (Book b : books) {
                        b.setAuthor(a);
                        libraryDao.createBook(b);
                    }

                    // Delete the duplicate author from the  db and the set.
                    allAuthors.remove(duplicate);

                }
            }


        }






    }

    /**
     * Makes a connection w/ Google Books API based on input string. Extracts data and puts it in our library database.
     * @param url the URI to query
     * @throws IOException
     * @throws ParseException
     */
    public void loadBooksFromAPI(String url) throws IOException, ParseException, XPathExpressionException {

        // Cast API call into URL object
        URL myURL = new URL(url);

        // Cast into HttpURLConnection object, specify GET request, start connection
        HttpURLConnection conn = (HttpURLConnection)myURL.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        // Make sure connection works

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("HttpResponseCode: " + conn.getResponseCode());
        }


        // Retrieve JSON data
        else {
            StringBuilder result = new StringBuilder("");

            Scanner sc = new Scanner(myURL.openStream());
            while (sc.hasNext()) {
                result.append(sc.nextLine());
            }

            sc.close();
            conn.disconnect();

            // This tool will help us parse the unstructured data
            JSONParser parse = new JSONParser();

            // Convert string object into JSON objects
            JSONObject jObj = (JSONObject)parse.parse(result.toString());

            // Convert JSON object into JSONArray object:
            JSONArray jsonArray = (JSONArray) jObj.get("items");

            // Get data in that array
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject book = (JSONObject) jsonArray.get(i);
                Triplet<Book, Author, JSONObject> bookAndAuthor = JSONtoBook(book);

                // It will be null if there was a problem extracting any data
                if (bookAndAuthor == null) {
                    continue;
                }
                Book newBook = bookAndAuthor.getValue0();
                Author newAuthor = bookAndAuthor.getValue1();
                JSONObject details = bookAndAuthor.getValue2();

                // Save it to database
                newBook.setAuthor(newAuthor);
                //libraryDao.createAuthor(newAuthor);
                libraryDao.createBook(newBook);

                // Create book copies
                generateBookCopiesRandomly(newBook, details);

            }


        }

    }


    /**
     * Util function to translate a full name into a url-digestible name (e.g., "John Holmes -> John-Holmes")
     */
    private String replaceSpacesWithDashes(String input) {

        StringBuilder output = new StringBuilder("");
        for (char c : input.toCharArray()) {
            if (c == ' ') {
                output.append('-');
            }
            else {
                output.append(c);
            }
        }
        return output.toString();
    }

    /**
     * This method takes as input a string date from Google Books API in one of these formats:
     *      Null
     *      "2019-08-27",
     *      "2020-03",
     *      "2020-03",
     * Then it converts it to Java.SQL format. Defaults are Y 2000, M 1, D 1.
     * @param input Google Book API date string
     * @return SQL date string
     */
    private static Timestamp StringToDate(String input) {

        // Default values
        int year = 2000;
        int month = 1;
        int day = 1;

        // Extract as much info as we can from the date
        if (input!= null) {
            year = Integer.valueOf(input.substring(0,4));

            if (input.length() > 4) {
                month = Integer.valueOf(input.substring(5,6));
            }

            if (input.length() > 7) {
                day = Integer.valueOf(input.substring(8,9));
            }

        }

        // Convert to SQL
        Calendar cal = Calendar.getInstance();
        cal.set( cal.YEAR, year );
        cal.set( cal.MONTH, month );
        cal.set( cal.DATE, day );
        return new java.sql.Timestamp( cal.getTime().getTime() );
    }


    /**
     * This method converts a JSONObject book from the Google books API to a Book and Author.
     * @param inputBook from google books API
     * @return the same info in our data model
     */
    private Triplet<Book,Author,JSONObject> JSONtoBook(JSONObject inputBook) throws IOException, XPathExpressionException {

        // Extracting all the info out of the JSON object
        try {
            JSONObject volumeInfo = (JSONObject) inputBook.get("volumeInfo");
            String id = (String) inputBook.get("id");
            JSONArray industryIdentifiers = (JSONArray) volumeInfo.get("industryIdentifiers");
            JSONObject identifierOne = (JSONObject) industryIdentifiers.get(0);
            String isbn = (String) identifierOne.get("identifier");
            JSONArray subjects = (JSONArray) volumeInfo.get("categories");
            Timestamp publishedDate = StringToDate((String)volumeInfo.get("publishedDate"));
            String title = (String) volumeInfo.get("title");
            Integer pageCount = ((Long)volumeInfo.get("pageCount")).intValue();
            String subject = "";
            try {
                subject = (String) subjects.get(0);
            } catch (NullPointerException e) {
                subject = null;
            }
            JSONArray authors = (JSONArray) volumeInfo.get("authors");
            String authorFullName = "";
            try {
                authorFullName = (String) authors.get(0);
            } catch (NullPointerException e) {
                authorFullName = null;
            }
            JSONObject imageLinks = (JSONObject) volumeInfo.get("imageLinks");
            String thumbnail = (String) imageLinks.get("smallThumbnail");

            // Create a new book with that info
            Book newBook = new Book();
            newBook.setId(id);
            newBook.setYearPublished(publishedDate);
            newBook.setGenre(subject);
            newBook.setIsbn(isbn);
            newBook.setTitle(title);
            newBook.setThumbnailURL(thumbnail);

            // And a new author
            GoodReadsAPI goodReadsAPI = new GoodReadsAPI();
            Author newAuthor = new Author();
            if (authorFullName != null ) {
                String goodReadsAuthorID = goodReadsAPI.authorToAuthorID(replaceSpacesWithDashes(authorFullName));
                newAuthor = goodReadsAPI.idToAuthor(goodReadsAuthorID);
            }
            else {
                System.out.println("Book without author?: " + newBook.getTitle());
            }

            // And book copy details
            JSONObject details = new JSONObject();
            details.put("pageCount", pageCount);

            // Return the two new objects
            return new Triplet<>(newBook,newAuthor, details);

        } catch (NullPointerException e) {
            return null;
        }

    }


}
