import java.net.StandardSocketOptions;
import java.sql.*;
import java.util.Scanner;

public class Opgave_6a {

    public static void main(String[] args) {
        try {
            Connection connection;
            Scanner scanner = new Scanner(System.in);
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLExpress:1433;database=AarhusBryghus_DB;user=sa;password=" + System.getenv("DB_PASSWORD"));

            System.out.println("Indtast produktnavn");
            String productName = scanner.nextLine();

            System.out.println("Intast antal på lager");
            int stock = scanner.nextInt();

            System.out.println("Intast minimum antal, der skal være på lager:");
            int minimumStock = scanner.nextInt();

            System.out.println("Her er der et overblik over produktkategorier:");
            Statement stmt = connection.createStatement();

            ResultSet result = stmt.executeQuery("select *  from ProductGroup");
            while (result.next()) {
                System.out.println("Produktgruppe: " + result.getString(1) + ", produktgruppeId: " + result.getInt(2));
            }

            System.out.println("\nIndtast produktgruppeID for at tilknytte produktet til den pågældende produktgruppe");
            int productGroupId = scanner.nextInt();


            String createProduct = "insert into Product values(?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(createProduct);
            statement.setString(1, productName);
            statement.setInt(2, stock);
            statement.setInt(3, minimumStock);
            statement.setInt(4, productGroupId);
            int rowsAffected = statement.executeUpdate();

            System.out.println("Rows affected: " + rowsAffected);

            connection.close();
            scanner.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
