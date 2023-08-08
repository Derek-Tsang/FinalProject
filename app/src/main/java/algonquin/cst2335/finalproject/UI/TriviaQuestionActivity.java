package algonquin.cst2335.finalproject.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import algonquin.cst2335.finalproject.Adapter.TriviaAdapter;
import algonquin.cst2335.finalproject.Entities.TriviaQuestion;
import algonquin.cst2335.finalproject.Model.TriviaViewModel;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.UI.Fragment.TriviaQuestionDetailsFragment;
import algonquin.cst2335.finalproject.Utilities.CommonSharedPreference;
import algonquin.cst2335.finalproject.databinding.ActivityTriviaQuestionBinding;

/**
 * TriviaQuestionActivity is an activity that displays a list of trivia questions.
 * It uses the TriviaViewModel to fetch the list of questions and displays them using the TriviaAdapter.
 */
public class TriviaQuestionActivity extends AppCompatActivity {

    /**
     * View binding for the activity
     */
    protected ActivityTriviaQuestionBinding binding;

    /**
     * List of trivia questions
     */
    List<TriviaQuestion> questions = new ArrayList<>();

    /**
     * ViewModel to fetch the list of questions
     */
    TriviaViewModel triviaModel;
    /**
     * Adapter to display the trivia questions in a RecyclerView
     */
    TriviaAdapter triviaAdapter;
    /**
     * Request queue for making network requests
     */
    RequestQueue queue = null;

