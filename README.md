# Item Manager
A Java web app that allows users to create and store lists of items (text, images or URLs).
## Details
This project was created using IntelliJ IDEA and is configured to be packaged and executed using Maven.
You can access the web app on http://localhost:8080/ after execution.
## Features
* Lists can be nested within each other and the program automatically saves when any changes are made, for example if a list is renamed.
* List data is stored in a custom format within a text file in order to improve performance.
* Items can be renamed, edited or deleted. The user can search for lists or items using the 2 methods of search provided in the program, deep search (default) or exact search.
  * Exact search is a faster variant provided in case the program is storing a large number of items and the exact name of the list or item is known in advance. 
  * Otherwise, deep search can be used to locate items based on their name or content. For example, a text item can be located using a keyword in the paragraph being stored.
## Screenshots
![Welcome View](/screenshots/welcome_page.png?raw=true)
![Main List View](/screenshots/main_view.png?raw=true)
![List View](/screenshots/list_view.png?raw=true)
![Text Item View](/screenshots/text_item.png?raw=true)
![Image Item View](/screenshots/image_item.png?raw=true)
## License
As this project does not contain a license, default copyright laws apply.
You may not reproduce, distribute, or create derivative works from this work. 
