package bs;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class BankingSystem {
    private final SQLiteDataSource dataSource;
    private Connection con;
    private static final String addCardQuery = "INSERT INTO card " +
            "(number, pin) VALUES (?, ?)";
    private static final String chooseCardQuery = "SELECT balance FROM card " +
            "WHERE number = ? and pin = ?";
    private Statement createTableStatement;
    private PreparedStatement addCardStatement;
    private PreparedStatement chooseCardStatement;

    public BankingSystem() {
        this.dataSource = new SQLiteDataSource();
    }

    public void connect(String url) throws SQLException {
        this.dataSource.setUrl(url);

        try {
            con = dataSource.getConnection();

            createTableStatement = con.createStatement();
            createTableStatement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "number TEXT NOT NULL," +
                    "pin TEXT NOT NULL," +
                    "balance INTEGER DEFAULT 0)");

            addCardStatement = con.prepareStatement(addCardQuery);
            chooseCardStatement = con.prepareStatement(chooseCardQuery);
        } catch (SQLException | NullPointerException e) {
            try {
                con.close();
                createTableStatement.close();
                addCardStatement.close();
                chooseCardStatement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw e;
        }
    }

    public void closeConnection() throws SQLException {
        try {
            con.close();
            createTableStatement.close();
            addCardStatement.close();
            chooseCardStatement.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void createAccount() throws SQLException {
        CreditCard creditCard = new CreditCard();
        System.out.println("Your card has been created");

        System.out.println("Your card number:");
        System.out.println(creditCard.getNumber());
        System.out.println("Your card PIN:");
        System.out.println(creditCard.getPin());

        addCardStatement.setString(1, creditCard.getNumber().toString());
        addCardStatement.setString(2, creditCard.getPin().toString());
        addCardStatement.executeUpdate();
    }

    public boolean logIntoAccount(String cardNumber, String pin) throws SQLException {
        chooseCardStatement.setString(1, cardNumber);
        chooseCardStatement.setString(2, pin);
        if (chooseCardStatement.execute()) {
            System.out.println("\nYou have successfully logged in!");
            return true;
        }
        System.out.println("\nWrong card number or PIN!");
        return false;
    }

    public int getBalance(String cardNumber, String pin) throws SQLException {
        chooseCardStatement.setString(1, cardNumber);
        chooseCardStatement.setString(2, pin);
        ResultSet result = chooseCardStatement.executeQuery();

        return result.getInt("balance");
    }

}
