package algonquin.cst2335.finalproject.Model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import algonquin.cst2335.finalproject.Entities.Bear;

/**
 * Represents the Room database for managing Bear entities.
 * @author min
 * @version 1.0
 */
@Database(entities = {Bear.class}, version = 1)
public abstract class BearDatabase extends RoomDatabase {
    /**
     * Abstract method to get the BearDAO instance for database interactions.
     * Define the abstract method to get the BearDAO instance
     * @return An instance of BearDAO for accessing database operations related to Bear entities.
     */
    public abstract BearDAO bearDAO();
}