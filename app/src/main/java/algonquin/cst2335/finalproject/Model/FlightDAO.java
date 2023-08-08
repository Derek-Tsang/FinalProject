package algonquin.cst2335.finalproject.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;

import java.util.List;

import algonquin.cst2335.finalproject.Entities.Airport;
import algonquin.cst2335.finalproject.Entities.Flight;
import algonquin.cst2335.finalproject.Entities.FlightInfo;

/**
 *  The FlightDAO interface provides data access methods for interacting with the Flight, Airport, and FlightInfo entities in the database.
 */
@Dao
public interface FlightDAO {
    /**
     *  Adds a Flight entity to the database.
     *  @param m The Flight entity to be added.
     *  @return The ID of the newly added Flight.
     */
    @Insert
    long addFlight(Flight m);

    /**
     *  Adds an Airport entity to the database.
     *  @param airport The Airport entity to be added.
     *  @return The ID of the newly added Airport.
     */
    @Insert
    long addAirport(Airport airport);

    /**
     *  Retrieves a list of FlightInfo entities with associated Airport information from the database.
     *  @return A list of FlightInfo entities with associated Airport information.
     *  */
    @Query("SELECT Flight.*, dep.*, arr.* FROM Flight " +
            "INNER JOIN Airport AS dep ON Flight.departureId = dep.airportId " +
            "INNER JOIN Airport AS arr ON Flight.arrivalId = arr.airportId ")
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    List<FlightInfo> getFlightsWithAirports();

    /**
     *  Retrieves a list of FlightInfo entities with associated Airport information from the database that match the specified keyword.
     *  @param keyword The keyword to search for in Flight, Airport, and FlightInfo entities.
     *  @return A list of FlightInfo entities with associated Airport information that match the specified keyword.
     */
    @Query("SELECT Flight.*, dep.*, arr.* FROM Flight " +
            "INNER JOIN Airport AS dep ON Flight.departureId = dep.airportId " +
            "INNER JOIN Airport AS arr ON Flight.arrivalId = arr.airportId " +
            "where Flight.flight_date like '%' || :keyword || '%' or " +
            "Flight.airline_name like '%' || :keyword || '%' or " +
            "Flight.flight_number like '%' || :keyword || '%' or " +
            "Flight.flight_iata like '%' || :keyword || '%' or " +
            "dep.airport like '%' || :keyword || '%' or " +
            "dep.iata like '%' || :keyword || '%' or " +
            "arr.airport like '%' || :keyword || '%' or " +
            "arr.iata like '%' || :keyword || '%'")
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    List<FlightInfo> getFlightsWithAirports(String keyword);

    /**
     *  Deletes a Flight entity from the database.
     *  @param flight The Flight entity to be deleted.
     */
    @Delete
    void deleteFlight(Flight flight);


}
