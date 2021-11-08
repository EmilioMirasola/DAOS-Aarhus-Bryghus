import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connection_Test {

    public static void main(String[] args) {
        try {
            Connection connection;

            connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLExpress:1433;database=AarhusBryghus_DB;user=sa;password=" + System.getenv("DB_PASSWORD"));
            Statement stmt = connection.createStatement();

            ResultSet result =stmt.executeQuery("select *  from Employee");
            while(result.next()){
                System.out.println(result.getString(1));
     }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
