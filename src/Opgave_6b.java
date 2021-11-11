import java.sql.ResultSet;
import java.sql.SQLException;

public class Opgave_6b {

    public static void main(String[] args) {

        // fix denne, så den fungere
        try {
            Repository.printProductGroups();

            System.out.println("Indtast ID på den produktgruppe, du vil se produkter for");
            int productGroupId = ReadUtil.readInt();

            Repository.printProducts(productGroupId);

            System.out.println("Indtast ID på det produkt du vil se det samlede salg for i DKK");
            int productId = ReadUtil.readInt();

            System.out.println("Indtast dato");
            String date = ReadUtil.readLine();

            ResultSet totalSale = Repository.getTotalSaleInDKKForASpeceficProduct(productId, date);
            if (totalSale != null) {
                while (totalSale.next()) {
                    System.out.println("\nSamlet salg i DKK: " + totalSale.getString(1));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}