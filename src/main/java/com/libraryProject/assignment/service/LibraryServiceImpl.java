package com.libraryProject.assignment.service;

import com.libraryProject.assignment.model.*;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Component
public class LibraryServiceImpl implements LibraryService {


    private static final String INVENTORY_MISMATCH = "Item details do not exist in the inventory" ;
    private static final String BORROWER_MISMATCH = "User's borrower history does not have this item.";
    private static final String RETURN_SUCCESSFUL = "Item returned successfully.";
    private static final List<Item> itemInventory = new CopyOnWriteArrayList<>();

    private List<User> users = new CopyOnWriteArrayList<>();

   static {
        try {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
            ClassPathResource resource = new ClassPathResource("inventory.csv");
            CSVReader csvReader = new CSVReaderBuilder(new FileReader(resource.getFile()))
                    .withCSVParser(csvParser)
                    .withSkipLines(1)  // Skip the header row
                    .build();

        String[] line;
            while ((line = csvReader.readNext()) != null) {
                String uniqueID = line[0];
                String itemID = line[1];
                String type = line[2];
                String title = line[3];
                int quantity = Integer.valueOf(line[4]);
                String borrower = line[5];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String date = line[6];
                LocalDate issueDate = null;
                //convert String to LocalDate
                if(date != null && !date.isBlank()) {
                    issueDate = LocalDate.parse(date, formatter);
                }

                // Create and add items to the inventory list
                Item item;
                switch (type) {
                    case "Book":
                        item = new Book(uniqueID, itemID, type, title, quantity, issueDate,borrower);
                        break;

                    case "DVD":
                        item = new DVD(uniqueID, itemID, type, title, quantity, issueDate, borrower);
                        break;
                    case "VHS":
                        item = new VHS(uniqueID, itemID, type, title, quantity, issueDate,borrower);
                        break;
                    case "CD":
                        item = new CD(uniqueID, itemID, type, title, quantity, issueDate,borrower);
                        break;
                    default:
                        item = null;
                }
                if (item != null) {
                    itemInventory.add(item);
                }
            }

            csvReader.close();
        } catch (IOException e) {
            // Handle IO exceptions (e.g., file not found)
            e.printStackTrace();
        } catch (CsvException e) {
            // Handle CSV parsing exceptions (e.g., invalid CSV format)
            e.printStackTrace();
        }
    }

    private List<Item> getItemDetails(String type, String title){
        return itemInventory.stream()
                .filter(i->(i.getType().equalsIgnoreCase(type) && i.getTitle().equalsIgnoreCase(title))).
                collect(Collectors.toList());
    }


    @Override
    public boolean issueItem(String userId, String type, String title) {
       List<Item> itemList = getItemDetails(type, title);
       if(itemList.size() == 0) return false;
       else{
        if(itemList.get(0) != null && itemList.get(0) .getQuantity()>0) {
            itemList.get(0).setIssueDate(LocalDate.now());
            itemList.get(0).setBorrowerId(userId);
        }
        else if(itemList.get(0) != null && itemList.get(0) .getQuantity() == 0) {
              return false;
           }
        for(Item i : itemList){
            i.setQuantity(i.getQuantity()-1);
        }
        }
       return true;
    }

    @Override
    public String returnItem(String userId, String type, String title) {
        List<Item> itemList = getItemDetails(type, title);
        if(itemList == null || itemList.size() == 0) return INVENTORY_MISMATCH;
       else {
           Optional<Item> item = itemList.stream().filter(i->i.getBorrowerId().equalsIgnoreCase(userId)).sorted(Comparator.comparing(Item::getIssueDate)).findFirst();
           if(item.isPresent()){
               item.get().setBorrowerId("");
               item.get().setIssueDate(null);
               for(Item i : itemList){
                   i.setQuantity(i.getQuantity()+1);
               }
           }
           else return BORROWER_MISMATCH;

           }
       return RETURN_SUCCESSFUL;
    }

    @Override
    public List<Item> getOverdueItems(){
           LocalDate currentDate = LocalDate.now();

        List<Item> overdueItems = new ArrayList<>();
        overdueItems = itemInventory.stream().filter(i->i.getIssueDate() != null).filter(i -> Period.between(i.getIssueDate(), currentDate).getDays()>7)
                .collect(Collectors.toList());

        return overdueItems;
    }
    @Override
    public List<Item> getBorrowedItems(String userId) {
        return itemInventory.stream().filter(item ->item.getBorrowerId().equalsIgnoreCase(userId)).collect(Collectors.toList());
    }

    @Override
    public boolean checkIfAvailable(String type, String title) {
        List<Item> itemList = getItemDetails(type, title);
        if(itemList == null || itemList.size() == 0) return false;
        Optional<Item> itemFound =itemList.stream().filter(i->i.getQuantity()>0).findFirst();
        return itemFound.isPresent();

    }
       @Override
       public List<String> getAvailableTitles(){
           return itemInventory.stream().filter(item -> item.getQuantity()>0).map(Item::getTitle).collect(Collectors.toList());
        }



}
