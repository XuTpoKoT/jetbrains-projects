package bs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String DBName = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-filename") && i != args.length - 1) {
                DBName = args[i + 1];
                break;
            }
        }

        String url = "jdbc:sqlite:" + DBName;
        // String url = "jdbc:sqlite:\\ideaProjects\\SimpleBakingSystem\\SimpleBankingSystem\\task\\" + DBName;
        // System.out.println(url);

        BankingSystem bankingSystem = new BankingSystem();
        Scanner scanner = new Scanner(System.in);

        try {
            bankingSystem.connect(url);
            int action = -1;
            do {
                UserInterface.printMenu();
                try {
                    action = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("\nEnter number!");
                    continue;
                }

                switch (action) {
                    case 1:
                        bankingSystem.createAccount();
                        break;
                    case 2:
                        System.out.println("\nEnter your card number:");
                        String cardNumber;
                        cardNumber = scanner.nextLine().trim();

                        System.out.println("Enter your PIN:");
                        String cardPIN = scanner.nextLine().trim();
                        if (cardPIN.length() != 4) {
                            System.out.println("\nWrong card number or PIN!");
                        }

                        if (!bankingSystem.logIntoAccount(cardNumber, cardPIN))
                            continue;
                        int accountAction = -1;
                        do {
                            UserInterface.printAccountActions();
                            try {
                                accountAction = Integer.parseInt(scanner.nextLine().trim());
                            } catch (NumberFormatException e) {
                                System.out.println("\nEnter number!");
                                continue;
                            }

                            switch (accountAction) {
                                case 1:
                                    System.out.println("\nBalance : " + bankingSystem.getBalance(cardNumber, cardPIN));
                                    break;
                                case 2:
                                    break;
                                case 0:
                                    action = 0;
                                    break;
                                default:
                                    System.out.println("Wrong action number!");
                            }
                        } while (accountAction != 0 && accountAction != 2);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Wrong action number!");
                }
            } while (action != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                bankingSystem.closeConnection();
            } catch (SQLException ex) {

            }
        }

        System.out.println("\nBye!");
    }
}
