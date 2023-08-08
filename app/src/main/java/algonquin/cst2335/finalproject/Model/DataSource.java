package algonquin.cst2335.finalproject.Model;

import android.content.Context;

import androidx.room.Room;
/**
 *  The DataSource class is responsible for managing the data source and database for the FlightTracker application.
 *  It provides methods to access and manipulate the FlightTrackerDatabase.
 */
public class DataSource {

    private Context mCtx;
    private static DataSource mInstance;

    private FlightTrackerDatabase flgithDB;
    private BearDatabase bearDB;
    private TriviaUserDatabase triviaUserDB;
    private CurrencyDatabase currencyDB;

    /**
     *  Constructs a new instance of the DataSource class.
     *  @param mCtx The application context.
     */
    private DataSource(Context mCtx) {
        this.mCtx = mCtx;
        //creating the app database with Room database builder
        flgithDB = Room.databaseBuilder(mCtx, FlightTrackerDatabase.class, "FlightTrackerDatabase").build();
        bearDB = Room.databaseBuilder(mCtx, BearDatabase.class, "BearDatabase").build();
        triviaUserDB = Room.databaseBuilder(mCtx, TriviaUserDatabase.class, "TriviaUserDatabase").build();
        currencyDB = Room.databaseBuilder(mCtx, CurrencyDatabase.class, "CurrencyDatabase").build();
    }
    /**
     *  Retrieves the singleton instance of the DataSource class.
     *  @param mCtx The application context.
     *  @return The singleton instance of the DataSource class.
     */
    public static synchronized DataSource getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DataSource(mCtx);
        }
        return mInstance;
    }
    /**
     *  Retrieves the FlightTrackerDatabase instance.
     *  @return The FlightTrackerDatabase instance.
     */
    public FlightTrackerDatabase getFlgithDB() {
        return flgithDB;
    }
    public BearDatabase getBearDB() {
        return bearDB;
    }
    public TriviaUserDatabase getTriviaUserDB() {
        return triviaUserDB;
    }
    public CurrencyDatabase getCurrencyDB() {
        return currencyDB;
    }



}
