package algonquin.cst2335.finalproject.Entities;

import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.ArrayList;


/**
 *  Represents a flight information object.
 */
public class FlightInfo implements Serializable {
    /**
     *The embedded flight object.
     */
    @Embedded
    public Flight flight;

    /**
     * The departure airport related to the flight.
     */
    @Relation(parentColumn = "departureId", entityColumn = "airportId", entity = Airport.class)
    public Airport departureAirport;

    /**
     * The arrival airport related to the flight.
     */
    @Relation(parentColumn = "arrivalId", entityColumn = "airportId", entity = Airport.class)
    public Airport arrivalAirport;
    /**
     Retrieves the flight object.
     @return The flight object.
     */
    public Flight getFlight() {
        return flight;
    }
    /**

     Sets the flight object.
     @param flight The flight object to set.
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    /**

     Retrieves the departure airport.
     @return The departure airport.
     */
    public Airport getDepartureAirport() {
        return departureAirport;
    }
    /**

     Sets the departure airport.
     @param departureAirport The departure airport to set.
     */
    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }
    /**

     Retrieves the arrival airport.
     @return The arrival airport.
     */
    public Airport getArrivalAirport() {
        return arrivalAirport;
    }
    /**

     Sets the arrival airport.
     @param arrivalAirport The arrival airport to set.
     */
    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }
}