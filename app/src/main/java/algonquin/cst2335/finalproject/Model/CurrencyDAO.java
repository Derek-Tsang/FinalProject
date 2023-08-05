package algonquin.cst2335.finalproject.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.finalproject.Entities.CurrencyResult;

/**
 * Data Access Object (DAO) interface for handling currency conversion results.
 * This interface defines methods to insert, retrieve, and delete currency conversion results from the database.
 *
 * @version 1.0
 * @since 2023-08-04
 */
@Dao
public interface CurrencyDAO {

    /**
     * Inserts a currency conversion result into the database.
     *
     * @param c The currency conversion result to insert.
     */
    @Insert
    void insertCurrency(CurrencyResult c);

    /**
     * Retrieves all currency conversion results from the database.
     *
     * @return A list of all currency conversion results.
     */
    @Query("Select * from CurrencyResult")
    List<CurrencyResult> getAllCurrency();

    /**
     * Deletes a currency conversion result from the database.
     *
     * @param c The currency conversion result to delete.
     */
    @Delete
    void deleteCurrency(CurrencyResult c);
}