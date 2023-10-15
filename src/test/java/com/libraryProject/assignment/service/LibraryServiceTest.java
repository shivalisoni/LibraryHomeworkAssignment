package com.libraryProject.assignment.service;

import com.libraryProject.assignment.model.Book;
import com.libraryProject.assignment.model.DVD;
import com.libraryProject.assignment.model.Item;
import com.libraryProject.assignment.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.ArrayList;
import java.util.List;

public class LibraryServiceTest {

    @InjectMocks
    private final LibraryServiceImpl libraryService = new LibraryServiceImpl();


    private User user;

    User user1;
    User user2;

    @BeforeTestClass
    public void setUp(){
        List<Item> items = new ArrayList<Item>();

        Item dvd = new DVD();
        dvd.setTitle("Pi");
        dvd.setQuantity(0);
        dvd.setType("dvd");
        dvd.setUniqueId("5");
        dvd.setItemId("105");

        items.add(dvd);
        List<Item> borrowedItems = new ArrayList<>();
        user1 = new User("1", "Mike", borrowedItems);
        user2 = new User("2", "John", items);
    }

    @Test
    public void testBorrowItem() {
        setUp();


        // Create test user and item

        // Call the method to be tested
        // Assertions
        // Verify that the user has the item in their borrowed items
        assert(libraryService.issueItem("102", "DVD","Pi"));
    }

    @Test
    public void testReturnItem() {
        setUp();
        String msg = libraryService.returnItem("104", "DVD","Pi");
        // Call the method to be tested
        assert(msg.equals("Item returned successfully."));
    }

    @Test
    public void testItemAvailability() {
        setUp();

        // Call the method to be tested
        assert(!libraryService.checkIfAvailable("ab", "abc"));
    }

    @Test
    public void testBorrowItemNotAvailable() {
        setUp();
        // Call the method to be tested
        // Call the method to be tested
        // Assertions
        // Verify that the item's quantity has decreased
        // Verify that the user has the item in their borrowed items
        assert(!libraryService.issueItem("110", "CD", "ABC"));
    }

    @Test
    public void testOverdueItems() {
        setUp();
        // Call the method to be tested
        List<Item> items = libraryService.getOverdueItems();

        // Assertions
        // Verify that the item's quantity has increased
        // Verify that the item has been removed from the user's borrowed items
        assert(null != items);
    }

    }
