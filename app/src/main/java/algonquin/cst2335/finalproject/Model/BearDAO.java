package algonquin.cst2335.finalproject.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import algonquin.cst2335.finalproject.Entities.Bear;

/**
 * Data Access Object (DAO) interface for interacting with the Bear entity in the Room database.
 * @author min
 * @version 1.0
 */
@Dao
public interface BearDAO {
    /**
     * Inserts a Bear entity into the database.
     *
     * @param m The Bear object to be inserted.
     * @return The ID of the newly inserted Bear entity.
     */
    @Insert
    long insertImage(Bear m);

    /**
     * Retrieves all Bear entities from the database.
     *
     * @return A List containing all Bear entities in the database.
     */
    @Query ("Select * from Bear")
    List<Bear> getAllImage();

    /**
     * Deletes a Bear entity from the database.
     *
     * @param m The Bear object to be deleted.
     */
    @Delete
    void deleteImage(Bear m);
}

