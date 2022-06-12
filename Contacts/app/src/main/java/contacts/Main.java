package contacts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        File file;
        Scanner scanner = new Scanner(System.in);
        Contacts contacts = new Contacts();
        String action = "";
        if (args.length == 2) {
            file = new File(args[1]);
            if (file.exists()) try {
                contacts = (Contacts) SerializationUtils.deserialize(args[1]);
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        do {
            System.out.println("Enter action (add, list, search, count, exit):");
            action = scanner.nextLine().trim();

            switch (action) {
                case "add":
                    contacts.add();
                    break;
                case "count":
                    System.out.println("The Phone Book has " + contacts.count() + " records.");
                    break;
                case "list": {
                    if (contacts.list() == 1) { // returning error code is OK?
                        continue;
                    }
                    System.out.println("Enter action ([number], back):");
                    action = scanner.nextLine().trim();
                    if (action.equals("back"))
                        break;

                    int recordNumber;
                    try {
                        recordNumber = Integer.parseInt(action);
                        if (recordNumber <= 0 || recordNumber > contacts.count()) {
                            System.out.println("Wrong record number!");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong action!");
                        break;
                    }

                    contacts.printInfo(recordNumber);
                    System.out.println("Enter action (edit, delete, menu):");
                    action = scanner.nextLine().trim();
                    switch (action) {
                        case "edit":
                            contacts.edit(recordNumber);
                            break;
                        case "delete":
                            contacts.remove(recordNumber);
                            break;
                        case "menu":
                            break;
                        default:
                            System.out.println("Wrong action!");
                    }

                    break;
                }
                case "search": {
                    if (contacts.count() == 0) {
                        System.out.println("No records!");
                        continue;
                    }

                    boolean endSearching;
                    do {
                        endSearching = true;
                        System.out.println("Enter search query:");
                        String searchQuery = scanner.nextLine();

                        ArrayList<Contact> matchingContacts =  contacts.search(searchQuery);
                        if (matchingContacts.isEmpty())
                            System.out.println("No records");
                        for (int i = 0; i < matchingContacts.size(); i++) {
                            Contact contact = matchingContacts.get(i);
                            System.out.printf("%d. %s", i + 1, contact.getName());
                            if (contact instanceof PersonContact)
                                System.out.printf(" %s", ((PersonContact) contact).getSurname());
                            System.out.println();
                        }

                        System.out.println("Enter action ([number], back, again):");
                        action = scanner.nextLine().trim();
                        if (action.equals("back"))
                            break;
                        if (action.equals("again"))
                            endSearching = false;
                    } while (!endSearching);

                    int recordNumber;
                    try {
                        recordNumber = Integer.parseInt(action);
                        if (recordNumber <= 0 || recordNumber > contacts.count()) {
                            System.out.println("Wrong record number!");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong record number!");
                        break;
                    }

                    contacts.printInfo(recordNumber);
                    System.out.println("Enter action (edit, delete, menu):");
                    action = scanner.nextLine().trim();
                    switch (action) {
                        case "edit":
                            contacts.edit(recordNumber);
                            break;
                        case "delete":
                            contacts.remove(recordNumber);
                            break;
                        case "menu":
                            break;
                        default:
                            System.out.println("Wrong action!");
                    }

                    break;
                }
                case "exit":
                    break;
                default:
                    System.out.println("Wrong action!");
            }
            System.out.println();
        } while (!action.equals("exit"));

        if (args.length == 2) try {
            SerializationUtils.serialize(contacts, args[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
