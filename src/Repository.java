import java.sql.*;
import java.util.Optional;

public class Repository {


	public static void printProductGroups() throws SQLException {
		Optional<Connection> connection = getConnection();
		if (connection.isPresent()) {
			Statement stmt = connection.get().createStatement();
			ResultSet result = stmt.executeQuery("select *  from ProductGroup");
			System.out.println("Produktgrupper:");
			while (result.next()) {
				System.out.println(result.getString(1) + ", produktgruppeID: " + result.getInt(2));
			}
		}
		System.out.println();
	}

	public static void printProducts(int id) throws SQLException {
		Optional<Connection> connection = getConnection();
		if (connection.isPresent()) {
			Statement stmt = connection.get().createStatement();
			ResultSet result = stmt.executeQuery("select name, productId  from Product P where P.productGroupId =" + id);

			System.out.println();
			System.out.println("Produkter:");
			while (result.next()) {
				System.out.println(result.getString(1) + ", produktID: " + result.getInt(2));
			}
			System.out.println();
		}
	}

	public static ResultSet getTotalSaleInDKKForASpeceficProduct(int productId, String date) throws SQLException {
		Optional<Connection> connection = getConnection();
		if (connection.isPresent()) {
			Statement statement = connection.get().createStatement();
			return statement.executeQuery("exec getTotalSaleInDKKForASpeceficProduct " + productId + ", " + "'" + date + "'");
		}
		return null;
	}

	private static Optional<Connection> getConnection() {
		try {
			return Optional.of(DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLExpress:1433;database=AarhusBryghus_DB;user=sa;password=" + System.getenv("DB_PASSWORD")));
		} catch (SQLException e) {
			return Optional.empty();
		}
	}

	public static ResultSet getAllSales() {
		Optional<Connection> connection = getConnection();

		if (connection.isPresent()) {
			try {
				Statement statement = connection.get().createStatement();
				ResultSet resultset = statement.executeQuery("SELECT * FROM Sale");
				return resultset;
			} catch (SQLException e) {
				e.printStackTrace();
			}


		}

		return null;
	}

	public static ResultSet getAllProductPrices() {
		Optional<Connection> connection = getConnection();

		if (connection.isPresent()) {
			try {
				Statement statement = connection.get().createStatement();
				ResultSet resultset = statement.executeQuery("select price, discountPercent, productPriceId from ProductPrice");

				return resultset;
			} catch (SQLException e) {
				e.printStackTrace();
			}


		}
		return null;
	}

	public static Integer createSale(int amount, int productPriceId) {
		Optional<Connection> connection = getConnection();

		if (connection.isPresent()) {
			String createSalesLine = "insert into SalesLine values(?,null,?,2)";

			try {
				PreparedStatement statement = connection.get().prepareStatement(createSalesLine);
				statement.setInt(1, amount);
				statement.setInt(2, productPriceId);

				return statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}



	public static Integer createSaleWithCustomPrice(int amount, int productPriceId, float customPrice) {
		Optional<Connection> connection = getConnection();

		if (connection.isPresent()) {
			String createSalesLine = "insert into SalesLine values(?,?,?,2)";

			try {
				PreparedStatement statement = connection.get().prepareStatement(createSalesLine);
				statement.setInt(1, amount);
				statement.setFloat(2, customPrice);
				statement.setInt(3, productPriceId);

				return statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public static Integer createSaleWithCustomPriceAndNewSale(int amount, int productPriceId, float customPrice) {
		Optional<Connection> connection = getConnection();

		if (connection.isPresent()) {
			String createSalesLine = "insert into SalesLine values(?,?,?,2)";

			try {
				PreparedStatement statement = connection.get().prepareStatement(createSalesLine);
				statement.setInt(1, amount);
				statement.setFloat(2, customPrice);
				statement.setInt(3, productPriceId);

				return statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}


}