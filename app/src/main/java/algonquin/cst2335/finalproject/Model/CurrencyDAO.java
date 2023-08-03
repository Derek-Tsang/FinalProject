package algonquin.cst2335.finalproject.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.finalproject.Entities.CurrencyResult;

@Dao
public interface CurrencyDAO {

    @Insert
    void insertCurrency(CurrencyResult c);

    @Query("Select * from CurrencyResult")
    List<CurrencyResult> getAllCurrency();

    @Delete
    void deleteCurrency(CurrencyResult c);
}