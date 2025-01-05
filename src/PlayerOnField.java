import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class PlayerOnField {

    private static final String url = "jdbc:mysql://localhost:3306/project";
    private static final String username = "root";
    private static final String password = "zeeshan";
    public static void main()  {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. Player are on the field, update the score");
            System.out.println("2. To check which player has maximum score or whatever");
            System.out.println("0. exit ");
            System.out.print("Please choose the option: ");
            int num = sc.nextInt();
            System.out.println();

            switch (num) {
                case 1: playerField();
                    break;
                case 2:
                    check_score();
                    break;
                case 0: System.exit(0);
                return;
                default:
                    System.out.println("Choose the option again");
                    break;
            }
        }
    }
    public static void playerField(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            UpdateScore obj = new UpdateScore(connection);
            CheckScore max = new CheckScore(connection);
            while (true) {
                System.out.print("1. Which player is batting: ");
                String player = sc.nextLine();
                boolean flag = obj.check_player(player);
                if (!flag) {
                    playerField();
                }
                System.out.println("What he played ");
                System.out.println("0. Run = 0");
                System.out.println("1. Run = 1");
                System.out.println("2. Run = 2");
                System.out.println("3. Run = 3");
                System.out.println("4. Four");
                System.out.println("5. Out");
                System.out.println("6. Six");
                System.out.println("7. Scorecard");
                System.out.println("8. Back to the Main Menu");
                System.out.print("Please choose the option: ");
                int choice = sc.nextInt();
                System.out.println();
                sc.nextLine();
                switch (choice) {
                    case 0, 1, 2, 3 -> obj.run(player, choice);
                    case 4, 6 -> obj.run_four_six(player, choice);
                    case 5 -> {
                        System.out.println("Enter the name of player who did take the wicket");
//                        sc.nextLine();
                        String wicket = sc.nextLine();
                        obj.out(player, wicket);
                    }
                    case 7 -> max.scorecard();
                    case 8 -> {
                        System.out.println("You came out of this function");
                        return;
                    }
                    default -> System.out.println("Choose the option again: ");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void check_score() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            CheckScore mx = new CheckScore(connection);
            while (true){
                System.out.println("What do you want to check ");
                System.out.println("1. Scorecard");
                System.out.println("2. Run rate");
                System.out.println("3. Which player has the highest Strike rate");
                System.out.println("4. Which player has the most runs");
                System.out.println("5. Which player hit the most fours");
                System.out.println("6. Which player hit the most sixes");
                System.out.println("7. To see the overall performance of a particular player");
                System.out.println("8. Back to Main Menu");
                System.out.print("Please choose the option: ");
                int choice = sc.nextInt();
                System.out.println();
                switch (choice){
                    case 1: mx.scorecard();
                        break;
                    case 2: mx.run_rate();
                        break;
                    case 3: mx.max("strike_rate");
                        break;
                    case 4: mx.max("run");
                        break;
                    case 5: mx.max("fours");
                        break;
                    case 6: mx.max("sixes");
                        break;
                    case 7:
                        System.out.println("Name of the player");
                        sc.nextLine();
                        String name = sc.nextLine();
                        mx.particular_player(name);
                        break;
                    case 8:
                        System.out.println("you came out of this function\n");
                        return;
                    default:
                        System.out.println("Choose the option again");

                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
