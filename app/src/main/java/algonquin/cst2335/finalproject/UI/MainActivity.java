package algonquin.cst2335.finalproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;
/**
 *  The MainActivity class is the main activity of the Final Project application.
 *  It provides the main user interface for the application and handles user interactions.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The main activity binding
     */
    ActivityMainBinding binding;
    /**
     * Called when the activity is starting.
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        configureToolbar();
        SharedPreferences prefs = getSharedPreferences("FinalProjectData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        binding.cvFlight.setOnClickListener(clk->{
            startActivity( new Intent(this, FlightTrackerActivity.class));
        });
        binding.cvCurrency.setOnClickListener(clk->{
            startActivity( new Intent(this, CurrencyConverterActivity.class));
        });
        binding.cvQuestion.setOnClickListener(clk->{
            startActivity( new Intent(this, TriviaQuestionDatabaseActivity.class));
        });
        binding.cvImgGenerator.setOnClickListener(clk->{
            startActivity( new Intent(this, BearImageGeneratorActivity.class));
        });

    }

    /**
     * Configures the app's toolbar.
     */
    private void configureToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
    }
    /**
     * Called to create the options menu.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed; if you return false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    /**
     * Called when a menu item is selected.
     *
     * @param item The menu item that was selected.
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.flight){
            startActivity( new Intent(this, FlightTrackerActivity.class));
        }else if(item.getItemId() == R.id.currency){
            startActivity( new Intent(this, CurrencyConverterActivity.class));
        }else if(item.getItemId() == R.id.trivia){
            startActivity( new Intent(this, TriviaQuestionDatabaseActivity.class));
        }else{
            startActivity( new Intent(this, BearImageGeneratorActivity.class));
        }
        return true;
    }
}