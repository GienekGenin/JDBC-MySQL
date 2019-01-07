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
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Books (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, img BLOB, PRIMARY KEY (id))");

            try {
                BufferedImage image = ImageIO.read(new File("D:\\photos\\rr.jpg"));
                Blob blob = con.createBlob();
                try (OutputStream outputStream = blob.setBinaryStream(1)){
                    ImageIO.write(image,"jpg", outputStream);
                }
                PreparedStatement statement1 = con.prepareStatement("INSERT INTO Books (NAME , img) VALUES (?,?)");
                statement1.setString(1,"test");
                statement1.setBlob(2, blob);
                statement1.execute();

                ResultSet set = statement.executeQuery("SELECT * FROM Books");
                while (set.next()){
                    Blob blob1 = set.getBlob("img");
                    BufferedImage image1 = ImageIO.read(blob1.getBinaryStream());
                    File outputFile = new File("saved.jpg");
                    ImageIO.write(image1, "jpg", outputFile);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("DB connection success");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
