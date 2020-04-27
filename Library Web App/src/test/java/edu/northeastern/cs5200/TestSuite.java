package edu.northeastern.cs5200;

import edu.northeastern.cs5200.daos.*;
import edu.northeastern.cs5200.dataloader.GoogleBooksAPI;
import edu.northeastern.cs5200.dataloader.SeedDatabase;
import static org.junit.Assert.*;

import edu.northeastern.cs5200.models.*;
import org.json.simple.parser.ParseException;
import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;

import javax.validation.constraints.AssertTrue;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestSuite {

	@Autowired
	LibraryImpl libraryDao;

	@Test
	public void loadBooks() throws IOException, ParseException, XPathExpressionException {
		libraryDao.dropBooks();
		GoogleBooksAPI api = new GoogleBooksAPI(libraryDao);
//		api.loadBooksFromAPI("https://www.googleapis.com/books/v1/volumes?q=cool&key=AIzaSyDzAEzIpOLfuwaEQcXsB-5vSN7b7lzJiMc");

		api.loadFamousBooks();
		api.seedDatabaseWithPopularBooks(100);
		api.pruneDuplicateAuthors();

	}


	@Test
	public void loadUsers() throws java.text.ParseException {
		libraryDao.dropUsers();
		SeedDatabase seed = new SeedDatabase(libraryDao);
		seed.createUsers();
		seed.rentBooks();
	}


}
