# ATM Machine

The application simulates an ATM machine. The program provides a GUI that allows the users to select a language. Then the user needs to provide correct ID and PIN. From there, they can view their name and balance. Perform transactions such as deposit and withdraw and view their transaction history. Once the user selects the "Quit" button the program stops. 

## Why you used the technologies you used? 
I used Java Swing and AWT because I think their design looks best. The CardLayout class is used to create a GUI with a few panels. I also used Locale and   ResourceBundle classes to create the multiple language support. 

### Challenges faced
I had a hard time creating creating the GUI and also I had an error for the longest time regarding the user authentication. Also for some reason the.  program was crashing when the user selects Bulgarian, but I managed to fix it after adding a variable I forgot to mention in the messages_bg_BG.properties.  file. 

### How to install and run the program?
Go to https://github.com/Breezlol/ATM.git. 
Download the .jar file and open it.  
The application should open in a new window. 
Ive added some sample IDs and PINs for testing ID:2345 PIN:1234;  ID:1234 PIN:5678; ID:6789 PIN:1256;

#### How to use it?
Select your language from the language selection screen. 
Enter your user ID and PIN on the login screen. 
After logging in you will be taken to the main menu. 
From the main menu, you can select options to deposit, withdraw or view transaction history. 
After performing a transaction, your updated balance will be displayed on the main menu.  
If you want to add a new user you should go to public class ATM. There you should see some sample user information. To add a new user you should type: users.add(new User(int, int, String, double));   
