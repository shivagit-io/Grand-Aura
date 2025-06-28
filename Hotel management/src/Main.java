import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String url="jdbc:mysql://localhost:3306/hoteldb";
    private static final String username="root";
    private static final String password="0000";
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection con= DriverManager.getConnection(url,username,password);
            Scanner sc=new Scanner(System.in);
            while(true){
                System.out.println("HOTEL AMVB");
                System.out.println("1.Reserve a room.");
                System.out.println("2.view reservations");
                System.out.println("3.Get room number");
                System.out.println("4.Update reservation");
                System.out.println("5.Delete reservation.");
                System.out.println("0.Exit");
                System.out.print("Choose an option:");
                int choice=sc.nextInt();
                if(choice==1){
                    System.out.print("Enter guest name:");
                    String guest_name=sc.next();
                    System.out.print("Enter room no:");
                    int room_no=sc.nextInt();
                    System.out.print("Enter phone no:");
                    int ph_no=sc.nextInt();
                    String sql="insert into reservation(guest_name,room_no,ph_no) values ('"+guest_name+"','"+room_no+"','"+ph_no+"')";
                    try(Statement st=con.createStatement()){
                       int rowseffected=st.executeUpdate(sql);
                       if(rowseffected>0){
                           System.out.println("Reservation successfull");
                       }else{
                           System.out.println("Reservation failed");
                       }
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }

                }else if(choice==2){
                    String sql="select * from reservation";
                    try(Statement st=con.createStatement()) {
                        ResultSet rs=st.executeQuery(sql);
                        System.out.println("+--------+------------+---------+----------+---------------------+");
                        System.out.println("| res_id | guest_name | room_no | ph_no    | res_date            |");
                        System.out.println("+--------+------------+---------+----------+---------------------+");

                        while(rs.next()){
                            int res_id=rs.getInt("res_id");
                            String guest_name=rs.getString("guest_name");
                            int room_no=rs.getInt("room_no");
                            int ph_no=rs.getInt("ph_no");
                            Timestamp res_date=rs.getTimestamp("res_date");
                            System.out.printf("|%-8d|%-12s|%-9d|%-10d|%-21s|\n",res_id,guest_name,room_no,ph_no,res_date);

                        }
                        System.out.println("+--------+------------+---------+----------+---------------------+");

                    }

                }else if(choice==3){
                    System.out.print("Enter res id:");
                    int res_id=sc.nextInt();
                    System.out.print("Enter guest name:");
                    String guest_name=sc.next();
                    String sql="select room_no from reservation where res_id="+res_id+" and guest_name='"+guest_name+"'";
                    try(Statement st=con.createStatement()){
                        ResultSet rs=st.executeQuery(sql);
                        if(rs.next()){
                            int room_no=rs.getInt("room_no");
                            System.out.println("Room no:"+room_no);
                        }else{
                            System.out.println("reservation not found");
                        }

                    }


                }else if(choice==4){
                    System.out.print("Enter new res id:");
                    int res_id=sc.nextInt();
                    System.out.print("Enter new guest name:");
                    String guest_name=sc.next();
                    System.out.print("Enter new room no:");
                    int room_no=sc.nextInt();
                    System.out.print("Enter new phone no:");
                    int ph_no=sc.nextInt();
                    String sql="update reservation set guest_name='"+guest_name+"',room_no='"+room_no+"',ph_no='"+ph_no+"' where res_id='"+res_id+"'";

                    try(Statement st=con.createStatement()){
                        int rseff=st.executeUpdate(sql);
                        if(rseff>0){
                            System.out.println("update successfull");
                        }else{
                            System.out.println("updation failed");
                        }

                    }

                }else if(choice==5){
                    System.out.print("Enter resrvation id to delete:");
                    int dres_id=sc.nextInt();
                    String sql="delete from reservation where res_id='"+dres_id+"'";
                    try(Statement st=con.createStatement()){
                        int rseff=st.executeUpdate(sql);
                        if(rseff>0){
                            System.out.println("Deletion successfull");
                        }else{
                            System.out.println("Deletion failed");
                        }

                    }

                }else if(choice==0){
                    break;
                }else{
                    System.out.println("Invalid option");
                    break;
                }
            }
            con.close();
            System.out.println("Thank you...");


        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}