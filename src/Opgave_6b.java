import java.sql.ResultSet;
import java.sql.SQLException;

public class Opgave_6b {

    public static void main(String[] args) {

        try {
            Repository.printProductGroups();

            System.out.println("Indtast ID på den produktgruppe, du vil se produkter for");
            int productGroupId = ReadUtil.readInt();

            Repository.printProducts(productGroupId);

            System.out.println("Indtast ID på det produkt du vil se det samlede salg for i DKK");
            int productId = ReadUtil.readInt();
            ReadUtil.readLine();

            System.out.println("Indtast dato (yyyy-mm-dd)");
            String date = ReadUtil.readLine();

            ResultSet totalSale = Repository.getTotalSaleInDKKForASpeceficProduct(productId, date);
            if (totalSale != null) {
                    while (totalSale.next()) {
						if(totalSale.getString(1) == null) {
							System.err.println("Denne er null, fordi der ikke er nogle salg på denne dato. Prøv 2021-11-09 :)");
                        }
                        System.out.println("\nSamlet salg i DKK: " + totalSale.getString(1));
				}
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}