package edu.northeastern.cs5200.dataloader;

import edu.northeastern.cs5200.models.Author;
import org.xml.sax.InputSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Scanner;

public class GoodReadsAPI {

    /**
     * Here is your developer key for using the Goodreads API. This key must be appended to every request using the form variable 'key'. (If you're using our write API, you'll need your secret too.)
     *
     * key: QkpUAjvtyYyvQf3WSsYCfQ
     * secret: ijf4LlK4Cq5Zi18yhHU6g7AdYMRfimeEDh6Ewuo99Po
     */


    public GoodReadsAPI(){
        /**
         * Initializes the API.
         */
    }

    /**
     * This method takes as input a string date from GoodReads API in this format:
     *      Null
     *      "1963/09/03"
     * Then it converts it to Java.SQL format. Defaults are Y 2000, M 1, D 1.
     * @param input Goodreads API date string
     * @return SQL date string
     */
    private static Timestamp stringToDate(String input) {

        // Default values
        Integer year = 0000;
        Integer month = 1;
        Integer day = 1;

        // Extract as much info as we can from the date
        if (input != null ) {
            if (input.length() != 0) {
                year = Integer.valueOf(input.substring(0,4));

                if (input.length() > 4) {
                    month = Integer.valueOf(input.substring(5,6));
                }

                if (input.length() > 7) {
                    day = Integer.valueOf(input.substring(8,9));
                }
            }
        }
        else {
            return null;
        }

        // Convert to SQL
        Calendar cal = Calendar.getInstance();
        cal.set( cal.YEAR, year );
        cal.set( cal.MONTH, month );
        cal.set( cal.DATE, day );
        return new java.sql.Timestamp( cal.getTime().getTime() );
    }

    /**
     * Extracts the first name (everything up to the first space)
     */
    private String getFirstName(String fullName) {

        char[] chars = fullName.toCharArray();
        StringBuilder firstName = new StringBuilder();
        for (char c : chars ) {

            if (c == ' ') {
                return firstName.toString();
            }

            firstName.append(c);

        }
        return firstName.toString();
    }

    /**
     * Extracts the last name (everything after the first space)
     */
    private String getLastName(String fullName) {

        char[] chars = fullName.toCharArray();
        StringBuilder lastName = new StringBuilder();
        boolean last = false;
        for (char c : chars ) {

            if (c == ' ' && !last) {
                last = true;
                continue;
            }

            if (last) {
                lastName.append(c);
            }


        }
        return lastName.toString();
    }

    /**
     * Takes as input a goodreads author id, queries their api, and extracts info about the author, storing it
     * in an Author object.
     * @param authorId  the goodreads author id
     * @return the Author object created
     * @throws IOException
     * @throws XPathExpressionException
     */
    public Author idToAuthor(String authorId) throws IOException, XPathExpressionException {

        String url = "https://www.goodreads.com/author/show/" + authorId + "?key=QkpUAjvtyYyvQf3WSsYCfQ";

        // Cast API call into URL object
        URL myURL = new URL(url);

        // Cast into HttpURLConnection object, specify GET request, start connection
        HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        // Make sure connection works
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("HttpResponseCode: " + conn.getResponseCode());
        }

        // Retrieve XML data
        else {

            StringBuilder result = new StringBuilder();

            Scanner sc = new Scanner(myURL.openStream());
            while (sc.hasNext()) {
                result.append(sc.nextLine());
            }

            sc.close();
            conn.disconnect();

            // Extract author values
            InputSource source = new InputSource(new StringReader(result.toString()));
            XPath xpath = XPathFactory.newInstance().newXPath();
            Object author = xpath.evaluate("/GoodreadsResponse/author", source, XPathConstants.NODE);
            String fullName = xpath.evaluate("name", author);
            String bornDate = xpath.evaluate("born_at", author);
            String diedDate = xpath.evaluate("died_at", author);
            String hometown = xpath.evaluate("hometown", author);

            // Create new object
            Author newAuthor = new Author();
            newAuthor.setFirstName(getFirstName(fullName));
            newAuthor.setLastName(getLastName(fullName));
            newAuthor.setHometown(hometown);
            if (diedDate.length() != 0) {
                newAuthor.setDateOfDeath(stringToDate((diedDate)));
            }
            if (bornDate.length() != 0) {
                newAuthor.setDateOfBirth(stringToDate(bornDate));
            }

            return newAuthor;

        }


    }

    /**
     * Takes as  input an author's name and searches for their goodreads ID
     * @param authorName an author's name
     * @return goodreads ID
     * @throws IOException
     * @throws XPathExpressionException
     */
    public String authorToAuthorID(String authorName) throws IOException, XPathExpressionException {


        String url = "https://www.goodreads.com/api/author_url/" + authorName + "?key=QkpUAjvtyYyvQf3WSsYCfQ";

        // Cast API call into URL object
        URL myURL = new URL(url);

        // Cast into HttpURLConnection object, specify GET request, start connection
        HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        // Make sure connection works
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("HttpResponseCode: " + conn.getResponseCode());
        }

        // Retrieve JSON data
        else {
            StringBuilder result = new StringBuilder();

            Scanner sc = new Scanner(myURL.openStream());
            while (sc.hasNext()) {
                result.append(sc.nextLine());
            }

            sc.close();
            conn.disconnect();

            InputSource source = new InputSource(new StringReader(result.toString()));
            XPath xpath = XPathFactory.newInstance().newXPath();
            Object author = xpath.evaluate("/GoodreadsResponse/author", source, XPathConstants.NODE);
            return xpath.evaluate("@id", author);

        }
    }



}
