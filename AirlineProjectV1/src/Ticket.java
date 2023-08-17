import javax.swing.*;
import java.lang.management.ManagementFactory;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.time.*;
import java.sql.*;
import com.mysql.jdbc.Driver;

public class Ticket {
    Flight flight;
    double price;
    boolean isBought = false;
    LocalDateTime dateOfFlight;
    String referenceNo;
    String departurePoint;
    String destination;
    int available;
    String classType; //First, Business or Economy class?
    Connection con;
    ResultSet result;


    public Ticket(String referenceNo,Flight flight, int available , LocalDateTime dateOfFlight, String departurePoint,
                  String destination, String classType, double price)
    {
        this.referenceNo = referenceNo;
        this.flight = flight;
        this.available = available;
        this.dateOfFlight = dateOfFlight;
        this.departurePoint = departurePoint;
        this.destination = destination;
        this.classType = classType;
        this.price = price;
        System.out.println(dateOfFlight.format(
                DateTimeFormatter.ofPattern("HH:mm a - dd/MM/yyyy")));
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlinesDatabase",
                    "root","root123");
            String query = "insert into Tickets values(?, ?, ?, ?, ?, ?, ?, ?)";
            String query2 = "select Departure_from from Tickets";
            String query3 = "select * from Tickets where Departure_from = ? and Destination = ? and Class = ? and " +
                    "Date_of_flight = ?";
            PreparedStatement preparedStatement1 = con.prepareStatement(query3);
            preparedStatement1.setString(1, this.departurePoint);
            preparedStatement1.setString(2, this.destination);
            preparedStatement1.setString(3, this.classType);
            preparedStatement1.setString(4,
                    this.dateOfFlight.format(DateTimeFormatter.ofPattern("HH:mm a - dd/MM/yyyy")));
            result = preparedStatement1.executeQuery();
            int row = 0;
            while (result.next())
            {
                row = result.getRow();
            }
            System.out.println(row);
            if(row == 0) {
                Statement statement = con.createStatement();
                result = statement.executeQuery(query2);
                int n = 0;
                while (result.next()) {
                    n = result.getRow();
                }
                System.out.println(n);
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, String.valueOf(n + 1));
                preparedStatement.setInt(2, available);
                preparedStatement.setString(3, departurePoint);
                preparedStatement.setString(4, destination);
                preparedStatement.setString(5, classType);
                preparedStatement.setString(6, dateOfFlight.format(
                        DateTimeFormatter.ofPattern("HH:mm a - dd/MM/yyyy")));
                preparedStatement.setString(7, flight.flightName);
                preparedStatement.setFloat(8, (float) price);
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
    void Buy(String userName) {
        this.isBought = true;
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlinesDatabase",
                    "root","root123");
            String query = "select Available from Tickets where Departure_from=? and Destination=? and Class = ?" +
                    " and Date_of_flight = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, this.departurePoint);
            preparedStatement.setString(2, this.destination);
            preparedStatement.setString(3, this.classType);
            preparedStatement.setString(4, this.dateOfFlight.format(
                    DateTimeFormatter.ofPattern("HH:mm a - dd/MM/yyyy")));
            result = preparedStatement.executeQuery();
            result.next();
            int n = result.getInt(1);
            System.out.println(n);
            String query2 = "update Tickets set Available = ? where Departure_from=? and Destination=?" +
                    " and Class = ? and Date_of_flight = ?";
            PreparedStatement preparedStatement2 = con.prepareStatement(query2);
            preparedStatement2.setInt(1, n-1);
            preparedStatement2.setString(2, this.departurePoint);
            preparedStatement2.setString(3, this.destination);
            preparedStatement2.setString(4, this.classType);
            preparedStatement2.setString(5, this.dateOfFlight.format(
                    DateTimeFormatter.ofPattern("HH:mm a - dd/MM/yyyy")));
            preparedStatement2.executeUpdate();
            System.out.println("Updated successfully");

            /*Adding bought ticket to the user ticket table
            String query3 = "insert into UserTickets values(?, ?, ?, ?, ?, ?)";
            preparedStatement2  = con.prepareStatement(query3);
            preparedStatement2.setString(1, userName);
            preparedStatement2.setString(2, this.departurePoint);
            preparedStatement2.setString(3, this.destination);
            preparedStatement2.setString(4, this.classType);
            preparedStatement2.setString(5, this.dateOfFlight.format(
                    DateTimeFormatter.ofPattern("HH:mm a - dd/MM/yyyy")));
            preparedStatement2.setString(6, "Not reserved");
            preparedStatement2.executeUpdate();*/
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(MainClass.currentWindow, "There was a problem " +
                    "connecting to the database.");
        }
    }

    static ResultSet TableGenerator(String from, String to)
    {
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlinesDatabase",
                    "root","root123");
            String query = "select * from Tickets where Departure_from = ? and Destination = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            System.out.println(from + "\n" + to);
            preparedStatement.setString(1, from);
            preparedStatement.setString(2, to);
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
    static void Buy(String userName,String from, String to, String classtype, String date)
    {
        try
        {
            LocalDateTime dateTime = LocalDateTime.parse(date,
                    DateTimeFormatter.ofPattern("HH:mm a - dd/MM/yyyy"));
            Ticket ticket = new Ticket("",new Flight(MainClass.currentTicket[5]),
                    Integer.parseInt(MainClass.currentTicket[0]),
                    dateTime, from, to, classtype, Float.parseFloat(MainClass.currentTicket[6]));
            ticket.Buy(userName);
            //Adding bought ticket to the user ticket table
            User.InsertIntoUser(userName, from, to, classtype, date);
            JOptionPane.showMessageDialog(MainClass.currentWindow, "You have successfully bought your ticket.");

        }
        catch(Exception ex)
        {
            System.out.println(ex.getLocalizedMessage());
            JOptionPane.showMessageDialog(MainClass.currentWindow, "There was a problem " +
                    "connecting to the database.");
        }
    }
    public static void unBuy(String from, String to, String classtype, String date)
    {
        Connection con;
        ResultSet result;
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlinesDatabase",
                    "root","root123");
            String query = "select Available from Tickets where Departure_from=? and Destination=? and Class = ?" +
                    " and Date_of_flight = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, from);
            preparedStatement.setString(2, to);
            preparedStatement.setString(3, classtype);
            preparedStatement.setString(4, date);
            result = preparedStatement.executeQuery();
            result.next();
            int n = result.getInt(1);
            System.out.println(n);
            String query2 = "update Tickets set Available = ? where Departure_from=? and Destination=?" +
                    " and Class = ? and Date_of_flight = ?";
            PreparedStatement preparedStatement2 = con.prepareStatement(query2);
            preparedStatement2.setInt(1, n+1);
            preparedStatement2.setString(2, from);
            preparedStatement2.setString(3, to);
            preparedStatement2.setString(4, classtype);
            preparedStatement2.setString(5, date);
            preparedStatement2.executeUpdate();
            System.out.println("Updated successfully");

        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(MainClass.currentWindow, "There was a problem " +
                    "connecting to the database.");
        }
    }
}
