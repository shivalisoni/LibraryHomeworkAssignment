# LibraryHomeworkAssignment

Overview : The application is designed to manage a library system, allowing customers to borrow and return books, DVDs, VHS tapes, and CDs
The application is developed using SpringBoot 2, it exposes RESTful endpoints to interact with the library system.
Item and User are the core entities representing library items (e.g., books, DVDs) and users. Item entity utilises object-oriented design with inheritance for different item types (Book, DVD, VHS, CD) and a user entity to store user-related information.
Inventory:  Reads the initial inventory of items from a CSV file. The file has been included in the class path.
Testing : To test the RESTful endpoints of the Spring Boot application using Postman, please follow below instructions for the LibraryController:
Assuming a user will only be aware of the item type , title and their id, this functionality accepts following details:
Common for all requests : Headers:Content-Type:application/json
1.Borrow an item :
Method:POST, URL: http://localhost:9090/library/issue 
Body { "type": "Book"
"title": "Java Concurrency In Practice",
"userId": "101"}
2.Return an item :
Method:POST, URL: http://localhost:9090/library/return
Body : Same as borrow.
3. Check availability:
Method:POST, URL: http://localhost:9090/library/availability
Body { "type": "Book"
"title": "Java Concurrency In Practice"}
4. Determine borrowed items for a user
Method:POST, URL: http://localhost:9090/library/userItems 
Body { "userId": "101"}
5. Determine all overdue items:
Method:GET, URL: http://localhost:9090/library/overdue 

6. Determine all available titles:
Method:GET, URL: http://localhost:9090/library/titles 







