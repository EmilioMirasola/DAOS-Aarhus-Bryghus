import java.sql.*;

public class Opgave_6a {

	public static void main(String[] args) {

		System.out.println("Indtast produktnavn");
		String productName = ReadUtil.readLine();

		System.out.println("Indtast lagerantal");
		int stock = ReadUtil.readInt();

		System.out.println("Intast minimum antal, der skal være på lager:");
		int minimumStock = ReadUtil.readInt();

		System.out.println("Her er der et overblik over produktkategorier:");

		try {
			ResultSet result = Repository.getAllProductGroups();
			if (result != null) {
				while (result.next()) {
					System.out.println("Produktgruppe: " + result.getString(1) + ", produktgruppeId: " + result.getInt(2));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("\nIndtast produktgruppeID for at tilknytte produktet til den pågældende produktgruppe");
		int productGroupId = ReadUtil.readInt();

		try {
			Integer rowsAffected = Repository.insertProduct(productName, stock, minimumStock, productGroupId);
			System.out.println("Antal produkter oprettet: " + rowsAffected);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
