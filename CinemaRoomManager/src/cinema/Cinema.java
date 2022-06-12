package cinema;

import java.util.Scanner;

public class Cinema {
    static int rows;
    static int seatsInRow;
    static int countOfSeats;
    static int totalIncome;
    static boolean[][] grid;
    static Scanner scanner;

    static int purchasedTickets = 0;
    static int currectIncome = 0;
    static final int seatsInSmallCinema = 60;
    static final int highPrice = 10;
    static final int lowPrice = 8;

    public static void main(String[] args) {

        scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        seatsInRow = scanner.nextInt();

        countOfSeats = rows * seatsInRow;

        grid = new boolean[rows][seatsInRow];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < seatsInRow; j++){
                grid[i][j] = true;
            }
        }

        totalIncome = calcTotalIncome();

        int choice;
        do {
            printMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    printSeats();
                    break;
                case 2:
                    buyTicket();
                    break;
                case 3:
                    printStatistics();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Wrong input!");
                    break;
            }
        } while (choice != 0);
    }

    static void printStatistics() {
        System.out.printf("Number of purchased tickets: %d\n", purchasedTickets);
        System.out.printf("Percentage: %.2f%%\n", (float)(purchasedTickets * 100) / countOfSeats);
        System.out.printf("Current income: $%d\n", currectIncome);
        System.out.printf("Total income: $%d\n\n", totalIncome);
    }

    static void printMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    static void printSeats() {
        if (grid.length == 0)
            return;

        System.out.printf("Cinema:\n  ");
        for (int i = 1; i <= grid[0].length; i++) {
            System.out.printf("%d ", i);
        }
        System.out.println();

        for (int i = 0; i < grid.length; i++){
            System.out.printf("%d ", i + 1);

            for (int j = 0; j < grid[0].length; j++){
                if (grid[i][j])
                    System.out.print("S ");
                else
                    System.out.print("B ");
            }

            System.out.println();
        }
    }

    static void buyTicket() {
        boolean successRead = false;
        int row, seatInRow;
        do {
            System.out.println("Enter a row number:");
            row = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            seatInRow = scanner.nextInt();

            if (row <= 0 || row > rows || seatInRow <= 0 || seatInRow > seatsInRow)
                System.out.println("Wrong input!\n");
            else if (!grid[row - 1][seatInRow - 1])
                System.out.println("That ticket has already been purchased!\n");
            else
                successRead = true;
        } while (!successRead);

        grid[row - 1][seatInRow - 1] = false;
        purchasedTickets++;
        int price = calcTicketPrice(row);
        currectIncome +=price ;
        System.out.printf("Ticket price: $%d\n", price);
    }

    static int calcTicketPrice(int row) {
        if (countOfSeats <= seatsInSmallCinema || row <= rows / 2)
            return highPrice;
        return lowPrice;
    }

    static int calcTotalIncome() {
        if (countOfSeats <= seatsInSmallCinema) {
            return countOfSeats * highPrice;
        }

        return ((rows / 2) * highPrice + (rows - rows / 2) * lowPrice) * seatsInRow;
    }
}
