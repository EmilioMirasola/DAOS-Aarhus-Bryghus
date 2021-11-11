import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Opgave_6b {

	public static void main(String[] args) {

		// fix denne, så den fungere
		try {
			Connection connection;
			Scanner scanner = new Scanner(System.in);
			connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLExpress:1433;database=AarhusBryghus_DB;user=sa;password=" + System.getenv("DB_PASSWORD"));

			printProductGroups(connection);

			System.out.println("Indtast ID på den produktgruppe, du vil se produkter for");
			int productGroupId = ReadUtil.readInt();

			printProducts(connection, productGroupId);

			System.out.println("Indtast ID på det produkt du vil se det samlede salg for i DKK");
			int productId = ReadUtil.readInt();

			System.out.println("Indtast dato");
			String date = scanner.nextLine();

			Statement statement = connection.createStatement();
			ResultSet totalSale = statement.executeQuery("exec getTotalSaleInDKKForASpeceficProduct " + productId + ", " + "'" + date + "'");

			while (totalSale.next()) {
				System.out.println("\nSamlet salg i DKK: " + totalSale.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void printProductGroups(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet result = stmt.executeQuery("select *  from ProductGroup");
		System.out.println("Produktgrupper:");
		while (result.next()) {
			System.out.println(result.getString(1) + ", produktgruppeID: " + result.getInt(2));
		}
		System.out.println();
	}

	private static void printProducts(Connection connection, int id) throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet result = stmt.executeQuery("select name, productId  from Product P where P.productGroupId =" + id);

		System.out.println();
		System.out.println("Produkter:");
		while (result.next()) {
			System.out.println(result.getString(1) + ", produktID: " + result.getInt(2));
		}
		System.out.println();
	}
}
