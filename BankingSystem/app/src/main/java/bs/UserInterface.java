package bs;

public interface UserInterface {
    static void printMenu() {
        System.out.println("\n1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    static void printAccountActions() {
        System.out.println("\n1. Balance");
        System.out.println("2. Log out");
        System.out.println("0. Exit");
    }
}
