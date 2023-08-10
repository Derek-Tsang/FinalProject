package algonquin.cst2335.finalproject.Entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
/**
 *  Represents an airport entity.
 */
@Entity(tableName = "Airport")
public class Airport {

    /**
     * The unique identifier for the airport.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "airportId")
    public long airportId;

    /**
     * The name of the airport.
     */
    @ColumnInfo(name = "airport")
    private String airport;

    /**
     * The timezone of the airport.
     */
    @ColumnInfo(name = "timezone")
    private String timezone;

    /**
     * The IATA code (International Air Transport Association) of the airport.
     */
    @ColumnInfo(name = "iata")
    private String iata;

    /**
     * The ICAO code (International Civil Aviation Organization) of the airport.
     */
    @ColumnInfo(name = "icao")
    private String icao;

    /**
     * The terminal information of the airport.
     */
    @ColumnInfo(name = "terminal")
    private String terminal;

    /**
     * The gate information of the airport.
     */
    @ColumnInfo(name = "gate")
    private String gate;

    /**
     * The baggage information of the airport.
     */
    @ColumnInfo(name = "baggage")
    private String baggage;

    /**
     * The delay status of the airport.
     */
    @ColumnInfo(name = "delay")
    private String delay;

    /**
     * The scheduled departure/arrival time of the airport.
     */
    @ColumnInfo(name = "scheduled")
    private String scheduled;

    /**
     * The estimated departure/arrival time of the airport.
     */
    @ColumnInfo(name = "estimated")
    private String estimated;

    /**
     * The actual departure/arrival time of the airport.
     */
    @ColumnInfo(name = "actual")
    private String actual;

    /**
     * The estimated runway time of the airport.
     */
    @ColumnInfo(name = "estimatedRunway")
    private String estimatedRunway;

    /**
     * The actual runway time of the airport.
     */
    @ColumnInfo(name = "actualRunway")
    private String actualRunway;
    /**
     * Gets the airport ID.
     *
     * @return The airport ID.
     */
    public long getAirportId() {
        return airportId;
    }

    /**
     * Sets the airport ID.
     *
     * @param airportId The airport ID to set.
     */
    public void setAirportId(long airportId) {
        this.airportId = airportId;
    }

    /**
     * Gets the airport name.
     *
     * @return The airport name.
     */
    public String getAirport() {
        return airport;
    }

    /**
     * Sets the airport name.
     *
     * @param airport The airport name to set.
     */
    public void setAirport(String airport) {
        this.airport = airport;
    }

    /**
     * Gets the timezone.
     *
     * @return The timezone.
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Sets the timezone.
     *
     * @param timezone The timezone to set.
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * Gets the IATA code.
     *
     * @return The IATA code.
     */
    public String getIata() {
        return iata;
    }

    /**
     * Sets the IATA code.
     *
     * @param iata The IATA code to set.
     */
    public void setIata(String iata) {
        this.iata = iata;
    }

    /**
     * Gets the ICAO code.
     *
     * @return The ICAO code.
     */
    public String getIcao() {
        return icao;
    }

    /**
     * Sets the ICAO code.
     *
     * @param icao The ICAO code to set.
     */
    public void setIcao(String icao) {
        this.icao = icao;
    }

    /**
     * Gets the terminal.
     *
     * @return The terminal.
     */
    public String getTerminal() {
        return terminal;
    }

    /**
     * Sets the terminal.
     *
     * @param terminal The terminal to set.
     */
    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    /**
     * Gets the gate.
     *
     * @return The gate.
     */
    public String getGate() {
        return gate;
    }

    /**
     * Sets the gate.
     *
     * @param gate The gate to set.
     */
    public void setGate(String gate) {
        this.gate = gate;
    }

    /**
     * Gets the baggage information.
     *
     * @return The baggage information.
     */
    public String getBaggage() {
        return baggage;
    }

    /**
     * Sets the baggage information.
     *
     * @param baggage The baggage information to set.
     */
    public void setBaggage(String baggage) {
        this.baggage = baggage;
    }

    /**
     * Gets the delay information.
     *
     * @return The delay information.
     */
    public String getDelay() {
        return delay;
    }

    /**
     * Sets the delay information.
     *
     * @param delay The delay information to set.
     */
    public void setDelay(String delay) {
        this.delay = delay;
    }

    /**
     * Gets the scheduled time.
     *
     * @return The scheduled time.
     */
    public String getScheduled() {
        return scheduled;
    }

    /**
     * Sets the scheduled time.
     *
     * @param scheduled The scheduled time to set.
     */
    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    /**
     * Gets the estimated time.
     *
     * @return The estimated time.
     */
    public String getEstimated() {
        return estimated;
    }

    /**
     * Sets the estimated time.
     *
     * @param estimated The estimated time to set.
     */
    public void setEstimated(String estimated) {
        this.estimated = estimated;
    }

    /**
     * Gets the actual time.
     *
     * @return The actual time.
     */
    public String getActual() {
        return actual;
    }

    /**
     * Sets the actual time.
     *
     * @param actual The actual time to set.
     */
    public void setActual(String actual) {
        this.actual = actual;
    }

    /**
     * Gets the estimated runway time.
     *
     * @return The estimated runway time.
     */
    public String getEstimatedRunway() {
        return estimatedRunway;
    }

    /**
     * Sets the estimated runway time.
     *
     * @param estimatedRunway The estimated runway time to set.
     */
    public void setEstimatedRunway(String estimatedRunway) {
        this.estimatedRunway = estimatedRunway;
    }

    /**
     * Gets the actual runway time.
     *
     * @return The actual runway time.
     */
    public String getActualRunway() {
        return actualRunway;
    }

    /**
     * Sets the actual runway time.
     *
     * @param actualRunway The actual runway time to set.
     */
    public void setActualRunway(String actualRunway) {
        this.actualRunway = actualRunway;
    }
}
