package algonquin.cst2335.finalproject.Model;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import algonquin.cst2335.finalproject.Entities.TriviaUser;

/**
 * RoomDatabase class for managing the TriviaUser database.
 */
@Database(entities = {TriviaUser.class}, version=1)
public abstract class TriviaUserDatabase extends RoomDatabase {

    /**
     * Get the TriviaUserDAO instance for performing database operations.
     *
     * @return The TriviaUserDAO instance.
     */
    public abstract TriviaUserDAO userDAO();
}
