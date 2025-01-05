import java.sql.*;

public class CheckScore {
    private final Connection connection;
//    private Scanner sc;

    public CheckScore(Connection connection) {
        this.connection = connection;
    }

    public void max(String column) {
        try {
            Statement stm = connection.createStatement();
            String str = "select player, " + column + " from india where " + column + " = (select MAX(" + column + ") from india);";
            ResultSet rs = stm.executeQuery(str);
            System.out.println("+---------------------+-------------+");
            System.out.println("| Player Name         | " + column + "       |");
            System.out.println("+---------------------+-------------+");
            while (rs.next()) {
                if(column.equals("strike_rate")){
                    float num = rs.getFloat(column);
                    String pl = rs.getString("player");
                    System.out.printf("| %-19s | %-11s |\n", pl, num);
                    System.out.println("+---------------------+-------------+");
                }
                else {
                    int num = rs.getInt(column);
                    String pl = rs.getString("player");
                    System.out.printf("| %-19s | %-11s |\n", pl, num);
                    System.out.println("+---------------------+-------------+");
                }
            }
        }catch(Exception e){
            System.out.println("Error:"+e);
        }
    }
    public void run_rate() {
        try {
            Statement stm = connection.createStatement();
            String str1 = "select sum(run) from india;";
            ResultSet rs = stm.executeQuery(str1);
            float run = 0;
            if (rs.next()) {
                run += rs.getInt(1);
            }
            String str2 = "select sum(ball) from bowler_nz;";
            rs = stm.executeQuery(str2);
            float ball = 0;
            if (rs.next()) {
                ball += rs.getInt(1);
            }
            float run_rate = run/(ball/6);
            System.out.println("|----------------------------|");
            System.out.println("Run rate = "+run_rate);
            System.out.println("Total overs played = "+ball/6);
            System.out.println("|----------------------------|");
            System.out.println();
        }catch(Exception e){
            System.out.println("Error:"+e);
        }
    }
    public void particular_player(String name) {
        try {
            Statement stm = connection.createStatement();
            String str = "select * from india where player = '" + name + "';";
            ResultSet rs = stm.executeQuery(str);
            System.out.println("+---------------------+-------------------------------+---------+----------+-----------+-----------+--------------+");
            System.out.println("| Player Name         | Wicket By                     | Run     | Ball     | Fours     | Sixes     | Strike Rate  |");
            System.out.println("+---------------------+-------------------------------+---------+----------+-----------+-----------+--------------+");
            while (rs.next()) {
                String pl = rs.getString("player");
                String wkt = rs.getString("wicket_by");
                int rn = rs.getInt("run");
                int bl = rs.getInt("ball");
                int fr = rs.getInt("fours");
                int sx = rs.getInt("sixes");
                float stk = rs.getInt("strike_rate");

                System.out.printf("| %-19s | %-29s | %-8s | %-8s | %-8s | %-8s | %-12s |\n", pl, wkt, rn, bl, fr, sx, stk);
                System.out.println("+---------------------+-------------------------------+---------+----------+-----------+-----------+--------------+");

            }
        }
            catch(Exception e){
                System.out.println("Error:" + e);
            }
    }
        public void scorecard(){
        try{

            Statement stm=connection.createStatement();
            String str="select * from india;";
            ResultSet rs=stm.executeQuery(str);
            System.out.println("+---------------------+-------------------------------+---------+----------+-----------+-----------+--------------+");
            System.out.println("|                                                      Batter Table                                               |");
            System.out.println("+---------------------+-------------------------------+---------+----------+-----------+-----------+--------------+");
            System.out.println("| Player Name         | Wicket By                     | Run     | Ball     | Fours     | Sixes     | Strike Rate  |");
            System.out.println("+---------------------+-------------------------------+---------+----------+-----------+-----------+--------------+");
            while(rs.next())
            {
                String pl=rs.getString("player");
                String wkt=rs.getString("wicket_by");
                int rn = rs.getInt("run");
                int bl = rs.getInt("ball");
                int fr = rs.getInt("fours");
                int sx= rs.getInt("sixes");
                float stk = rs.getInt("strike_rate");

                System.out.printf("| %-19s | %-29s | %-8s | %-8s | %-8s | %-9s | %-12s |\n", pl, wkt, rn, bl, fr, sx, stk);
                System.out.println("+---------------------+-------------------------------+---------+----------+-----------+-----------+--------------+");
            }
            String str1 = "select sum(run) from india;";
            rs = stm.executeQuery(str1);
            float run = 0;
            if (rs.next()) {
                run += rs.getInt(1);
            }
            String str2 = "select sum(ball) from bowler_nz;";
            rs = stm.executeQuery(str2);
            float ball = 0;
            if (rs.next()) {
                ball += rs.getInt(1);
            }
            System.out.println("|---------------------|");
            System.out.println("Total Runs = "+run);
            System.out.println("Total Overs = "+ball/6);
            System.out.println("|---------------------|");
            System.out.println();

            String str3="select * from bowler_nz;";
            rs=stm.executeQuery(str3);
            System.out.println("+---------------------+-----------+---------+----------+---------------+");
            System.out.println("|                             Bowler Table                             |");
            System.out.println("+---------------------+-----------+---------+----------+---------------+");
            System.out.println("| Bowler Name         | Overs     | Run     | Ball     | Economy Rate  |");
            System.out.println("+---------------------+-----------+---------+----------+---------------+");
            while(rs.next())
            {
                String pl=rs.getString("bowler");
                int overs = rs.getInt("overs");
                int rn = rs.getInt("run");
                int bl = rs.getInt("ball");
                float eco= rs.getFloat("eco");
                System.out.printf("|%-20s | %-9s | %-7s | %-8s | %-13s |\n", pl, overs, rn, bl, eco);
                System.out.println("+---------------------+-----------+---------+----------+---------------+");
            }
            System.out.println();
        }catch(Exception e){
            System.out.println("Error:"+e);
        }
    }


}