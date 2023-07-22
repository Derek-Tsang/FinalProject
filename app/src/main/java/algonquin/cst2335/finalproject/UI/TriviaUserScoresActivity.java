package algonquin.cst2335.finalproject.UI;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import algonquin.cst2335.finalproject.Adapter.TriviaUserAdapter;
import algonquin.cst2335.finalproject.Entities.TriviaUser;
import algonquin.cst2335.finalproject.Model.TriviaUserDAO;
import algonquin.cst2335.finalproject.Model.TriviaUserDatabase;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.ActivityTriviaUserScoresBinding;


/**
 * TriviaUserScoresActivity displays the top ten user scores in a RecyclerView.
 * It fetches user scores from a local database using TriviaUserDAO
 * and displays them in the RecyclerView using TriviaUserAdapter.
 */
public class TriviaUserScoresActivity extends AppCompatActivity {

    /**
     * View binding for the activity layout
     */
    protected ActivityTriviaUserScoresBinding binding;

    /**
     * DAO (Data Access Object) for accessing the local database
     */
    protected TriviaUserDAO triviaUserDAO;

    /**
     * List to store the top ten user scores fetched from the database
     */
    protected List<TriviaUser> users ;

    /**
     * Adapter to bind the user scores to the RecyclerView
     */
    protected TriviaUserAdapter triviaUserAdapter;

    /**
     * Executor to perform database operations on a separate thread
     */
    private Executor thread;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTriviaUserScoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        configureToolbar();
        if(getIntent()!=null) {
            score = getIntent().getIntExtra("score",0);
            binding.editUserScores.setText("Your Score is "+ score+"");
        }

        users = new ArrayList<>();

        binding.loginBtn.setOnClickListener((click)->{

            String username = binding.editUsername.getText().toString();



            if(("").equals(username)){

                Snackbar.make(binding.getRoot(), "username cannot be empty", Snackbar.LENGTH_SHORT).show();
            }else{

                // connect to database
                TriviaUserDatabase db = Room.databaseBuilder(getApplicationContext(),TriviaUserDatabase.class, "database-name").
                        fallbackToDestructiveMigration().
                        build();

                triviaUserDAO = db.userDAO();
                binding.headerId.setVisibility(View.VISIBLE);
                binding.headerName.setVisibility(View.VISIBLE);
                binding.headerScores.setVisibility(View.VISIBLE);

                TriviaUser user = new TriviaUser();
                user.setUsername(username);
                user.setScores(score);

                 // thread safe
                thread = Executors.newSingleThreadExecutor();
                thread.execute(()->{
                    // insert user scores into database
                    triviaUserDAO.insertUserScores(user);
                    // Fetch the top ten users from the database
                    List<TriviaUser>  topTenUsers = triviaUserDAO.getTopTenUser();
                    runOnUiThread(() -> {
                        users.clear();
                        users.addAll(topTenUsers);
                        triviaUserAdapter.notifyDataSetChanged();
                    });

                });

            }

        });

        // Set the adapter and layout manager outside the click listener
        triviaUserAdapter = new TriviaUserAdapter(this, users);
        binding.scoresRecycleView.setAdapter(triviaUserAdapter);
        binding.scoresRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Set configuration for toolbar
     */
    private void configureToolbar() {
        setSupportActionBar(binding.triviaScoresToolbar);
        getSupportActionBar().setTitle("User Scores");
        binding.triviaScoresToolbar.setTitleTextColor(Color.WHITE);
        //display home icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.triviaScoresToolbar.setNavigationIcon(R.drawable.ic_home);
        binding.triviaScoresToolbar.setNavigationOnClickListener(click -> {
            finish();
        });

    }

    /**
     * Initializes the options menu for the activity.
     *
     * @param menu The options menu in which you place your items.
     * @return true to display the menu, false otherwise.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /**
     * Handles the selection of items from the options menu.
     *
     * @param item The menu item that was selected.
     * @return true to consume the event here, false to allow normal menu processing to proceed.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.help){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("How to use")
                    .setMessage("Enter your username and scores to view Top ten user scores")
                    .create().show();
        }
        return true;
    }
}
