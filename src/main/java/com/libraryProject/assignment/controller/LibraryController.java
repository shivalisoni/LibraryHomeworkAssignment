package com.libraryProject.assignment.controller;


import com.libraryProject.assignment.model.Item;
import com.libraryProject.assignment.model.ItemRequest;
import com.libraryProject.assignment.service.LibraryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {

    private final LibraryServiceImpl libraryService;

    public LibraryController(LibraryServiceImpl libraryService) {
        this.libraryService = libraryService;
    }


    @PostMapping("/issue")
    public ResponseEntity<String> issueItem(@RequestBody ItemRequest itemRequest) {

        if(libraryService.issueItem(itemRequest.getUserId(), itemRequest.getType(), itemRequest.getTitle())) {
            LocalDate returnDate = LocalDate.now().plusDays(7);
            return ResponseEntity.ok("Item successfully borrowed, expected return date:" + returnDate);
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item is not available.");
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnItem(@RequestBody ItemRequest itemRequest) {
        String returnMsg = libraryService.returnItem(itemRequest.getUserId(), itemRequest.getType(), itemRequest.getTitle());
            return ResponseEntity.ok(returnMsg);
    }

    @PostMapping("/availability")
    public ResponseEntity<String> checkAvailability(@RequestBody ItemRequest item) {
        boolean available = libraryService.checkIfAvailable(item.getType(), item.getTitle());
        if (available) {
            return ResponseEntity.ok("Item is available.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item is not available.");
        }
    }

    @PostMapping("/userItems")
    public List<Item> getBorrowedItems(@RequestBody ItemRequest itemRequest) {
        return libraryService.getBorrowedItems(itemRequest.getUserId());
    }

    @GetMapping("/overdue")
    public List<Item> getOverdueItems() {
        return libraryService.getOverdueItems();
    }

    @GetMapping("/titles")
    public List<String> getAvailableTitles() {
        return libraryService.getAvailableTitles();
    }
}
