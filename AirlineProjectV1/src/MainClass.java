import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MainClass extends JFrame {
    //port number is 3306
    //username is root
    //password: Strong*Password
    public static int WELCOME = 0;
    public static int CHECKTICKETS = 1;
    public static int CHOOSELOCATION = 2;
    public static int CHOOSETICKET = 3;
    public static int PAYMENT = 4;
    //public static int TICKETDETAILS = 5;
    public static int SEATRESERVATION = 5;
    public static int LOGIN = 6;
    public static JFrame currentWindow;
    public static int currentIndex;

    public static String departureChoice;
    public static String destinationChoice;
    public static String[] currentTicket;
    public static String[] reservingTicket;
    public static String[] changedTicket = new String[5];
    public static String userName;
    public static String passportNO;
    public static boolean changingTicket;

    JFrame frame;
    Container container;

    JButton buyTickets;
    JButton manageTickets;
    JComboBox departure;
    JComboBox destination;
    JButton choose;
    JButton back;
    JTable ticketsTable;
    JButton confirmBuy;
    JButton confirm;
    JTextField AccountNo;
    JPasswordField Password;
    JTextField PassportNo;
    JTextField Name;
    JTable tickets;
    JButton reserveSeat;
    JButton changeTicket;
    JButton cancelTicket;
    JComboBox seatChoice;
    JButton confirmSeat;
    JButton login;
    JButton backLogin;
    JTextField loginName;
    JTextField loginPassport;
    static JLabel A1,A2,A3,A4,A5,A6,A7,A8,A9,A10,A11,A12;
    static JLabel B1,B2,B3,B4,B5,B6,B7,B8,B9,B10,B11,B12;
    static JLabel C1,C2,C3,C4,C5,C6,C7,C8,C9,C10,
            C11,C12,C13,C14,C15,C16,C17,C18,C19,C20,
            C21,C22,C23,C24,C25,C26,C27,C28,C29,C30;
    static JLabel[] firstClassSeats = {A1,A2,A3,A4,A5,A6,A7,A8,A9,A10,A11,A12};
    static JLabel[] businessClassSeats = {B1,B2,B3,B4,B5,B6,B7,B8,B9,B10,B11,B12};
    static JLabel[] economyClassSeats = {C1,C2,C3,C4,C5,C6,C7,C8,C9,C10,C11,C12};

    String[] countries = {"Afghanistan","Albania","Algeria","American Samoa","Andorra","Angola","Anguilla","Antarctica","Antigua and Barbuda",
            "Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus",
            "Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia and Herzegovina","Botswana","Brazil","Brunei Darussalam",
            "Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada","Cape Verde","Cayman Islands","Central African Republic",
            "Chad","Chile","China","Christmas Island","Cocos Islands","Colombia","Comoros","Democratic Republic of the Congo (Kinshasa)",
            "Central Africa","Republic of Congo(Brazzaville)","Cook Islands","Costa Rica","Côte D'ivoire (Ivory Coast)","Croatia","Cuba","Cyprus",
            "Czech Republic","Denmark","Djibouti","Dominique","Dominican Republic","East Timor (Timor-Leste)","Ecuador","Egypt",
            "El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia","Falkland Islands","Faroe Islands","Fiji","Finland","France",
            "French Guiana","French Polynesia","French Southern Territories","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar",
            "Greece","Greenland","Grenada","Guadeloupe","Guam","Guatemala","Guinea","Guinea-Bissau","Guyana","Haiti","Holy See",
            "Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Israel","Italy","Ivory Coast","Jamaica",
            "Japan","Jordan","Kazakhstan","Kenya","Kiribati","Korea, North","Korea, South","Kosovo","Kuwait","Kyrgyzstan","Lao","Latvia",
            "Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macau","Madagascar","Malawi","Malaysia",
            "Maldives","Mali","Malta","Marshall Islands","Martinique","Mauritania","Mauritius","Mayotte","Mexico","Micronesia","Moldova",
            "Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","Myanmar","Namibia","Nauru","Nepal","Netherlands",
            "New Caledonia","New Zealand","Nicaragua","Niger","Nigeria","Niue","North Macedonia","Northern Mariana Islands","Norway",
            "Oman","Pakistan","Palau","Palestinian territories","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Pitcairn Island",
            "Poland","Portugal","Puerto Rico","Qatar","Reunion Island","Romania","Russia","Rwanda","Saint Kitts and Nevis","Saint Lucia",
            "Saint Vincent and the Grenadines","Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal","Serbia","Seychelles",
            "Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","South Sudan","Spain","Sri Lanka",
            "Sudan","Suriname","Swaziland (Eswatini)","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Tibet",
            "Timor-Leste (East Timor)","Togo","Tokelau","Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan",
            "Turks and Caicos Islands","Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States","Uruguay",
            "Uzbekistan","Vanuatu","Vatican City State (Holy See)","Venezuela","Vietnam","Virgin Islands (British)","Virgin Islands (U.S.)",
            "Wallis and Futuna Islands","Western Sahara","Yemen","Zambia","Zimbabwe"
    };
    public static void imageAssign(JFrame Frame)
    {
        try
        {
            BufferedImage img = ImageIO.read(new File("imageIcon.png"));
            Frame.setIconImage(new ImageIcon(img).getImage());
        }
        catch (Exception ex)
        {

        }
    }
    public MainClass(int index)
    {
        super("");
        for (int i = 0; i < firstClassSeats.length; i++)
        {
            firstClassSeats[i] = new JLabel("A"+(i+1));
            firstClassSeats[i].setName("A"+(i+1));
            //firstClassSeats[i].setBackground(Color.GRAY);
        }
        for (int i = 0; i < businessClassSeats.length; i++)
        {
            businessClassSeats[i] = new JLabel("B"+(i+1));
            businessClassSeats[i].setName("B"+(i+1));
            //businessClassSeats[i].setBackground(Color.GRAY);
        }
        for (int i = 0; i < economyClassSeats.length; i++)
        {
            economyClassSeats[i] = new JLabel("C"+(i+1));
            economyClassSeats[i].setName("C"+(i+1));
            //economyClassSeats[i].setBackground(Color.GRAY);
        }
        if(currentWindow != null)
        {
            currentWindow.setVisible(false);
        }
        if(index == WELCOME)
        {
            frame = new JFrame("Welcome!");
            currentWindow = frame;
            currentIndex = index;
            frame.setSize(500, 400);
            frame.setLocation(500, 200);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            imageAssign(frame);
            container = frame.getContentPane();
            container.setLayout(new GridLayout(3,1));

            userName = null;
            changingTicket = false;
            JLabel logo;
            JPanel panell = new JPanel();
            try
            {
                BufferedImage image = ImageIO.read(new File("Imagelow.png"));
                logo = new JLabel(new ImageIcon(image));
            }
            catch (Exception ex)
            {
                logo = new JLabel("");
            }
            JPanel panel1 = new JPanel();
            panel1.setLayout(new FlowLayout());

            JLabel welcomeText = new JLabel("       Welcome! Please choose one of the following options.");
            welcomeText.setFont(new Font("Arial", Font.BOLD, 14));
            buyTickets = new JButton("Buy Tickets");
            buyTickets.setBackground(Color.BLUE);
            buyTickets.setForeground(Color.white);
            buyTickets.setPreferredSize(new Dimension(150, 50));
            manageTickets = new JButton("Manage Tickets");
            manageTickets.setBackground(Color.BLUE);
            manageTickets.setForeground(Color.white);
            manageTickets.setPreferredSize(new Dimension(150, 50));

            buyTickets.addActionListener(new Listener());
            manageTickets.addActionListener(new Listener());

            panell.add(logo);
            container.add(panell);
            container.add(welcomeText);
            panel1.add(buyTickets);
            panel1.add(manageTickets);
            container.add(panel1);
            frame.setVisible(true);

        }
        if(index == CHOOSELOCATION)
        {
            frame = new JFrame("Departure and Destination points");
            currentWindow = frame;
            currentIndex = index;
            frame.setSize(375, 450);
            frame.setLocation(500, 50);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            imageAssign(frame);
            container = frame.getContentPane();
            container.setLayout(new GridLayout(4,1));

            JPanel panel1 = new JPanel();
            panel1.setLayout(new FlowLayout());
            JPanel panel2 = new JPanel();
            panel2.setLayout(new FlowLayout());
            JPanel panel3 = new JPanel();
            panel3.setLayout(new FlowLayout());

            JLabel instructions = new JLabel("Choose your point of departure and destination.");
            instructions.setFont(new Font("Arial", Font.BOLD, 14));
            JLabel departureText = new JLabel("Point of Departure");
            JLabel destinationText = new JLabel("Destination");
            choose = new JButton("Confirm Choice");
            choose.setBackground(Color.BLUE);
            choose.setForeground(Color.white);
            departure = new JComboBox(countries);
            departure.setPreferredSize(new Dimension(150, 30));
            destination = new JComboBox(countries);
            destination.setPreferredSize(new Dimension(150, 30));
            back = new JButton("Back");
            back.setBackground(Color.BLUE);
            back.setForeground(Color.white);

            choose.addActionListener(new Listener());
            back.addActionListener(new Listener());

            container.add(instructions);
            panel1.add(departureText);
            panel1.add(departure);
            panel2.add(destinationText);
            panel2.add(destination);
            panel3.add(choose);
            panel3.add(back);
            container.add(panel1);
            container.add(panel2);
            container.add(panel3);
            frame.setVisible(true);
        }
        if(index == CHOOSETICKET)
        {
            frame = new JFrame("Tickets");
            currentWindow = frame;
            currentIndex = index;
            frame.setSize(1100, 400);
            frame.setLocation(100, 50);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            imageAssign(frame);
            container = frame.getContentPane();
            container.setLayout(new GridLayout(3,1));

            JPanel panel1 = new JPanel();
            panel1.setLayout(new FlowLayout());

            JLabel instructions = new JLabel("  Click on the ticket you want from the table and " +
                    "click confirm to buy it.");
            instructions.setFont(new Font("Arial", Font.BOLD, 16));
            confirmBuy = new JButton("Confirm");
            confirmBuy.setBackground(Color.BLUE);
            confirmBuy.setForeground(Color.white);
            ticketsTable = new JTable();
            ticketsTable.setModel(new DefaultTableModel(
                    new Object[][]
                            {},
                    new String[]
                {
                        "No", "Tickets Available", "From", "To", "Class", "Time and Date", "Flight Name", "Price in ETB"
                }
            ));
            JScrollPane jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(ticketsTable);
            DefaultTableModel tableModel = (DefaultTableModel) ticketsTable.getModel();
            try
            {
                ResultSet resultSet = Ticket.TableGenerator(departureChoice, destinationChoice);
                int rowCount = 0;
                while(resultSet.next())
                {
                    rowCount = resultSet.getRow();
                }
                resultSet =  Ticket.TableGenerator(departureChoice, destinationChoice);
                resultSet.next();
                String[] rowData = new String[8];
                for(int i = 0; i < rowCount; i++)
                {
                    rowData[0] = String.valueOf(resultSet.getInt(1));
                    rowData[1] = String.valueOf(resultSet.getInt(2));
                    rowData[2] = resultSet.getString(3);
                    rowData[3] = resultSet.getString(4);
                    rowData[4] = resultSet.getString(5);
                    rowData[5] = resultSet.getString(6);
                    rowData[6] = resultSet.getString(7);
                    rowData[7] = String.valueOf(resultSet.getFloat(8));
                    tableModel.addRow(rowData);
                    resultSet.next();
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex.toString());
                JOptionPane.showMessageDialog(currentWindow, ex.getMessage());
            }
            back = new JButton("Back");
            back.setBackground(Color.BLUE);
            back.setForeground(Color.white);

            confirmBuy.addActionListener(new Listener());
            back.addActionListener(new Listener());

            container.add(instructions);
            panel1.add(confirmBuy);
            panel1.add(back);
            container.add(panel1);
            container.add(jScrollPane, BorderLayout.CENTER);
            frame.setVisible(true);
        }
        if(index == PAYMENT)
        {
            frame = new JFrame("Tickets");
            currentWindow = frame;
            currentIndex = index;
            frame.setSize(500, 450);
            frame.setLocation(500, 50);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            imageAssign(frame);
            container = frame.getContentPane();
            container.setLayout(new GridLayout(6,1));

            JPanel panel1 = new JPanel();
            panel1.setLayout(new FlowLayout());
            JPanel panel2 = new JPanel();
            panel2.setLayout(new FlowLayout());
            JPanel panel3 = new JPanel();
            panel3.setLayout(new FlowLayout());
            JPanel panel4 = new JPanel();
            panel4.setLayout(new FlowLayout());
            JPanel panel5 = new JPanel();
            panel5.setLayout(new GridLayout(1,1));

            JLabel instructions = new JLabel("  Type in your details, then click confirm.");
            JLabel accountNo = new JLabel("Account number");
            JLabel name = new JLabel("Full name");
            JLabel passportNo = new JLabel("Passport Number");
            JLabel password = new JLabel("Password");
            confirm = new JButton("Confirm");
            confirm.setBackground(Color.BLUE);
            confirm.setForeground(Color.white);
            confirm.setPreferredSize(new Dimension(100, 50));
            AccountNo = new JTextField();
            AccountNo.setPreferredSize(new Dimension(150,40));
            Name = new JTextField();
            Name.setPreferredSize(new Dimension(150, 40));
            PassportNo = new JTextField();
            PassportNo.setPreferredSize(new Dimension(150,40));
            Password = new JPasswordField();
            Password.setPreferredSize(new Dimension(100,40));
            back = new JButton("Back");
            back.setBackground(Color.BLUE);
            back.setForeground(Color.white);
            back.setPreferredSize(new Dimension(100, 50));

            confirm.addActionListener(new Listener());
            back.addActionListener(new Listener());

            container.add(instructions);
            panel1.add(accountNo);
            panel1.add(AccountNo);
            panel2.add(password);
            panel2.add(Password);
            panel3.add(name);
            panel3.add(Name);
            panel4.add(passportNo);
            panel4.add(PassportNo);
            panel5.add(confirm);
            panel5.add(back);
            container.add(panel1);
            container.add(panel2);
            container.add(panel3);
            container.add(panel4);
            container.add(panel5);
            frame.setVisible(true);
        }
        if(index == CHECKTICKETS)
        {
            frame = new JFrame("Manage Tickets");
            currentWindow = frame;
            currentIndex = index;
            frame.setSize(700, 600);
            frame.setLocation(500, 50);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            imageAssign(frame);
            container = frame.getContentPane();
            container.setLayout(new GridLayout(3,1));

            JPanel panel1 = new JPanel();
            panel1.setLayout(new FlowLayout());

            JLabel instructions = new JLabel("  Choose one of the following tickets \n and pick an option");
            instructions.setFont(new Font("Arial", Font.BOLD, 16));
            tickets = new JTable();
            tickets.setModel(new DefaultTableModel(
                    new Object[][]
                            {},
                    new String[]
                            {
                                    "From", "To", "Class", "Time and Date", "Seat"
                            }
            ));
            DefaultTableModel tableModel = (DefaultTableModel) tickets.getModel();
            try
            {
                ResultSet resultSet = User.TableGenerator(userName);
                int rowCount = 0;
                while(resultSet.next())
                {
                    rowCount = resultSet.getRow();
                }
                resultSet =  User.TableGenerator(userName);
                resultSet.next();
                String[] rowData = new String[5];
                for(int i = 0; i < rowCount; i++)
                {
                    rowData[0] = resultSet.getString(2).toString();
                    rowData[1] = resultSet.getString(3).toString();
                    rowData[2] = resultSet.getString(4).toString();
                    rowData[3] = resultSet.getString(5).toString();
                    rowData[4] = resultSet.getString(6).toString();
                    tableModel.addRow(rowData);
                    resultSet.next();
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex.toString());
                JOptionPane.showMessageDialog(currentWindow, ex.getMessage());
            }
            JScrollPane jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(tickets);
            reserveSeat = new JButton("Reserve Seat");
            changeTicket = new JButton("Change Ticket");
            cancelTicket = new JButton("Cancel Ticket");
            reserveSeat.setBackground(Color.BLUE);
            reserveSeat.setForeground(Color.white);
            reserveSeat.setPreferredSize(new Dimension(200, 50));
            changeTicket.setBackground(Color.BLUE);
            changeTicket.setForeground(Color.white);
            changeTicket.setPreferredSize(new Dimension(200, 50));
            cancelTicket.setBackground(Color.BLUE);
            cancelTicket.setForeground(Color.white);
            cancelTicket.setPreferredSize(new Dimension(200, 50));
            back = new JButton("Back");
            back.setBackground(Color.BLUE);
            back.setForeground(Color.white);
            back.setPreferredSize(new Dimension(100, 50));

            reserveSeat.addActionListener(new Listener());
            changeTicket.addActionListener(new Listener());
            cancelTicket.addActionListener(new Listener());
            back.addActionListener(new Listener());

            container.add(instructions);
            container.add(jScrollPane);
            panel1.add(reserveSeat, BorderLayout.WEST);
            panel1.add(changeTicket, BorderLayout.CENTER);
            panel1.add(cancelTicket, BorderLayout.EAST);
            panel1.add(back, BorderLayout.SOUTH);
            container.add(panel1);
            frame.setVisible(true);
        }
        if(index == SEATRESERVATION)
        {
            frame = new JFrame("Seat Reservation");
            currentWindow = frame;
            currentIndex = index;
            frame.setSize(500, 700);
            frame.setLocation(500, 30);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            imageAssign(frame);
            container = frame.getContentPane();
            container.setLayout(new GridLayout(3,1));

            JPanel panel1 = new JPanel();
            panel1.setLayout(new FlowLayout());
            JPanel panel2 = new JPanel();
            panel2.setLayout(new GridLayout(2, 6));
            JPanel panel3 = new JPanel();
            panel3.setLayout(new GridLayout(2, 6));
            JPanel panel4 = new JPanel();
            panel4.setLayout(new GridLayout(2, 6));
            JPanel panel5 = new JPanel();
            panel5.setLayout(new FlowLayout());

            JLabel instructions = new JLabel("Choose a seat after referring to the diagram below.");
            instructions.setFont(new Font("Arial", Font.BOLD, 16));
            seatChoice = new JComboBox();
            seatChoice.setPreferredSize(new Dimension(100, 50));
            confirmSeat = new JButton("Confirm Choice");
            confirmSeat.setBackground(Color.BLUE);
            confirmSeat.setForeground(Color.white);
            confirmSeat.setPreferredSize(new Dimension(150, 50));
            back = new JButton("Back");
            back.setBackground(Color.BLUE);
            back.setForeground(Color.white);
            back.setPreferredSize(new Dimension(100, 50));

            confirmSeat.addActionListener(new Listener());
            back.addActionListener(new Listener());

            panel1.add(instructions);
            panel5.add(seatChoice);
            panel5.add(confirmSeat);
            panel5.add(back);
            container.add(panel1);
            container.add(panel5);
            if(reservingTicket[2].equals("First"))
            {
                for (int i = 0; i < firstClassSeats.length; i++)
                {
                    seatChoice.addItem(firstClassSeats[i].getText());
                    panel2.add(firstClassSeats[i]);
                    firstClassSeats[i].setBorder(BorderFactory.createLineBorder(Color.black));
                    firstClassSeats[i].setPreferredSize(new Dimension(50,50));
                    if(Seat.isReserved(firstClassSeats[i].getText(), reservingTicket[0], reservingTicket[1],
                            reservingTicket[2], reservingTicket[3]))
                    {
                        firstClassSeats[i].setBorder(BorderFactory.createLineBorder(Color.red));
                    }
                    else
                    {
                        firstClassSeats[i].setBorder(BorderFactory.createLineBorder(Color.black));
                    }
                }
                container.add(panel2);
            }
            if(reservingTicket[2].equals("Business"))
            {
                for (int i = 0; i < businessClassSeats.length; i++)
                {
                    seatChoice.addItem(businessClassSeats[i].getText());
                    panel3.add(businessClassSeats[i]);
                    businessClassSeats[i].setPreferredSize(new Dimension(50,50));
                    if(Seat.isReserved(businessClassSeats[i].getText(), reservingTicket[0], reservingTicket[1],
                            reservingTicket[2], reservingTicket[3]))
                    {
                        businessClassSeats[i].setBorder(BorderFactory.createLineBorder(Color.red));
                    }
                    else
                    {
                        businessClassSeats[i].setBorder(BorderFactory.createLineBorder(Color.black));
                    }
                }
                container.add(panel3);
            }
            if(reservingTicket[2].equals("Economy"))
            {
                for (int i = 0; i < economyClassSeats.length; i++) {
                    seatChoice.addItem(economyClassSeats[i].getText());
                    panel4.add(economyClassSeats[i]);
                    economyClassSeats[i].setBorder(BorderFactory.createLineBorder(Color.black));
                    economyClassSeats[i].setPreferredSize(new Dimension(50,50));
                    if(Seat.isReserved(economyClassSeats[i].getText(), reservingTicket[0], reservingTicket[1],
                            reservingTicket[2], reservingTicket[3]))
                    {
                        economyClassSeats[i].setBorder(BorderFactory.createLineBorder(Color.red));
                    }
                    else
                    {
                        economyClassSeats[i].setBorder(BorderFactory.createLineBorder(Color.black));
                    }
                }
                container.add(panel4);
            }
            frame.setVisible(true);
        }
        if(index == LOGIN)
        {
            frame = new JFrame("Login");
            currentWindow = frame;
            currentIndex = index;
            frame.setSize(400, 300);
            frame.setLocation(500, 200);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            imageAssign(frame);
            container = frame.getContentPane();
            container.setLayout(new GridLayout(3,1));

            JPanel panel1 = new JPanel();
            panel1.setLayout(new FlowLayout());
            JPanel panel2 = new JPanel();
            panel1.setLayout(new FlowLayout());
            JPanel panel3 = new JPanel();

            JLabel instructions = new JLabel("Type in your details.");
            login = new JButton("Login");
            login.addActionListener(new Listener());
            login.setBackground(Color.BLUE);
            login.setForeground(Color.white);
            JLabel name = new JLabel("Name");
            loginName = new JTextField();
            loginName.setPreferredSize(new Dimension(150, 50));
            JLabel passport = new JLabel("Passport number");
            loginPassport = new JTextField();
            loginPassport.setPreferredSize(new Dimension(100, 50));
            backLogin = new JButton("Back");
            backLogin.setBackground(Color.BLUE);
            backLogin.setForeground(Color.white);
            backLogin.addActionListener(new Listener());

            panel1.add(name);
            panel1.add(loginName);
            container.add(panel1);
            panel2.add(passport);
            panel2.add(loginPassport);
            container.add(panel2);
            panel3.add(login, BorderLayout.CENTER);
            panel3.add(backLogin, BorderLayout.SOUTH);
            container.add(panel3);
            frame.setVisible(true);
        }
    }
    class Listener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == buyTickets)
            {
                new MainClass(MainClass.CHOOSELOCATION);
            }
            if(e.getSource() == manageTickets)
            {
                new MainClass(MainClass.LOGIN);
            }
            if(e.getSource() == back)
            {
                if(currentIndex == MainClass.CHOOSELOCATION)
                {
                    System.out.println(userName);
                    new MainClass(MainClass.WELCOME);
                }
                if(currentIndex == MainClass.CHOOSETICKET)
                {
                    if(!changingTicket)
                    {
                        new MainClass(MainClass.CHOOSELOCATION);
                    }
                    else
                    {
                        new MainClass(MainClass.LOGIN);
                    }
                }
                if(currentIndex == MainClass.PAYMENT)
                {
                    new MainClass(MainClass.CHOOSETICKET);
                }
                if(currentIndex == MainClass.CHECKTICKETS)
                {
                    new MainClass(MainClass.LOGIN);
                }
                if(currentIndex == MainClass.SEATRESERVATION)
                {
                    new MainClass(MainClass.CHECKTICKETS);
                }
            }
            if(e.getSource() == backLogin)
            {
                new MainClass(MainClass.WELCOME);
            }
            if(e.getSource() == choose)
            {
                departureChoice = departure.getSelectedItem().toString();
                destinationChoice = destination.getSelectedItem().toString();
                new MainClass(MainClass.CHOOSETICKET);
            }
            if(e.getSource() == confirmBuy)
            {
                int i = ticketsTable.getSelectedRow();
                DefaultTableModel tableModel = (DefaultTableModel) ticketsTable.getModel();
                //String refNo = tableModel.getValueAt(i, 2).toString().substring(0,2) +
                //        tableModel.getValueAt(i, 3).toString().substring(0,2);
                //String flightName = tableModel.getValueAt(i, 6).toString();
                //String available = tableModel.getValueAt(i, 1).toString();
                String dateTime = tableModel.getValueAt(i, 5).toString();
                String from = tableModel.getValueAt(i, 2).toString();
                String to = tableModel.getValueAt(i, 3).toString();
                String classType = tableModel.getValueAt(i, 4).toString();
                String available = tableModel.getValueAt(i, 1).toString();
                String flt = tableModel.getValueAt(i, 6).toString();
                String price = tableModel.getValueAt(i, 7).toString();

                currentTicket = new String[]{available, from, to, classType, dateTime, flt, price};
                new MainClass(MainClass.PAYMENT);
            }
            if(e.getSource() == confirm)
            {
                userName = Name.getText();
                passportNO = PassportNo.getText();
                Ticket.Buy(userName,currentTicket[1], currentTicket[2], currentTicket[3], currentTicket[4]);
                userName = Name.getText();
                if(changingTicket)
                {
                    User.RemoveFromUser(userName, changedTicket[0],changedTicket[1],changedTicket[2],
                            changedTicket[3],changedTicket[4]);
                    changingTicket = false;
                }
                new MainClass(MainClass.CHECKTICKETS);
            }
            if(e.getSource() == reserveSeat)
            {
                int i = tickets.getSelectedRow();
                DefaultTableModel tableModel = (DefaultTableModel) tickets.getModel();
                reservingTicket = new String[]{tableModel.getValueAt(i, 0).toString(),
                tableModel.getValueAt(i, 1).toString(),
                tableModel.getValueAt(i, 2).toString(),
                        tableModel.getValueAt(i, 3).toString()};
                new MainClass(MainClass.SEATRESERVATION);
            }
            if(e.getSource() == changeTicket)
            {
                changedTicket[0] = tickets.getValueAt(tickets.getSelectedRow(), 0).toString();
                changedTicket[1] = tickets.getValueAt(tickets.getSelectedRow(), 1).toString();
                changedTicket[2] = tickets.getValueAt(tickets.getSelectedRow(), 2).toString();
                changedTicket[3] = tickets.getValueAt(tickets.getSelectedRow(), 3).toString();
                changedTicket[4] = tickets.getValueAt(tickets.getSelectedRow(), 4).toString();
                departureChoice = tickets.getValueAt(tickets.getSelectedRow(), 0).toString();
                destinationChoice = tickets.getValueAt(tickets.getSelectedRow(), 1).toString();
                new MainClass(MainClass.CHOOSETICKET);
                changingTicket = true;
            }
            if(e.getSource() == cancelTicket)
            {
                try
                {
                    changedTicket[0] = tickets.getValueAt(tickets.getSelectedRow(), 0).toString();
                    changedTicket[1] = tickets.getValueAt(tickets.getSelectedRow(), 1).toString();
                    changedTicket[2] = tickets.getValueAt(tickets.getSelectedRow(), 2).toString();
                    changedTicket[3] = tickets.getValueAt(tickets.getSelectedRow(), 3).toString();
                    changedTicket[4] = tickets.getValueAt(tickets.getSelectedRow(), 4).toString();
                    User.RemoveFromUser(userName, changedTicket[0], changedTicket[1], changedTicket[2],
                            changedTicket[3], changedTicket[4]);
                    JOptionPane.showMessageDialog(currentWindow, "Cancelled Ticket Successfully.");
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(currentWindow, "There was a problem.");
                }
            }
            if(e.getSource() == confirmSeat)
            {
                String seat = seatChoice.getSelectedItem().toString();
                Seat.reserve(userName, seat, reservingTicket[0], reservingTicket[1],
                        reservingTicket[2], reservingTicket[3]);

            }
            if(e.getSource() == login)
            {
                userName = loginName.getText();
                String passNo = loginPassport.getText();
                boolean correctLogin = User.loginUser(userName, passNo);
                if(correctLogin)
                {
                    new MainClass(CHECKTICKETS);
                }
                else
                {
                    JOptionPane.showMessageDialog(MainClass.currentWindow, "Your login details are not correct, " +
                            "please try again");
                }
            }
        }
    }
    public static void main(String[] args)
    {
        new MainClass(MainClass.WELCOME);
        Seat.updateReservation();
        /*Scanner s = new Scanner(System.in);
        int i = s.nextInt();
        new MainClass(i);*/
        /*Ticket ticket = new Ticket("EthMal22",new Flight("FLT420"), 12,
                LocalDateTime.of(2022, 2, 18, 9, 30), "Ethiopia",
                "Malawi", "First", 25000);
        Ticket ticket2 = new Ticket("EthMal22",new Flight("FLT420"), 12,
                        LocalDateTime.of(2022, 2, 18, 9, 30), "Ethiopia",
                        "Malawi", "Business", 15000);
        Ticket ticket3 = new Ticket("EthMal22",new Flight("FLT420"), 30,
                LocalDateTime.of(2022, 2, 18, 9, 30), "Ethiopia",
                "Malawi", "Economy", 10000);
        Ticket ticket4 = new Ticket("EthRus22",new Flight("FLT420"), 12,
                LocalDateTime.of(2022, 2, 20, 13, 30), "Ethiopia",
                "Russia", "First", 30000);
        Ticket ticket5 = new Ticket("EthRus22",new Flight("FLT420"), 12,
                LocalDateTime.of(2022, 2, 20, 13, 30), "Ethiopia",
                "Russia", "Business", 20000);
        Ticket ticket6 = new Ticket("EthRus22",new Flight("FLT420"), 30,
                LocalDateTime.of(2022, 2, 20, 13, 30), "Ethiopia",
                "Russia", "Economy", 15000);
        Ticket ticket7 = new Ticket("EthCan22",new Flight("FLT420"), 12,
                LocalDateTime.of(2022, 2, 24, 6, 30), "Ethiopia",
                "Canada", "First", 35000);
        Ticket ticket8 = new Ticket("EthCan22",new Flight("FLT420"), 12,
                LocalDateTime.of(2022, 2, 24, 6, 30), "Ethiopia",
                "Canada", "Business", 25000);
        Ticket ticket9 = new Ticket("EthCan22",new Flight("FLT420"), 30,
                LocalDateTime.of(2022, 2, 24, 6, 30), "Ethiopia",
                "Canada", "Economy", 20000);

         */
        //Seat.insertSeats(firstClassSeats);
        //Seat.insertSeats(businessClassSeats);
        //Seat.insertSeats(economyClassSeats);
    }
}
