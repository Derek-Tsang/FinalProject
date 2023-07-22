package algonquin.cst2335.finalproject.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import algonquin.cst2335.finalproject.Entities.TriviaQuestion;
import algonquin.cst2335.finalproject.Model.TriviaViewModel;
import algonquin.cst2335.finalproject.R;

/**
 * RecyclerView adapter class for displaying a list of TriviaQuestion items.
 * This adapter binds TriviaQuestion data to the corresponding views in the RecyclerView.
 */
public class TriviaAdapter extends RecyclerView.Adapter<TriviaAdapter.TriviaViewHolder>{

    /**
     *  List of TriviaQuestion
     */
    public List<TriviaQuestion> questions;
    /**
     * Application context
     */
    private Context context;
    /**
     * The current position in the adapter
     */
    private  int pos;
    /**
     * Represents ViewModel for managing TriviaQuestion data
     */
    private TriviaViewModel triviaModel;


    /**
     * Sets the ViewModel for managing Trivia data.
     *
     * @param triviaModel The TriviaViewModel to be set.
     */
    public void setViewModel(TriviaViewModel triviaModel) {
        this.triviaModel = triviaModel;
    }


    /**
     * Gets the current position in the adapter.
     *
     * @return The current position in the adapter.
     */
    public int getPosition(){
        return this.pos;
    }

    /**
     * Constructor for the TriviaAdapter.
     *
     * @param applicationcontext The application context.
     * @param questions List of TriviaQuestion items to be displayed.
     */
    public TriviaAdapter(Context applicationcontext, List<TriviaQuestion> questions){
        this.context = applicationcontext;
        this.questions = questions;
    }


    /**
     * Creates a new ViewHolder by inflating the layout for the Trivia question item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new instance of TriviaViewHolder.
     */
    @NonNull
    @Override
    public TriviaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View itemView= LayoutInflater.from(context).inflate(R.layout.trivia_item_layout,parent,false);
        return new TriviaViewHolder(itemView);

    }

    /**
     * Binds the Trivia data to the corresponding views in the ViewHolder.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
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

        // Set the OnCheckedChangeListener for the RadioGroup
        holder.answerGroup.setOnCheckedChangeListener((group, checkedId) -> {


                // Get the selected RadioButton
                RadioButton selectedBtn = group.findViewById(checkedId);
                if (selectedBtn != null) {

                    String selectedAnswer = selectedBtn.getText().toString();
                    // Check if the correct question selected
                    if (selectedAnswer.equalsIgnoreCase(triviaQuestion.getCorrectAnswer())) {
                        triviaModel.userScoresMap.put(position, 1);
                    } else {
                        triviaModel.userScoresMap.put(position, 0);
                    }
                }

        });

    }

    /**
     * Returns the total number of TriviaQuestion items in the adapter.
     *
     * @return The number of TriviaQuestion items in the adapter.
     */
    @Override
    public int getItemCount() {
        return questions.size();
    }

    /**
     * ViewHolder class for displaying a single Trivia question item in a RecyclerView.
     * This class holds references to the various views representing a Trivia question, such as the question text and answer options.
     */
    class TriviaViewHolder extends RecyclerView.ViewHolder {
        /**
         * Represents a question for Trivia question
         */
        TextView question;

        /**
         * Represents an anserGroup
         */
        RadioGroup answerGroup;
        /**
         * Represents an answer of trivia question
         */
        RadioButton answerA;

        /**
         * Represents an answer of trivia question
         */
        RadioButton answerB;

        /**
         * Represents an answer of trivia question
         */
        RadioButton answerC;

        /**
         * Represents an answer of trivia question
         */
        RadioButton answerD;

        /**
         * Constructor for the TriviaViewHolder.
         *
         * @param itemView The View representing a single Trivia question item.
         */
        public TriviaViewHolder(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            answerGroup = itemView.findViewById(R.id.answerGroup);
            answerA = itemView.findViewById(R.id.answer_A);
            answerB = itemView.findViewById(R.id.answer_B);
            answerC = itemView.findViewById(R.id.answer_C);
            answerD = itemView.findViewById(R.id.answer_D);


            itemView.setOnClickListener( click -> {
                int position = getAbsoluteAdapterPosition();
                TriviaQuestion selectQuestion = questions.get(pos=position);
                triviaModel.selectQuestion.postValue(selectQuestion);
            });

        }
    }
}
