package algonquin.cst2335.finalproject.Model;


import androidx.room.Database;
import androidx.room.RoomDatabase;

//@Database(entities = {TriviaQuestion.class}, version=1)
public abstract class TriviaQuestionDatabase extends RoomDatabase {
    public abstract TriviaDAO tqDAO();
}
