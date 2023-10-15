package com.libraryProject.assignment.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class User {

    private String userId;
    private String name;
    private List<Item> borrowedItems = new ArrayList<>();



    public void returnItem(Item item) {
        borrowedItems.remove(item);
        item.returnItem();
    }
}
