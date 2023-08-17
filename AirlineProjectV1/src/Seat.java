import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;
import java.util.ArrayList;
import java.sql.*;

public class Seat {
    static Connection con;
    public static void reserve(String name, String seatName, String from, String to, String classType, String date)
    {
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlinesDatabase",
                    "root", "root123");
            String query = "select * from Seats where Name = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, MainClass.userName);
            ResultSet resultSet = statement.executeQuery();
            int n = 0;
            while (resultSet.next())
            {
                n = resultSet.getRow();
            }
            if(n != 0)
            {
                String query2 = "update Seats set Name = ? where Name = ?";
                PreparedStatement preparedStatement = con.prepareStatement(query2);
                preparedStatement.setString(1, "Nobody");
                preparedStatement.setString(2, MainClass.userName);
                preparedStatement.executeUpdate();
            }
            String query5 = "select Seat from UserTickets where Seat = ? and Departure_from = ? and Destination = ? " +
                    "and Class = ? and Date_of_flight = ?";
            PreparedStatement preparedStatement3 = con.prepareStatement(query5);
            preparedStatement3.setString(1, seatName);
            preparedStatement3.setString(2, from);
            preparedStatement3.setString(3, to);
            preparedStatement3.setString(4, classType);
            preparedStatement3.setString(5, date);
            ResultSet resultSet1 = preparedStatement3.executeQuery();
            int m = 0;
            while (resultSet1.next())
            {
                m = resultSet1.getRow();
            }
            if(m == 0) {
                String query3 = "update Seats set Name = ? where Seat_Name = ?";
                PreparedStatement preparedStatement = con.prepareStatement(query3);
                preparedStatement.setString(1, MainClass.userName);
                preparedStatement.setString(2, seatName);
                preparedStatement.executeUpdate();
                String query4 = "update UserTickets set Seat = ? where Departure_from = ? " +
                        "and Destination = ? and Class = ? and Name = ?";
                PreparedStatement preparedStatement2 = con.prepareStatement(query4);
                preparedStatement2.setString(1, seatName);
                preparedStatement2.setString(2, from);
                preparedStatement2.setString(3, to);
                preparedStatement2.setString(4, classType);
                preparedStatement2.setString(5, name);
                preparedStatement2.executeUpdate();
                JOptionPane.showMessageDialog(MainClass.currentWindow, seatName + " has been successfully reserved.");
            }
            else
            {
                JOptionPane.showMessageDialog(MainClass.currentWindow, seatName + " has already been reserved" +
                        "by another passenger. Try reserving another seat.");
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(MainClass.currentWindow, "There was a problem " +
                    "connecting to the database.");
        }
    }
    public static String seatReserved(String userName)
    {
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlinesDatabase",
                    "root", "root123");
            String query = "select * from Seats where Name = ?";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getString(1);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(MainClass.currentWindow, "There was a problem " +
                    "connecting to the database.");
            return null;
        }
    }
    public static boolean isReserved(String seatName, String from, String to, String classType, String date)
    {
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlinesDatabase",
                    "root", "root123");
            String query5 = "select Seat from UserTickets where Seat = ? and Departure_from = ? and Destination = ? " +
                    "and Class = ? and Date_of_flight = ?";
            PreparedStatement preparedStatement3 = con.prepareStatement(query5);
            preparedStatement3.setString(1, seatName);
            preparedStatement3.setString(2, from);
            preparedStatement3.setString(3, to);
            preparedStatement3.setString(4, classType);
            preparedStatement3.setString(5, date);
            ResultSet resultSet1 = preparedStatement3.executeQuery();
            int m = 0;
            while (resultSet1.next())
            {
                m = resultSet1.getRow();
            }
            if(m == 0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(MainClass.currentWindow, "There was a problem " +
                    "connecting to the database.");
            return false;
        }
    }
    public static void insertSeats(JLabel[] seats)
    {
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlinesDatabase",
                    "root", "root123");
            String query = "insert into Seats values(?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            for (int i = 0; i < seats.length; i++)
            {
                preparedStatement.setString(1, seats[i].getText());
                preparedStatement.setString(2, "Nobody");
                preparedStatement.executeUpdate();
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(MainClass.currentWindow, "There was a problem " +
                    "connecting to the database.");
        }
    }
    public static void unreserveSeat(String name)
    {
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlinesDatabase",
                    "root", "root123");
            String query = "update Seats set Name = 'Nobody' where Seat_Name = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(MainClass.currentWindow, "There was a problem " +
                    "connecting to the database.");
        }
    }
    public static void updateReservation()
    {
        String date = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("HH:mm a - dd/MM/yyyy"));
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlinesDatabase",
                    "root", "root123");
            String query = "select * from UserTickets where Date_of_flight = ? and Seat != 'Not reserved'";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String query2 = "update Seats set Name = ? where Seat_Name = ?";
            PreparedStatement preparedStatement2 = con.prepareStatement(query);
            while(resultSet.next())
            {
                preparedStatement2.setString(1, resultSet.getString(1));
                preparedStatement2.setString(2, resultSet.getString(6));
                preparedStatement2.executeUpdate();
                resultSet.next();
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(MainClass.currentWindow, "There was a problem " +
                    "connecting to the database.");
        }
    }
}
