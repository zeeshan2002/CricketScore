import java.sql.*;
import java.util.ArrayList;

public class UpdateScore {
    private static Connection conn;
    public UpdateScore(Connection connection) {
        this.conn = connection;
    }
    public static void start(){
        try{
            ArrayList<String> list = new ArrayList<>();
            ArrayList<String> list1 = new ArrayList<>();
            Statement stm = conn.createStatement();
            String str = "select player from player_info where no < 3;";
            ResultSet rs = stm.executeQuery(str);
            while (rs.next()){
                String name = rs.getString("player");
                list.add(name);
            }
            for (int i = 0; i < list.size(); i++) {

                String str1 = "insert into india values('"+list.get(i)+"', 'not out', 0, 0, 0, 0, 0);";
                stm.executeUpdate(str1);
            }

            for (int i = 0; i < list.size(); i++) {
                System.out.print(list.get(i) + ", ");
            }
            System.out.println("are in the field for batting from team India against New Zealand.");

            // for comi in the field
            String str1 = "select player from player_nz where no < 2;";
            rs = stm.executeQuery(str1);
            while (rs.next()){
                String name = rs.getString("player");
                list1.add(name);
            }
            for (int i = 0; i < list1.size(); i++) {
                String str2 = "insert into bowler_nz values('"+list1.get(i)+"', 0, 0, 0, 1, 0);";
                stm.executeUpdate(str2);
            }

            for (int i = 0; i < list1.size(); i++) {
                System.out.print(list1.get(i) + " ");

            }
            System.out.println("are in the field for bowling from New Zealand team.\n");
            PlayerOnField.playerField();
        }catch(Exception e){
            System.out.println("Error:"+e);
        }
    }
    public boolean check_player(String pla){
        try{
            ArrayList<String> list = new ArrayList<>();
            Statement stm=conn.createStatement();
            String str = "select player from india where wicket_by = 'not out';";
            ResultSet rs = stm.executeQuery(str);
            while (rs.next()){
                String name = rs.getString("player");
                list.add(name);
            }
            System.out.println("Players are on the field: " + list);
            if (!list.contains(pla)) {
                System.out.println(pla + " is out or does not exist. Please enter a valid player name.");
                stm.close();
                conn.close();
                return false;
            }

        }catch(Exception e){
            System.out.println("Error:"+e);
            return false;
        }
        return true;
    }
    public boolean check_player(){ // used compile time polymorphism
        try{

            ArrayList<String> list = new ArrayList<>();
            Statement stm=conn.createStatement();
            String str = "select count(*) from india;";
            ResultSet rs = stm.executeQuery(str);
            int num = 0;
            if(rs.next()) {
                num = rs.getInt(1);
            }
            if(num==0){
                return false;
            }

        }catch(Exception e){
            System.out.println("Error:"+e);
        }
        return true;
    }

    public void run(String pla, int run){
        try{
            ArrayList<String> list = new ArrayList<>();
            Statement stm=conn.createStatement();
            String str = "select player from india where wicket_by = 'not out';";
            ResultSet rs = stm.executeQuery(str);
            while (rs.next()){
                String name = rs.getString("player");
                list.add(name);
            }
            for (int i = 0; i <= list.size(); i++) {
                if(list.get(i).equals(pla)) {
                    String str1 = ("update india set run = run + " + run + " where player = '" + pla + "';");
                    stm.executeUpdate(str1);
                    String str2 = ("update india set ball = ball + 1, strike_rate = (run/ball)*100 where player = '" + pla + "';");
                    stm.executeUpdate(str2);
                    bowler_update(run);
                    break;
                }
            }
            System.out.println("Update is successfully completed");

        }catch(Exception e){
            System.out.println("Error:"+e);
        }

    }
    public void bowler_update(int run){
    try {
        Statement stm = conn.createStatement();

        String str3 = "select count(*) from bowler_nz;";
        ResultSet rs = stm.executeQuery(str3);
        int num = 0;
        if (rs.next()) {
            num += rs.getInt(1);
        }
        String str5 = "select ball from bowler_nz where no = " + num + ";";
        rs = stm.executeQuery(str5);
        int sum = 0;
        if (rs.next()) {
            sum += rs.getInt(1);
        }
        if(num==3){
            System.out.println("Overs are completed");
            System.out.println("Next inning will start play after 30 minutes");
            return;
        }
        if (sum == 0 || sum % 6 != 0) {
            String str4 = ("update bowler_nz set run = run + " + run + ", ball = ball + 1, eco = (run/ball) where no = '" + num + "';");
            stm.executeUpdate(str4);
        } else {
            String str4 = ("update bowler_nz set overs = overs + 1 where no = '" + num + "';");
            stm.executeUpdate(str4);
            num++;
            String str7 = ("select player from player_nz where no = '" + num + "';");
            rs = stm.executeQuery(str7);
            if (rs.next()) {
                String pl = rs.getString("player");
                String str9 = ("insert into bowler_nz values('" + pl + "', 0, 0, 0, '" + num + "', 0);");
                stm.executeUpdate(str9);
                String str6 = ("update bowler_nz set run = run + " + run + ", ball = ball + 1, eco = (run/ball) where bowler = '" + num + "';");
                stm.executeUpdate(str6);
            }
        }

    }catch(Exception e){
        System.out.println("Error:"+e);
    }
    }
    public void run_four_six(String pla, int run){
        try{
            Statement stm=conn.createStatement();
            if(run==4) {
                String str1 = ("update india set run = run + " +run+ " where player = '" + pla + "';");
                stm.executeUpdate(str1);
                String str2 = ("update india set ball = ball + 1, strike_rate = (run/ball)*100 where player = '" + pla + "';");
                stm.executeUpdate(str2);
                String str3 = ("update india set fours = fours + 1 where player = '" + pla + "';");
                stm.executeUpdate(str3);
                bowler_update(run);
            }
            else{
                String str1 = ("update india set run = run + " +run+ " where player = '" + pla + "';");
                stm.executeUpdate(str1);
                String str2 = ("update india set ball = ball + 1, strike_rate = (run/ball)*100 where player = '" + pla + "';");
                stm.executeUpdate(str2);
                String str3 = ("update india set sixes = sixes + 1 where player = '" + pla + "';");
                stm.executeUpdate(str3);
                bowler_update(run);
            }
            System.out.println("Update is successfully completed");

        }catch(Exception e){
            System.out.println("Error:"+e);
        }

    }
    public void out(String pla, String wicketer){
        try{
            Statement stm=conn.createStatement();

                String str1 = ("update india set wicket_by = '"+wicketer+"' where player = '" + pla + "';");
                stm.executeUpdate(str1);
            next_player();
            System.out.println("Update is successfully completed");

        }catch(Exception e){
            System.out.println("Error:"+e);
        }

    }
    public void next_player(){
        try{
            Statement stm=conn.createStatement();
            String str="select count(*) from india;";
            ResultSet rs = stm.executeQuery(str);
            int num =1;
            if(rs.next()) {
                num += rs.getInt(1);
            }
            if(num==12){
                System.out.println("All team out");
                System.out.println("Next inning will start play after 30 minutes");
                return;
            }
            String st="select player from player_info where no = "+num+";";
            rs = stm.executeQuery(st);
                if(rs.next()) {
                    String pl = rs.getString("player");
                    String str1 = "insert into india values('" + pl + "', 'not out', 0, 0, 0, 0, 0);";
                    stm.executeUpdate(str1);
                    System.out.println(pl + " is the next batter");
                }

        }catch(Exception e){
            System.out.println("Error:"+e);
        }

    }
}
