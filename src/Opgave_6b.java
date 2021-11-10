import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Opgave_6b {

    public static void main(String[] args) {

        // fix denne, så den fungere
        try{

            Connection connection;
            Scanner scanner = new Scanner(System.in);
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLExpress:1433;database=AarhusBryghus_DB;user=sa;password=" + System.getenv("DB_PASSWORD"));

            System.out.println("Indtast det produkt du vil se det samlede salg i DKK");

            String productName = scanner.nextLine();
            System.out.println("Indtast dato");
            LocalDate date = LocalDate.parse(scanner.nextLine());



            Statement statement = connection.createStatement();
           ResultSet result = statement.executeQuery("exec getTotalSaleInDKKForASpeceficProduct " + productName + ", " + date);

           while(result.next()){
               System.out.println("Produkt: " + productName + "\nSamlet salg i DKK: " + result.getString(1));
           }

        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
