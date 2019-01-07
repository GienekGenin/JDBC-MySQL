import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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
            statement.execute("drop table if exists Books");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Books (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, PRIMARY KEY (id))");
            statement.executeUpdate("INSERT INTO Books  (NAME) VALUE ('C++')");
            statement.executeUpdate("INSERT INTO Books  (NAME) VALUE ('Java')");

            CallableStatement callableStatement = con.prepareCall("{call BookCount(?)}");
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.execute();
            System.out.println(callableStatement.getInt(1));

            System.out.println("DB connection success");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
