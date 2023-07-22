package algonquin.cst2335.finalproject.UI.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import algonquin.cst2335.finalproject.Entities.TriviaQuestion;
import algonquin.cst2335.finalproject.databinding.FragmentQuestionDetailsLayoutBinding;

/**
 * A Fragment class to display the details of a TriviaQuestion.
 */
public class TriviaQuestionDetailsFragment extends Fragment {

    /**
     * Represents the currently selected question
     */
    TriviaQuestion selectQuestion;


    /**
     * Constructor for creating a new instance of the TriviaQuestionDetailsFragment.
     *
     * @param question The TriviaQuestion object to display details for.
     */
    public TriviaQuestionDetailsFragment(TriviaQuestion question){
        selectQuestion = question;
    }


    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        FragmentQuestionDetailsLayoutBinding binding =  FragmentQuestionDetailsLayoutBinding.inflate(inflater, container, false);

        binding.category.setText("Category: \t" + selectQuestion.getCategory());
        binding.type.setText("Type: \t" + selectQuestion.getType());
        binding.difficulty.setText("Difficulty: \t" + selectQuestion.getDifficulty());
        binding.question.setText("Question: \t" + selectQuestion.getQuestion());
        binding.correctAnswer.setText("Correct answer: " + selectQuestion.getCorrectAnswer());
        binding.incorrectAnswers.setText("Incorrect answer: " + "\n"+
                selectQuestion.getIncorrectAnswers().get(0) + "\n" +
                selectQuestion.getIncorrectAnswers().get(1) + "\n" +
                selectQuestion.getIncorrectAnswers().get(2));

        return binding.getRoot();
    }


}
