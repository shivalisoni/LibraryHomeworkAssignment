package com.libraryProject.assignment.service;

import com.libraryProject.assignment.model.Item;

import java.util.List;

public interface LibraryService {

    boolean issueItem(String userId, String type, String title);
    String returnItem(String userId, String type, String title);
    List<String> getAvailableTitles();
    List<Item> getOverdueItems();
    List<Item> getBorrowedItems(String userId);
    boolean checkIfAvailable(String type, String title);

}
