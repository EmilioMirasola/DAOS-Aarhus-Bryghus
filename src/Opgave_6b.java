import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.MissingResourceException;

public class Opgave_6b {

	public static void main(String[] args) {

		try {
			Repository.printProductGroups();

			System.out.println("Indtast ID på den produktgruppe, du vil se produkter for");
			int productGroupId = ReadUtil.readInt();

			ResultSet results = Repository.getProductsByProductGroup(productGroupId);
			System.out.println();
			if (results != null) {
				int rowCount = 0;
				System.out.println("Produkter:");
				while (results.next()) {
					rowCount++;
					System.out.println(results.getString(1) + ", produktID: " + results.getInt(2));
				}

				if (rowCount == 0) {
					throw new RuntimeException("Produktgruppe: " + productGroupId + " findes ikke eller har ingen produkter");
				}
			}
			System.out.println();

			System.out.println("Indtast ID på det produkt du vil se det samlede salg for i DKK");
			int productId = ReadUtil.readInt();
			ReadUtil.readLine();

			System.out.println("Indtast dato (yyyy-mm-dd)");
			String date = ReadUtil.readLine();

			ResultSet totalSale = Repository.getTotalSaleInDKKForASpeceficProduct(productId, date);
			if (totalSale != null) {
				while (totalSale.next()) {
					if (totalSale.getString(1) == null) {
						System.err.println("Denne er null, fordi der ikke er nogle salg på denne dato, eller produktet ikke findes. Prøv gruppe 3, produkt 8, 2021-11-09 :)");
					}
					System.out.println("Samlet salg i DKK: " + totalSale.getString(1));
				}
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
}