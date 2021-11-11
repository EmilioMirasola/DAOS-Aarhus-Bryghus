import javax.swing.text.html.Option;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.Optional;

public class Repository {

	public static ResultSet getAllProductGroups() throws SQLException {
		Optional<Connection> connection = getConnection();
		if (connection.isPresent()) {
			Statement stmt = connection.get().createStatement();
			return stmt.executeQuery("select *  from ProductGroup");
		} else {
			return null;
		}
	}

	public static Integer insertProduct(String productName, int stock, int minimumStock, int productGroupId) throws SQLException {
		Optional<Connection> connection = getConnection();
		if (connection.isPresent()) {
			String createProduct = "insert into Product values(?,?,?,?)";
			PreparedStatement statement = connection.get().prepareStatement(createProduct);
			statement.setString(1, productName);
			statement.setInt(2, stock);
			statement.setInt(3, minimumStock);
			statement.setInt(4, productGroupId);
			try {
				return statement.executeUpdate();
			} catch (SQLException e) {
				System.err.println("FEJL: " + e.getMessage());
			}
		}
		return null;
	}

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

	public static ResultSet getProductsByProductGroup(int id) throws SQLException {
		Optional<Connection> connection = getConnection();
		if (connection.isPresent()) {
			Statement stmt = connection.get().createStatement();
			return stmt.executeQuery("select name, productId  from Product P where P.productGroupId =" + id);
		}
		return null;
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
				return statement.executeQuery("SELECT * FROM Sale");
			} catch (SQLException e) {
				System.err.println("FEJL: " + e.getMessage());
			}
		}

		return null;
	}

	public static ResultSet getAllProductPrices() {
		Optional<Connection> connection = getConnection();

		if (connection.isPresent()) {
			try {
				Statement statement = connection.get().createStatement();

				return statement.executeQuery("select name, price, discountPercent, productPriceId from ProductPrice join Product on Product.productId = ProductPrice.productId");
			} catch (SQLException e) {
				System.err.println("FEJL: " + e.getMessage());
			}


		}
		return null;
	}

	public static Integer createSalesLine(int amount, int productPriceId, int saleId) {
		Optional<Connection> connection = getConnection();

		if (connection.isPresent()) {
			String createSalesLine = "insert into SalesLine values(?,null,?,?)";

			try {
				PreparedStatement statement = connection.get().prepareStatement(createSalesLine);
				statement.setInt(1, amount);
				statement.setInt(2, productPriceId);
				statement.setInt(3, saleId);

				return statement.executeUpdate();
			} catch (SQLException e) {
				System.err.println("FEJL: " + e.getMessage());
				return null;
			}
		}
		return null;
	}

	public static Integer createSalesLineWithCustomPrice(int amount, int productPriceId, float customPrice, int saleId) {
		Optional<Connection> connection = getConnection();

		if (connection.isPresent()) {
			String createSalesLine = "insert into SalesLine values(?,?,?,?)";
			try {
				PreparedStatement statement = connection.get().prepareStatement(createSalesLine);
				statement.setInt(1, amount);
				statement.setFloat(2, customPrice);
				statement.setInt(3, productPriceId);
				statement.setInt(4, saleId);

				return statement.executeUpdate();
			} catch (SQLException e) {
				System.err.println("FEJL: " + e.getMessage());
				return null;
			}
		}
		return null;
	}

	public static ResultSet checkStock(int productPriceId) {
		Optional<Connection> connection = getConnection();

		if (connection.isPresent()) {
			try {
				Statement statement = connection.get().createStatement();
				return statement.executeQuery("SELECT * FROM stock_check(" + productPriceId + ")");
			} catch (SQLException e) {
				System.err.println("FEJL: " + e.getMessage());
			}
		}
		return null;
	}


	public static ResultSet getProductStock(int productPriceId) {
		Optional<Connection> connection = getConnection();
		if (connection.isPresent()) {
			try {
				Statement statement = connection.get().createStatement();
				return statement.executeQuery("SELECT distinct stock, minimumStock FROM Product join ProductPrice on Product.productId = ProductPrice.productId where productPriceId=" + productPriceId);
			} catch (SQLException e) {
				System.err.println("FEJL: " + e.getMessage());
			}
		}
		return null;
	}
}