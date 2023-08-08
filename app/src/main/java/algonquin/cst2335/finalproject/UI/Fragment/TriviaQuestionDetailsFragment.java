package algonquin.cst2335.finalproject.UI.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.Entities.TriviaQuestion;
import algonquin.cst2335.finalproject.R;
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
        List<String> incorrectAnswers = new ArrayList<>(selectQuestion.getAnswers());
        // remove correct answer from answers
        for(int i = 0; i < incorrectAnswers.size(); i++){
            if(incorrectAnswers.get(i).equals(selectQuestion.getCorrectAnswer())){
                incorrectAnswers.remove(i);
            }
        }

        binding.category.setText(getResources().getString(R.string.category)+": \t" + selectQuestion.getCategory());
        binding.type.setText(getResources().getString(R.string.type)+": \t" + selectQuestion.getType());
        binding.difficulty.setText(getResources().getString(R.string.difficulty)+": \t" + selectQuestion.getDifficulty());
        binding.question.setText(getResources().getString(R.string.question)+": \t" + selectQuestion.getQuestion());
        binding.correctAnswer.setText(getResources().getString(R.string.correctAnswer)+": " + selectQuestion.getCorrectAnswer());
        binding.incorrectAnswers.setText(getResources().getString(R.string.incorrectAnswer)+": " + "\n"+
                incorrectAnswers.get(0) + "\n" +
                incorrectAnswers.get(1) + "\n" +
                incorrectAnswers.get(2));

        return binding.getRoot();
    }


}
