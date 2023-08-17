import java.util.ArrayList;

public class Flight {
    ArrayList<Seat> seats = new ArrayList<>();
    String flightName;
    public Flight()
    {

    }
    public Flight(String flightName)
    {
        this.flightName = flightName;
    }
}
