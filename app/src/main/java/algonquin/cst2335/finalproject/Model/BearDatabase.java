package algonquin.cst2335.finalproject.Model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import algonquin.cst2335.finalproject.Entities.Bear;

@Database(entities = {Bear.class}, version = 1)
public abstract class BearDatabase extends RoomDatabase {
    // Define the abstract method to get the BearDAO instance
    public abstract BearDAO bearDAO();
}

