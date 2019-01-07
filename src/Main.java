import java.sql.*;

public class Main {

    public static void main(String[] args) {
        Main.createConnection();
    }

    private static void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "admin");

            Statement statement = con.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM mydb.users");
            while (set.next()){
                String name = set.getString("name");
                System.out.println(name);
            }

            System.out.println("DB connection success");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