    /**
     * This method is responsible for initializing the activity and setting up its UI components.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTriviaQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.triviaToolbar);
        configureToolbar();

        queue = Volley.newRequestQueue(this);
        triviaModel = new ViewModelProvider(this).get(TriviaViewModel.class);

        // set adapter for recycle view
        triviaAdapter = new TriviaAdapter(this,questions);
        triviaAdapter.setViewModel(triviaModel);
        binding.recycleView.setAdapter(triviaAdapter);


        if (questions == null) {
            triviaModel.questions.postValue(questions = new ArrayList<>());
        }


          int categoryNumber = CommonSharedPreference.getsharedInt(this,"categoryNumber");
          int amount= CommonSharedPreference.getsharedInt(this,"amount");

        // fetch data from server
        handleServerData(amount,categoryNumber);

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        // handle submit button
        binding.sumbitBtn.setOnClickListener((click)->{
            int scores = calculateTotalScores();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.you_achieved)+":    " + scores + "/" + amount)
                    .setMessage(R.string.alert_question).
                    setPositiveButton(R.string.alert_yes,(dialog, cl)->{
                        Intent intent = new Intent(this, TriviaUserScoresActivity.class);
                        intent.putExtra("score",scores);          
                        startActivity(intent);
                    }).
                    setNegativeButton(R.string.alert_no,(dialog, cl)->{


                    }).
                    create().show();
        });
    }

    /**
     * Set configuration for toolbar
     */
    private void configureToolbar() {
        setSupportActionBar(binding.triviaToolbar);
        getSupportActionBar().setTitle(R.string.Trivia_Question);
        binding.triviaToolbar.setTitleTextColor(Color.WHITE);
        //display home icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.triviaToolbar.setNavigationOnClickListener(click -> {
            finish();
        });

    }

    /**
     * Initializes the options menu for the activity.
     *
     * @param menu The options menu in which you place your items.
     * @return true to display the menu, false otherwise.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.trivia_menu, menu);
        return true;
    }


    /**
     * Handles the selection of items from the options menu.
     *
     * @param item The menu item that was selected.
     * @return true to consume the event here, false to allow normal menu processing to proceed.
     */
    public boolean onOptionsItemSelected( MenuItem item) {


        if(item.getItemId() == R.id.triviaQuestionDetail){
            viewQuestionDetails();
        }else if(item.getItemId() == R.id.triviaDelete){

            deleteQuestion();
        }else if(item.getItemId() == R.id.triviaDownload){

           downloadQuestionsToFile();
        }else if(item.getItemId() == R.id.triviaHelp){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.howToUse)
                    .setMessage(R.string.trivia_question_help2)
                            .setPositiveButton(R.string.alert_ok,(dialog, cl)->{})
                    .create().show();
        }

        return true;
    }

    /**
     * Handles the server data received after making a request for trivia questions.
     *
     * @param amount The number of trivia questions received from the server.
     * @param categoryNumber The category number of the trivia questions
     */
    private void handleServerData(int amount,int categoryNumber){


        String url ="";

        if(categoryNumber !=0){
            url = "https://opentdb.com/api.php?amount=" + amount + "&category=" + categoryNumber +"&type=multiple";
        }else{
            url = "https://opentdb.com/api.php?amount=" + amount +"&type=multiple";
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null,
                (response) -> {
                    try {

                        // get the result questions
                        JSONArray results = response.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {

                            JSONObject item = results.getJSONObject(i);
                            TriviaQuestion triviaQuestion = new TriviaQuestion();

                            triviaQuestion.setCategory(item.getString("category"));
                            triviaQuestion.setType(item.getString("type"));
                            triviaQuestion.setDifficulty(item.getString("difficulty"));
                            triviaQuestion.setQuestion(item.getString("question"));
                            triviaQuestion.setCorrectAnswer(item.getString("correct_answer"));

                            JSONArray incorrectAnswersArray = item.getJSONArray("incorrect_answers");
                            List<String> answers = new ArrayList<>();
                            for (int j = 0; j < incorrectAnswersArray.length(); j++) {
                                answers.add(incorrectAnswersArray.getString(j));
                            }
                            answers.add(item.getString("correct_answer"));

                            Collections.shuffle(answers);
                            triviaQuestion.setAnswers(answers);
                            questions.add(triviaQuestion);
                            triviaAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                },
                (error) -> { });
        queue.add(request);

    }

    /**
     * Download a set of trivia questions
     */
    private void downloadQuestionsToFile() {

        // After fetching the questions, convert them to a string
        StringBuilder questionString = new StringBuilder();
        for (int i = 0; i < questions.size(); i++) {
            TriviaQuestion question = questions.get(i);

            // shuffle the order of list anwsers
            List<String> answers = new ArrayList<>(question.getAnswers());

            questionString.append((i+1) + "." + question.getQuestion()).append("\n");
            questionString.append("A." + answers.get(0)).append("\n");
            questionString.append("B." + answers.get(1)).append("\n");
            questionString.append("C." + answers.get(2)).append("\n");
            questionString.append("D." + answers.get(3)).append("\n");
            questionString.append("Correct Answer: ").append(question.getCorrectAnswer()).append("\n");
            questionString.append("\n");
        }

        // Write the data to a file
        try {
            String filename = "trivia_questions.txt";
            FileOutputStream fos = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(questionString.toString().getBytes());
            fos.close();
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.Trivia_snacker_message) + filename, Snackbar.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Delete the selected trivia question
     */
    private void deleteQuestion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TriviaQuestion deletedQuestion = triviaModel.selectQuestion.getValue();
        if(deletedQuestion != null ){
            builder.setTitle(R.string.alert_delete_question).
                    setNegativeButton(R.string.alert_no,(dialog, cl)->{}).
                    setPositiveButton(R.string.alert_yes,(dialog, cl)->{
                        int position = triviaAdapter.getPosition();
                        questions.remove(deletedQuestion);
                        triviaAdapter.notifyItemRemoved(position);

                        TextView questionText = findViewById(R.id.question);
                        Snackbar.make(questionText, getResources().getString(R.string.snacker_question_remove_suc) + (position+1), Snackbar.LENGTH_LONG).
                                setAction(R.string.UNDO, (clk) -> {
                                        questions.add(position, deletedQuestion);
                                        triviaAdapter.notifyItemInserted(position);


                                }).show();
                    }).
                    create().
                    show();

        }
    }

    /**
     * View the details of selected trivia question
     */
    private void viewQuestionDetails(){

        TriviaQuestion viewQuestion = triviaModel.selectQuestion.getValue();
        if(viewQuestion != null ){
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();
            TriviaQuestionDetailsFragment frag = new TriviaQuestionDetailsFragment(viewQuestion);
            tx.add(R.id.triviaFragment,frag);
            tx.addToBackStack("");
            tx.commit();
        }

    }

    /**
     * Calculates the total scores for all the trivia questions.
     *
     * @return The total scores obtained by the user for all the trivia questions.
     */
    private int calculateTotalScores() {
        int totalScore = 0;
        for (int score : triviaModel.userScoresMap.values()) {
            totalScore += score;
        }
        return totalScore;
    }
}
