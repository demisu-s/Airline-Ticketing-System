import javax.swing.*;
import java.util.ArrayList;
import java.sql.*;
public class User {
    String name;
    String passportNo;
    ArrayList<Ticket> ticketsBought = new ArrayList<>();
    static ResultSet TableGenerator(String userName)
    {
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlinesDatabase",
                    "root","root123");
            String query = "select * from UserTickets where Name = ? ";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            JOptionPane.showMessageDialog(MainClass.currentWindow, ex.getMessage());
            return null;
        }
    }
    static void InsertIntoUser(String Name, String from, String to, String classtype, String date)
    {
        try {
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlinesDatabase",
                    "root","root123");
            String query = "insert into UserTickets values(?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con1.prepareStatement(query);
            preparedStatement.setString(1, Name);
            preparedStatement.setString(2, from);
            preparedStatement.setString(3, to);
            preparedStatement.setString(4, classtype);
            preparedStatement.setString(5, date);
            preparedStatement.setString(6, "Not reserved");
            preparedStatement.executeUpdate();
            String query3 = "select * from UserTable where Name = ?";
            PreparedStatement statement = con1.prepareStatement(query3);
            statement.setString(1, Name);
            ResultSet result = statement.executeQuery();
            int i = 0;
            while(result.next())
            {
                i = result.getRow();
            }
            if(i == 0)
            {
                String query2 = "insert into UserTable values(?, ?)";
                PreparedStatement preparedStatement2 = con1.prepareStatement(query2);
                preparedStatement2.setString(1, Name);
                preparedStatement2.setString(2, MainClass.passportNO);
                preparedStatement2.executeUpdate();
            }
            con1.close();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(MainClass.currentWindow, "There was a problem " +
                    "connecting to the database.");
        }
    }
    static void RemoveFromUser(String Name, String from, String to, String classtype, String date, String seat)
    {
        try {
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlinesDatabase",
                    "root","root123");
            String query = "delete from UserTickets where Name = ? and Departure_from = ? and Destination = ? " +
                    "and Class = ? and Date_of_flight = ? and Seat = ?";
            PreparedStatement preparedStatement = con1.prepareStatement(query);
            preparedStatement.setString(1, Name);
            preparedStatement.setString(2, from);
            preparedStatement.setString(3, to);
            preparedStatement.setString(4, classtype);
            preparedStatement.setString(5, date);
            preparedStatement.setString(6, seat);
            preparedStatement.executeUpdate();
            Seat.unreserveSeat(seat);

            Ticket.unBuy(from, to, classtype, date);
            if(!seat.equals("Not reserved"))
            {
                Seat.unreserveSeat(seat);
            }
            con1.close();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(MainClass.currentWindow, "There was a problem " +
                    "connecting to the database.");
        }
    }
    public static boolean loginUser(String userName, String passportNo)
    {
        ResultSet resultSet;
        try {
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlinesDatabase",
                    "root","root123");
            String query = "select Passport_No from UserTable where Name = ?";
            PreparedStatement prepareStatement = con1.prepareStatement(query);
            prepareStatement.setString(1, userName);
            try
            {
                resultSet = prepareStatement.executeQuery();
                resultSet.next();
                if(passportNo.equals(resultSet.getString(1)))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
                return false;
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
}
