import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Scanner;

public class Opgave_6c {


	public static void main(String[] args) {

		try {
			int saleId = 0;
			int customPrice = 0;
			System.out.println("Vil du tilknytte en salgslinje til et eksisterende salg? indtast ja eller nej");
			String answer = ReadUtil.readNext();
			if (answer.equalsIgnoreCase("ja")) {
				System.out.println("Oversigt over salg: Indtast et salgsID som du vil tilknytte salgslinjen på");
				ResultSet sales = Repository.getAllSales();
				if (sales != null) {
					while (sales.next()) {
						System.out.println("dato: " + sales.getString(3) + ", salgsid: " + sales.getString(4));
					}
					saleId = ReadUtil.readInt();
				}

			}


			System.out.println("Indtast antal");
			int amount = ReadUtil.readInt();

			System.out.println("Vil du angive en selvangivet pris?, Angiv venligst ja eller nej");
			String reply = ReadUtil.readNext();

			if (reply.equalsIgnoreCase("ja")) {
				System.out.println("Indtast pris");
				customPrice = ReadUtil.readInt();
			}

			System.out.println("Her er en række priser, indtast productPriceId for at vælge en pris:");
			ResultSet getAllPrices = Repository.getAllProductPrices();
			while (getAllPrices.next()) {
				System.out.println("ProductPrideId: " + getAllPrices.getInt("productPriceId") + ", pris: " + getAllPrices.getInt("price") + ", rabat:  " + getAllPrices.getInt("discountPercent"));
			}
			int productPriceId = ReadUtil.readInt();

			String createSalesLine = null;

			if (customPrice > 0 && answer.equalsIgnoreCase("ja")) {
				createSalesLine = "insert into SalesLine values(?,?,?,?)";
			} else if (customPrice > 0) {
				createSalesLine = "insert into SalesLine values(?,?,?,2)";
			} else {
				createSalesLine = "insert into SalesLine values(?,null,?,2)";
			}

			PreparedStatement preparedStatement = connection.prepareStatement(createSalesLine);

			if (customPrice > 0 && answer.equalsIgnoreCase("ja")) {
				preparedStatement.setInt(1, amount);
				preparedStatement.setFloat(2, customPrice);
				preparedStatement.setInt(3, productPriceId);
				preparedStatement.setInt(4, saleId);
			} else if (customPrice > 0) {
				Repository.createSaleWithCustomPrice(amount,customPrice, productPriceId );
			} else {
				Repository.createSale(amount, productPriceId);
			}


			Opgave_6c.checkStock(connection, productPriceId);


		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void checkStock(Connection connection, int productPriceId) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery("SELECT * FROM stock_check(" + productPriceId + ")");
			while (resultset.next()) {
				if (resultset.getInt(1) < resultset.getInt(2)) {
					System.out.println("Der er mindre end minimum antal på produktet:");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
