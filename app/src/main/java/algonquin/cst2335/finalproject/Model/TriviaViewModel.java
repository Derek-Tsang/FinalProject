package algonquin.cst2335.finalproject.Model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.HashMap;
import java.util.List;
import algonquin.cst2335.finalproject.Entities.TriviaQuestion;

/**
 * ViewModel class for managing data related to the Trivia Question.
 */
public class TriviaViewModel extends ViewModel {

    /**
     * LiveData holding the list of TriviaQuestions.
     */
    public MutableLiveData<List<TriviaQuestion>> questions = new MutableLiveData<>();

    /**
     * LiveData holding the currently selected TriviaQuestion.
     */
    public MutableLiveData<TriviaQuestion> selectQuestion = new MutableLiveData< >();

    /**
     * HashMap to store user scores for each question.
     * The key represents the position of the question in the adapter,
     * and the value represents the user's score for that question
     */
    public HashMap<Integer, Integer> userScoresMap = new HashMap<>();

}
