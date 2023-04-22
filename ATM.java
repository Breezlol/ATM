/**
 * File: ATM.java
 * Date: 04/22/2023
 * @author Yoan Avramov
 *
 The ATM class represents a ATM machine with basic functionalities like login, deposit, withdrawal, and transaction history.
 It uses Swing GUI library for creating a user interface and supports two languages, English and Bulgarian.
 The ATM class contains static inner classes User and Transaction that represent a user and a transaction.
 The main method of the ATM class initializes the GUI and starts the program by asking the user to select their preferred language.
 The createAndShowGUI method creates the main JFrame and initializes the login panel. It also takes a Consumer<User> parameter which is used to handle the event when the user logs in successfully.
 The createLoginPanel method creates the login panel and handles the login process. It takes a JPanel parameter which is the main panel of the application, a Locale parameter, which represents the current language, and a Consumer<User> parameter, which is used to handle the event when the user logs in successfully.
 The createMenuPanel method creates the menu panel and adds functionalities like deposit, withdrawal, transaction history, and quitting the application.
 The checkCredentials method checks if the provided credentials (user id and pin) match with any existing user in the users list. If the credentials are valid, it returns the user object, otherwise, it returns null.
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Consumer;

class Transaction {
    private String type;
    private double amount;

    /**
     * Constructs a Transaction object with the specified type and amount.
     *
     * @param  the type of the transaction (Deposit or Withdraw)
     * @param  the amount of the transaction
     */

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class User {
    private int id;
    private int pin;
    private String name;
    private double balance;
    private List<Transaction> transactions;

    /**
     * Constructs a User object with the specified id, pin, name, and balance.
     *
     * @param  the id of the user
     * @param  the pin of the user
     * @param  the name of the user
     * @param  the balance of the user
     */

    public User(int id, int pin, String name, double balance) {
        this.id = id;
        this.pin = pin;
        this.name = name;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }
    /**
     * @return the id of the user
     */
    public int getId() {
        return id;
    }
    /**
     * @return the pin of the user
     */
    public int getPin() {
        return pin;
    }
    /**
     * @return the name of the user
     */
    public String getName() {
        return name;
    }
    /**
     * @return the balance of the user
     */
    public double getBalance() {
        return balance;
    }
    /**
     * @param balance the new balance of the user
     * Sets the balance of the user to the specified value.
     */

    public void setBalance(double balance) {
        this.balance = balance;
    }
    /**
     * @return a list of transactions made by the user
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }
}


public class ATM {

    private static List<User> users = new ArrayList<>();
    private static CardLayout cardLayout = new CardLayout();
    private static User loggedInUser;
    private static JPanel mainPanel = new JPanel();

    static {
        // Initialize some sample id, pin and names
        users.add(new User(2345, 1234, "Yoan Avramov", 1000));
        users.add(new User(1234, 5678, "Georgi Ivanov", 500));
        users.add(new User(6789, 1256, "Ivan Petrov", 700));
    }
    /**
     * The main method of the ATM program.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Show the language selection dialog and initialize the GUI with the selected language
            String[] languages = {"English", "Български"};
            int selectedLanguage = JOptionPane.showOptionDialog(
                    null,
                    "Choose your language:",
                    "Language Selection",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    languages,
                    languages[0]
            );

            Locale locale;

            if (selectedLanguage == 1) {
                locale = new Locale("bg", "BG");
            } else {
                locale = new Locale("en", "US");
            }

            createAndShowGUI(locale, user -> {
                // Handle the event when the user logs in successfully
                loggedInUser = user;
                JPanel menuPanel = createMenuPanel(mainPanel, locale, loggedInUser);
                mainPanel.add(menuPanel, "Menu");
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                mainPanel.setLayout(cardLayout);
                cardLayout.show(mainPanel, "Menu");
            });

        });
    }
    /**
     * Creates and shows the main GUI with the specified locale and event handler for user login.
     *
     * @param the locale of the application
     * @param the event handler for user login
     */

    private static void createAndShowGUI(Locale locale, Consumer<User> onUserLoggedIn) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        JFrame frame = new JFrame(bundle.getString("atm"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 300));

        mainPanel.setLayout(new CardLayout());

        JPanel loginPanel = createLoginPanel(mainPanel, locale, onUserLoggedIn);

        mainPanel.add(loginPanel, "Login");

        frame.getContentPane().add(mainPanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
/**
 * Creates the login panel and handles the login process.
 *
 * @param the main panel of the application
 * @param the locale of the application
 * @paramb the event handler for user login
 * @return the login panel
 */


    private static JPanel createLoginPanel(JPanel mainPanel, Locale locale, Consumer<User> onUserLoggedIn) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        JPanel loginPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        loginPanel.add(new JLabel(bundle.getString("enter_id")), gbc);
        gbc.gridy++;
        JTextField idField = new JTextField(10);
        loginPanel.add(idField, gbc);
        gbc.gridy++;
        loginPanel.add(new JLabel(bundle.getString("enter_pin")), gbc);
        gbc.gridy++;
        JPasswordField pinField = new JPasswordField(10);
        loginPanel.add(pinField, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        JButton loginButton = new JButton(bundle.getString("login"));
        loginButton.addActionListener(e -> {
            int enteredID = Integer.parseInt(idField.getText());
            int enteredPIN = Integer.parseInt(new String(pinField.getPassword()));

            User authenticatedUser = checkCredentials(enteredID, enteredPIN);
            if (authenticatedUser != null) {
                onUserLoggedIn.accept(authenticatedUser);
            } else {
                JOptionPane.showMessageDialog(mainPanel, bundle.getString("invalid_id_pin"), bundle.getString("error"), JOptionPane.ERROR_MESSAGE);
            }
        });
        loginPanel.add(loginButton, gbc);

        return loginPanel;
    }
    /**
     * Creates the menu panel for the logged-in user.
     *
     * @param the main panel of the application
     * @param the locale of the application
     * @param the logged-in user
     * @return the menu panel
     */

    private static JPanel createMenuPanel(JPanel mainPanel, Locale locale, User loggedInUser) {

        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        JPanel menuPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        // Add user name and balance labels
        JLabel nameLabel = new JLabel(loggedInUser.getName());
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        menuPanel.add(nameLabel, gbc);

        JLabel balanceLabel = new JLabel(String.format(bundle.getString("balance") + ": $%.2f", loggedInUser.getBalance()));
        gbc.gridy = 1;
        menuPanel.add(balanceLabel, gbc);

        // Reset the GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add deposit button
        JButton depositButton = new JButton(bundle.getString("deposit"));
        depositButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(mainPanel, bundle.getString("enter_deposit_amount"));
            if (input != null) {
                double depositAmount = Double.parseDouble(input);
                double newBalance = loggedInUser.getBalance() + depositAmount;
                loggedInUser.setBalance(newBalance);
                loggedInUser.getTransactions().add(new Transaction("Deposit", depositAmount));
                JOptionPane.showMessageDialog(mainPanel, String.format(bundle.getString("deposit_success"), newBalance), bundle.getString("deposit"), JOptionPane.INFORMATION_MESSAGE);

                // Update the balance label
                balanceLabel.setText(String.format(bundle.getString("balance") + ": $%.2f", newBalance));
            }
        });
        menuPanel.add(depositButton, gbc);
        // Add withdraw button
        gbc.gridy++;
        JButton withdrawButton = new JButton(bundle.getString("withdraw"));
        withdrawButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(mainPanel, bundle.getString("enter_withdraw_amount"));
            if (input != null) {
                double withdrawalAmount = Double.parseDouble(input);
                double currentBalance = loggedInUser.getBalance();
                if (withdrawalAmount <= currentBalance) {
                    double newBalance = currentBalance - withdrawalAmount;
                    loggedInUser.setBalance(newBalance);
                    loggedInUser.getTransactions().add(new Transaction("Withdraw", withdrawalAmount));
                    JOptionPane.showMessageDialog(mainPanel, String.format(bundle.getString("withdraw_success"), newBalance), bundle.getString("withdraw"), JOptionPane.INFORMATION_MESSAGE);

                    // Update the balance label
                    balanceLabel.setText(String.format(bundle.getString("balance") + ": $ %.2f", newBalance));
                } else {
                    JOptionPane.showMessageDialog(mainPanel, bundle.getString("insufficient_balance"), bundle.getString("error"), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        menuPanel.add(withdrawButton, gbc);

        // Add transaction history button
        gbc.gridy++;
        JButton historyButton = new JButton(bundle.getString("transaction_history"));
        historyButton.addActionListener(e -> {
            StringBuilder history = new StringBuilder();
            for (Transaction transaction : loggedInUser.getTransactions()) {
                history.append(String.format("%s: $%.2f%n", transaction.getType(), transaction.getAmount()));
            }
            JTextArea textArea = new JTextArea(history.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(300, 150));
            JOptionPane.showMessageDialog(mainPanel, scrollPane, bundle.getString("transaction_history"), JOptionPane.INFORMATION_MESSAGE);
        });
        menuPanel.add(historyButton, gbc);

        // Add quit button
        gbc.gridy++;
        JButton quitButton = new JButton(bundle.getString("quit"));
        quitButton.addActionListener(e -> System.exit(0));
        menuPanel.add(quitButton, gbc);

        return menuPanel;
    }
    /**
     * Checks the entered user credentials and returns the authenticated user, or null if the credentials are invalid.
     *
     * @param the entered user id
     * @param the entered user pin
     * @return the authenticated user or null if the credentials are invalid
     */
    private static User checkCredentials(int id, int pin) {
        for (User user : users) {
            if (user.getId() == id && user.getPin() == pin) {
                return user;
            }
        }
        return null;
    }
}