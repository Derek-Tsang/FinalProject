package algonquin.cst2335.finalproject.Model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import algonquin.cst2335.finalproject.Entities.TriviaUser;

@Dao
/**
 * Data Access Object interface for performing database operations on the TriviaUser
 */
public interface TriviaUserDAO {

    /**
     * insert a new TriviaUser into the database.
     *
     * @param triviaUser The TriviaUser object to be inserted.
     */
    @Insert
    void insertUserScores(TriviaUser triviaUser);

    /**
     * Retrieve the top ten TriviaUsers with the highest scores from the database.
     *
     * @return A list of TriviaUser objects representing the top ten users.
     */
    @Query("SELECT DISTINCT * FROM trivia_users ORDER BY scores DESC LIMIT 10")
    List<TriviaUser> getTopTenUser();


}
