package algonquin.cst2335.finalproject.Model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import algonquin.cst2335.finalproject.Entities.Airport;
import algonquin.cst2335.finalproject.Entities.Flight;
import algonquin.cst2335.finalproject.Entities.FlightInfo;
/**
 *  The FlightTrackerDatabase class represents the Room database for the Flight Tracker application.
 *  It extends the RoomDatabase class and provides access to the FlightDAO.
 *  @version 1.0
 *  @since 2023-07-06
 */
@Database(entities ={Flight.class, Airport.class}, version = 1)
public abstract class FlightTrackerDatabase extends RoomDatabase {
    /**
     *  Retrieves the FlightDAO instance associated with the database.
     *  @return The FlightDAO instance.
     */
    public abstract FlightDAO flightDAO();
}
