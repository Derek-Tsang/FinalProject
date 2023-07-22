package algonquin.cst2335.finalproject.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Class representing a Trivia User with a unique identifier, username, and scores.
 */
@Entity(tableName = "trivia_users")
public class TriviaUser {

    /**
     * Unique identifier for the Trivia User.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    /**
     * The username of the Trivia User.
     */
    @ColumnInfo(name = "username")
    protected String username;

    /**
     * The scores of the Trivia User.
     */
    @ColumnInfo(name = "scores")
    protected int scores;

    /**
     * Get the unique identifier of the Trivia User.
     *
     * @return The unique identifier of the Trivia User.
     */
    public int getId(){
        return id;
    }

    /**
     * Set the username of the Trivia User.
     *
     * @param username The username to set for the Trivia User.
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Get the username of the Trivia User.
     *
     * @return The username of the Trivia User.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Set the scores of the Trivia User.
     *
     * @param scores The scores to set for the Trivia User.
     */
    public void setScores(int scores){
        this.scores = scores;
    }

    /**
     * Get the scores of the Trivia User.
     *
     * @return The scores of the Trivia User.
     */
    public int getScores(){return scores;}

}
