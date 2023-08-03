package algonquin.cst2335.finalproject.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import algonquin.cst2335.finalproject.Entities.CurrencyResult;

@Database(entities = {CurrencyResult.class}, version=1)
public abstract class CurrencyDatabase extends RoomDatabase {
    public abstract CurrencyDAO cDAO() ;
}
