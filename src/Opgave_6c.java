import java.sql.ResultSet;
import java.sql.SQLException;

public class Opgave_6c {


	public static void main(String[] args) {

		try {
			int saleId = 0;
			Integer customPrice = null;
			System.out.println("Oversigt over salg: Indtast det salgsID, som du vil oprette en salgslinjen på");
			ResultSet sales = Repository.getAllSales();
			if (sales != null) {
				while (sales.next()) {
					System.out.println("dato: " + sales.getString(3) + ", salgsid: " + sales.getString(4));
				}
				saleId = ReadUtil.readInt();
			}


			System.out.println("Indtast antal");
			int amount = ReadUtil.readInt();

			System.out.println("Vil du angive en selvangivet pris?, Angiv venligst ja eller nej");
			String reply = ReadUtil.readNext();

			if (reply.equalsIgnoreCase("ja")) {
				System.out.println("Indtast pris");
				customPrice = ReadUtil.readInt();
			}

			System.out.println("Her er mulige priser, indtast productPriceId for at vælge en pris:");
			ResultSet prices = Repository.getAllProductPrices();
			if (prices != null) {
				while (prices.next()) {
					System.out.println("ProductPrideId: " + prices.getInt("productPriceId") + ", produkt: " + prices.getString("name") + ", pris: " + prices.getInt("price") + ", rabat:  " + prices.getInt("discountPercent"));
				}
			}
			int productPriceId = ReadUtil.readInt();

			Integer rowsAffected;
			if (customPrice != null) {
				rowsAffected = Repository.createSalesLineWithCustomPrice(amount, productPriceId, customPrice, saleId);
			} else {
				rowsAffected = Repository.createSalesLine(amount, productPriceId, saleId);
			}

			System.out.println("Antal salgslinjer oprettet: " + rowsAffected);

			Opgave_6c.checkStock(productPriceId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void checkStock(int productPriceId) {
		try {
			ResultSet resultsetCheckStock = Repository.checkStock(productPriceId);
			if (resultsetCheckStock != null) {
				while (resultsetCheckStock.next()) {
					if (resultsetCheckStock.getInt(1) < resultsetCheckStock.getInt(2)) {
						ResultSet productStock = Repository.getProductStock(productPriceId);
						if (productStock != null) {
							productStock.next();
							System.out.println("Der er mindre end minimum antal på produktet. Minimum er: " + productStock.getString(2) + ", Nuværende antal er: " + productStock.getString(1));
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
