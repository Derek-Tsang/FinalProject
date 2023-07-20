package algonquin.cst2335.finalproject.UI.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.finalproject.Entities.TriviaQuestion;
import algonquin.cst2335.finalproject.databinding.FragmentQuestionDetailsLayoutBinding;

public class TriviaQuestionDetailsFragment extends Fragment {

    TriviaQuestion selectQuestion;
    private DialogInterface.OnDismissListener onDismissListener;

    public TriviaQuestionDetailsFragment(TriviaQuestion question){

        selectQuestion = question;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        FragmentQuestionDetailsLayoutBinding binding =  FragmentQuestionDetailsLayoutBinding.inflate(inflater, container, false);

        binding.category.setText("Category: " + selectQuestion.getCategory());
        binding.type.setText("Type: " + selectQuestion.getType());
        binding.difficulty.setText("Difficulty: " + selectQuestion.getDifficulty());
        binding.question.setText("Question: " + selectQuestion.getQuestion());
        binding.correctAnswer.setText("Correct Answer: " + "\n"+ selectQuestion.getCorrectAnswer());
        binding.incorrectAnswers.setText("Incorrect Answer: " + "\n"+
                selectQuestion.getIncorrectAnswers().get(0) + "\n" +
                selectQuestion.getIncorrectAnswers().get(1) + "\n" +
                selectQuestion.getIncorrectAnswers().get(2));


        return binding.getRoot();
    }


}
