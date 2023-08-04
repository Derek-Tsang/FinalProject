package algonquin.cst2335.finalproject.Model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import algonquin.cst2335.finalproject.Entities.CurrencyResult;

/**
 * Represents the Room database for storing currency conversion results.
 * This class defines the database configuration and provides access to the CurrencyDAO.
 *
 * @version 1.0
 * @since 2023-08-04
 */
@Database(entities = {CurrencyResult.class}, version=1)
public abstract class CurrencyDatabase extends RoomDatabase {

    /**
     * Returns the CurrencyDAO for accessing currency conversion result data in the database.
     *
     * @return The CurrencyDAO instance.
     */
    public abstract CurrencyDAO cDAO() ;
}
