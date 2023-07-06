package algonquin.cst2335.finalproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;
/**
 *  The MainActivity class is the main activity of the Final Project application.
 *  It provides the main user interface for the application and handles user interactions.
 */
public class MainActivity extends AppCompatActivity {

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
}