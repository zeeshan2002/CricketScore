import java.sql.*;
import java.util.Random;

public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/db_name";
    private static final String username = "root";
    private static final String password = "root";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            UpdateScore obj = new UpdateScore(connection);
            boolean flag = obj.check_player();
            if (flag) {
                System.out.println("\nMatch is already started, please choose the option.\n");
                PlayerOnField.main();
                return;
            }
            System.out.println("\n***************************India vs New Zealand Semi Final***************************\n");
            System.out.println("This game is playing in Hyderabad Stadium.");
            System.out.println("Toss is happening");
            Random rn = new Random();
            int num = rn.nextInt(1, 3);
            if (num == 1) {
                System.out.println("India won the toss and choose batting\n");
            } else {
                System.out.println("New Zealand won the toss and choose bowling\n");
            }
            UpdateScore.start();
            System.out.println();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
