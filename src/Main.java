import java.sql.*;

public class Main {

    public static void main(String[] args) {
        Main.createConnection();
    }

    private static void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "admin");

            String param = "Pasha";
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM mydb.users where name = ?");
            preparedStatement.setString(1, param);
            ResultSet set  = preparedStatement.executeQuery();

            while (set.next()){
                System.out.println(set.getString("name"));
            }

            System.out.println("DB connection success");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
