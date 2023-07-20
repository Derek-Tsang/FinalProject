package algonquin.cst2335.finalproject.Adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import algonquin.cst2335.finalproject.Entities.TriviaQuestion;
import algonquin.cst2335.finalproject.Model.TriviaViewModel;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.UI.FlightTrackerActivity;
import algonquin.cst2335.finalproject.UI.Fragment.TriviaQuestionDetailsFragment;
import algonquin.cst2335.finalproject.UI.TriviaQuestionActivity;

public class TriviaAdapter extends RecyclerView.Adapter<TriviaAdapter.TriviaViewHolder>{

    public List<TriviaQuestion> questions;
    private Context context;

    private TriviaViewModel triviaModel;

    public void setViewModel(TriviaViewModel triviaModel) {
        this.triviaModel = triviaModel;
    }

    public TriviaAdapter(Context applicationcontext, List<TriviaQuestion> questions){
        this.context = applicationcontext;
        this.questions = questions;
    }


    @NonNull
    @Override
    public TriviaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View itemView= LayoutInflater.from(context).inflate(R.layout.trivia_item_layout,parent,false);
        return new TriviaViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull TriviaAdapter.TriviaViewHolder holder, int position) {

        TriviaQuestion triviaQuestion = questions.get(position);
        holder.question.setText((position + 1) + ". " + triviaQuestion.getQuestion());

        // shuffle the order of list
        List<String> answers = new ArrayList<>(triviaQuestion.getIncorrectAnswers());
        answers.add(triviaQuestion.getCorrectAnswer());
        Collections.shuffle(answers);
        holder.answerA.setText(answers.get(0));
        holder.answerB.setText(answers.get(1));
        holder.answerC.setText(answers.get(2));
        holder.answerD.setText(answers.get(3));



    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class TriviaViewHolder extends RecyclerView.ViewHolder {
        TextView question;

        RadioGroup answerGroup;
        RadioButton answerA;
        RadioButton answerB;
        RadioButton answerC;
        RadioButton answerD;

        public TriviaViewHolder(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            answerGroup = itemView.findViewById(R.id.answerGroup);
            answerA = itemView.findViewById(R.id.answer_A);
            answerB = itemView.findViewById(R.id.answer_B);
            answerC = itemView.findViewById(R.id.answer_C);
            answerD = itemView.findViewById(R.id.answer_D);

//            itemView.setOnClickListener( click -> {
//
//
//                int position = getAbsoluteAdapterPosition();
//                TriviaQuestion selectQuestion = questions.get(position);
//                if(selectQuestion != null){
//                    TriviaQuestionDetailsFragment fragment = new TriviaQuestionDetailsFragment(selectQuestion);
//
//                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.triviaFragment, fragment);
//                    fragmentTransaction.addToBackStack("");
//                    fragmentTransaction.commit();
//                }
//
//
//            });

        }
    }
}
