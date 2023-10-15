package com.libraryProject.assignment.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ItemRequest {

    private String userId;
    private String type;
    private String title;
}
