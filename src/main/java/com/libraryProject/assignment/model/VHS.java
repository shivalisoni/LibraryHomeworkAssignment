package com.libraryProject.assignment.model;

import java.time.LocalDate;

public class VHS extends Item {
    public VHS(String itemId, String uniqueId, String type, String title, int quantity, LocalDate issueDate, String borrowerId) {
        super(itemId, uniqueId, type, title, quantity, issueDate, borrowerId);
    }

    public VHS() {
    }
}
