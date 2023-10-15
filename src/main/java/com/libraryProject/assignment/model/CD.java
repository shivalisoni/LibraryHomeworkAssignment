package com.libraryProject.assignment.model;

import java.time.LocalDate;

public class CD extends Item {

    public CD(String itemId, String uniqueId, String type, String title, int quantity, LocalDate issueDate, String borrowerId) {
        super(itemId, uniqueId, type, title, quantity, issueDate, borrowerId);
    }

    public CD() {
    }
}
