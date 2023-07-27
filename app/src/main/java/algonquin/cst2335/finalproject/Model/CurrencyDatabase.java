package algonquin.cst2335.finalproject.Model;

import android.content.Context;

import androidx.room.RoomDatabase;

public abstract class CurrencyDatabase extends RoomDatabase {
    public static CurrencyDatabase getInstance(Context applicationcontext) {
        return null;
    }

    public abstract CurrencyDAO currencyDAO() ;
}
