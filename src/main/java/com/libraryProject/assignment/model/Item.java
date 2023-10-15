package com.libraryProject.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public abstract class Item {

    private String itemId;
    public String uniqueId;
    private String type;
    private String title;
    private int quantity;
    private LocalDate issueDate;
    private String borrowerId;



    public void returnItem() {
        quantity++;
    }

}
