package algonquin.cst2335.finalproject.Entities;


        import static androidx.room.ForeignKey.CASCADE;

        import androidx.room.ColumnInfo;
        import androidx.room.Entity;
        import androidx.room.ForeignKey;
        import androidx.room.PrimaryKey;

/**
 *  Represents a Flight entity.
 *  This class is used to store information about a flight.
 */
@Entity(tableName = "Flight",
        foreignKeys = {
                @ForeignKey(entity = Airport.class,
                        parentColumns = "airportId",
                        childColumns = "departureId",
                        onDelete = CASCADE,
                        onUpdate = CASCADE
                ),
                @ForeignKey(entity = Airport.class,
                        parentColumns = "airportId",
                        childColumns = "arrivalId",
                        onDelete = CASCADE,
                        onUpdate = CASCADE)
        })
public class Flight {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "flightId")
    public long flightId;

    @ColumnInfo(name = "departureId", index = true)
    private long departureId;

    @ColumnInfo(name = "arrivalId", index = true)
    private long arrivalId;

    @ColumnInfo(name = "flight_date")
    private String flight_date;

    @ColumnInfo(name = "airline_name")
    private String airline_name;

    @ColumnInfo(name = "airline_iata")
    private String airline_iata;

    @ColumnInfo(name = "airline_icao")
    private String airline_icao;

    @ColumnInfo(name = "flight_number")
    private String flight_number;

    @ColumnInfo(name = "flight_iata")
    private String flight_iata;

    @ColumnInfo(name = "flight_icao")
    private String flight_icao;
    /**

     Sets the flight ID.
     @param flightId the flight ID to set
     */
    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }
    /**

     Gets the flight ID.
     @return the flight ID
     */
    public long getFlightId() {
        return this.flightId;
    }
    /**

     Gets the departure ID.
     @return the departure ID
     */
    public long getDepartureId() {
        return departureId;
    }
    /**

     Sets the departure ID.
     @param departureId the departure ID to set
     */
    public void setDepartureId(long departureId) {
        this.departureId = departureId;
    }
    /**

     Gets the arrival ID.
     @return the arrival ID
     */
    public long getArrivalId() {
        return arrivalId;
    }
    /**

     Sets the arrival ID.
     @param arrivalId the arrival ID to set
     */
    public void setArrivalId(long arrivalId) {
        this.arrivalId = arrivalId;
    }
    /**

     Gets the flight date.
     @return the flight date
     */
    public String getFlight_date() {
        return flight_date;
    }
    /**

     Sets the flight date.
     @param flight_date the flight date to set
     */
    public void setFlight_date(String flight_date) {
        this.flight_date = flight_date;
    }
    /**

     Gets the airline name.
     @return the airline name
     */
    public String getAirline_name() {
        return airline_name;
    }
    /**

     Sets the airline name.
     @param airline_name the airline name to set
     */
    public void setAirline_name(String airline_name) {
        this.airline_name = airline_name;
    }
    /**

     Gets the airline IATA code.
     @return the airline IATA code
     */
    public String getAirline_iata() {
        return airline_iata;
    }
    /**

     Sets the airline IATA code.
     @param airline_iata the airline IATA code to set
     */
    public void setAirline_iata(String airline_iata) {
        this.airline_iata = airline_iata;
    }
    /**

     Gets the airline ICAO code.
     @return the airline ICAO code
     */
    public String getAirline_icao() {
        return airline_icao;
    }
    /**

     Sets the airline ICAO code.
     @param airline_icao the airline ICAO code to set
     */
    public void setAirline_icao(String airline_icao) {
        this.airline_icao = airline_icao;
    }
    /**

     Gets the flight number.
     @return the flight number
     */
    public String getFlight_number() {
        return flight_number;
    }
    /**

     Sets the flight number.
     @param flight_number the flight number to set
     */
    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }
    /**

     Gets the flight IATA code.
     @return the flight IATA code
     */
    public String getFlight_iata() {
        return flight_iata;
    }
    /**

     Sets the flight IATA code.
     @param flight_iata the flight IATA code to set
     */
    public void setFlight_iata(String flight_iata) {
        this.flight_iata = flight_iata;
    }
    /**

     Gets the flight ICAO code.
     @return the flight ICAO code
     */
    public String getFlight_icao() {
        return flight_icao;
    }
    /**

     Sets the flight ICAO code.
     @param flight_icao the flight ICAO code to set
     */
    public void setFlight_icao(String flight_icao) {
        this.flight_icao = flight_icao;
    }
}