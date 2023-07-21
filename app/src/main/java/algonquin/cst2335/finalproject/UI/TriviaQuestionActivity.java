package algonquin.cst2335.finalproject.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.Adapter.TriviaAdapter;
import algonquin.cst2335.finalproject.Entities.TriviaQuestion;
import algonquin.cst2335.finalproject.Model.TriviaViewModel;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.UI.Fragment.TriviaQuestionDetailsFragment;
import algonquin.cst2335.finalproject.databinding.ActivityTriviaQuestionBinding;


public class TriviaQuestionActivity extends AppCompatActivity {

    protected ActivityTriviaQuestionBinding binding;
    List<TriviaQuestion> questions = new ArrayList<>();
    TriviaViewModel triviaModel;
    TriviaAdapter triviaAdapter;
    RequestQueue queue = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTriviaQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.triviaToolbar);
        configureToolbar();

        queue = Volley.newRequestQueue(this);

        triviaModel = new ViewModelProvider(this).get(TriviaViewModel.class);
        questions= triviaModel.questions.getValue();


        if (questions == null) {
            triviaModel.questions.postValue(questions = new ArrayList<>());
        }

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String amountSelect = prefs.getString("amount","");

        int amount;
        if(amountSelect == null || amountSelect.equals("")){
            amount = 10;
        }else{ amount = Integer.parseInt(amountSelect);}
        int categoryNumber = prefs.getInt("categoryNumber",0);

        handleServerData(amount,categoryNumber);

        // set adapter for recycle view
        triviaAdapter = new TriviaAdapter(this,questions);
        binding.recycleView.setAdapter(triviaAdapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void configureToolbar() {
        setSupportActionBar(binding.triviaToolbar);
        getSupportActionBar().setTitle("Trivia Question");
        binding.triviaToolbar.setTitleTextColor(Color.WHITE);
        //display home icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        binding.triviaToolbar.setNavigationIcon(R.drawable.ic_home);
        binding.triviaToolbar.setNavigationOnClickListener(click -> {
            finish();
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.trivia_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected( MenuItem item) {

        if(item.getItemId() == R.id.delete){
            Toast.makeText(getApplicationContext(),"You deleted question",Toast.LENGTH_LONG).show();

        }else if(item.getItemId() == R.id.download){
            TextView question = binding.sumbitBtn;
            Snackbar.make(question, "Question is downloading ... ", Snackbar.LENGTH_SHORT).show();


        }else if(item.getItemId() == R.id.help){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("How to use")
                    .setMessage("Add description later")
                    .create().show();
        }

        return true;
    }

    public void handleServerData(int amount,int categoryNumber){


        String url;

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
                            List<String> incorrectAnswersList = new ArrayList<>();
                            for (int j = 0; j < incorrectAnswersArray.length(); j++) {
                                incorrectAnswersList.add(incorrectAnswersArray.getString(j));
                            }
                            triviaQuestion.setIncorrectAnswers(incorrectAnswersList);
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
}
