package algonquin.cst2335.finalproject.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;



import algonquin.cst2335.finalproject.Entities.TriviaQuestion;

@Dao
public interface TriviaDAO {

    @Insert
    public long insertQuestion(TriviaQuestion question);

//    @Query("Select * from TriviaQuestion")
//    public List<TriviaQuestion> getAllQuestions();

    @Delete
    void deleteQuestion(TriviaQuestion m);

}
