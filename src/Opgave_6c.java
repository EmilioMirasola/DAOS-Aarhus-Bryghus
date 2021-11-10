import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Scanner;

public class Opgave_6c {


    public static void main(String[] args) {
  // skal en salgslinje sættes på et allerede oprettet salg? - eller skal der oprettes et ny salg?
        // samt skal der tjekkes for om stock er mindre end mininmumStock og så gives en meddeelse.
        try {
            int price = 0;
            Connection connection;
            Scanner scanner = new Scanner(System.in);
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLExpress:1433;database=AarhusBryghus_DB;user=sa;password=" + System.getenv("DB_PASSWORD"));
            Statement statement = connection.createStatement();

            System.out.println("Indtast antal");
            int antal = scanner.nextInt();

            System.out.println("Vil du angive en selvangivet pris?, Angiv venligst ja eller nej");
            String reply = scanner.next();

            if (reply.equalsIgnoreCase("ja")) {
                System.out.println("Indtast pris");
                price = scanner.nextInt();
            }

            System.out.println("Her er en række priser, indtast productPriceId for at vælge en pris:");
            Statement productPrice = connection.createStatement();
            productPrice.executeQuery("select price, discountPercent, productPriceId from ProductPrice");
            ResultSet rs = productPrice.getResultSet();
            while (rs.next()) {
                System.out.println(rs.getInt("productPriceId") + " " + rs.getInt("price") + " " + rs.getInt("discountPercent"));
            }
            int productPriceId = scanner.nextInt();

            String createSalesLine = null;

            if (price > 0) {
                createSalesLine = "insert into SalesLine values(?,?,?,2)";
            } else {
                createSalesLine = "insert into SalesLine values(?,null,?,2)";
            }

            PreparedStatement preparedStatement = connection.prepareStatement(createSalesLine);

            if (price > 0) {
                preparedStatement.setInt(1, antal);
                preparedStatement.setFloat(2, price);
                preparedStatement.setInt(3, productPriceId);
            } else {
                preparedStatement.setInt(1, antal);
                preparedStatement.setInt(2, productPriceId);

            }

            int rowsaffected = preparedStatement.executeUpdate();
            System.out.println("Rows afftected: " + rowsaffected);


            Statement statement2 = connection.createStatement();
            connection.close();
            scanner.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
