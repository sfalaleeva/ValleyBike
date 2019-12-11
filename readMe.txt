A4-G6 ValleyBikeSim 

To import our project from the .zip to eclipse: 
	1. Click File --> Import 
	2. Click General --> Existing Projects Into Workspace, then click next
	3. Have Select Archive File marked and click finish

Currently, everything is run in Eclipse and its console. 
Have Junit installed and working in order to get no errors

Our main class is the ValleyBikeSim. 

We have 3 accounts already accessible on start-up: 
1. Admin, who is able to access more/do different things than a user. 
	- To login as admin:
		- select login option from main menu 
		- type "admin" when prompted for email. 
2. Two user accounts stored in user_data.csv file 
	- To login as one of those users :
		- select login from main menu
		- enter "sarah@gmail.com" when prompted for email
		- enter "Pwd123" when prompted for password
		- Second preloaded user: username "mary@hotmail.com" -- password "Mary77"

If you register a new user that account will become accessible for login during current and any further sessions.

Our additional feature is a map of stations, to access it - select 'Show station map' option from the user menu.


Coding References: 
https://stackoverflow.com/questions/8204680/java-regex-email
https://www.mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
https://dzone.com/articles/java-string-format-examples
https://stackoverflow.com/questions/6000810/printing-with-t-tabs-does-not-result-in-aligned-columns
https://www.geeksforgeeks.org/writing-a-csv-file-in-java-using-opencsv/
