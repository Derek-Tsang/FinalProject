package algonquin.cst2335.finalproject.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import algonquin.cst2335.finalproject.Adapter.TriviaAdapter;
import algonquin.cst2335.finalproject.Entities.TriviaQuestion;
import algonquin.cst2335.finalproject.Model.TriviaViewModel;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.Utilities.CommonSharedPreference;
import algonquin.cst2335.finalproject.databinding.ActivityQuestionBinding;


/**
 * This activity serves as the entry point for the Trivia Question Database app.
 */
public class TriviaQuestionDatabaseActivity extends AppCompatActivity {

    /**
     * View binding for the activity
     */
    ActivityQuestionBinding binding;


    /**
     * toolbar for currently activity
     */
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        configureToolbar();

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.category_options, R.layout.color_spinner_layout);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.categorySpinner.setAdapter(spinnerAdapter);



        binding.searchBtn.setOnClickListener(view -> {


            String selectedCategory = binding.categorySpinner.getSelectedItem().toString();
            String amountSelect = binding.amount.getText().toString();
            int amount;

            if(amountSelect == null || amountSelect.equals("")){
                amount = 10;
            }else{ amount = Integer.parseInt(amountSelect);}

            if(amount > 50){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Number of Questions must be less than or equal to 50 ")
                        .setPositiveButton("Got it!", (dialog, cl) -> {
                        })
                        .create().show();
            }else{
                Toast.makeText(getApplicationContext(), "Selected category: " + selectedCategory + ", amount: " + amount, Toast.LENGTH_LONG).show();
                // store category and amount
                CommonSharedPreference.setsharedInt(this,"categoryNumber",getCategoryNumber(selectedCategory));
                CommonSharedPreference.setsharedInt(this, "amount", amount);

                startActivity(new Intent(this,TriviaQuestionActivity.class));
            }


        });

    }

    /**
     * Get the category number selected by the user.
     *
     * @param selectedCategory The seletec category
     * @return The category number selected by the user.
     */
    public int getCategoryNumber(String selectedCategory){

        int categoryNumber;

        switch (selectedCategory) {
            case "Sports":
                categoryNumber = 21;
                break;
            case "Geography":
                categoryNumber = 22;
                break;
            case "History":
                categoryNumber = 23;
                break;
            case "Politics":
                categoryNumber = 24;
                break;
            case "Vehicles":
                categoryNumber = 28;
                break;
            case "Animals":
                categoryNumber = 27;
                break;
            case "General Knowledge":
                categoryNumber = 9;
                break;
            case "Science: Computers":
                categoryNumber = 18;
                break;
            case "Entertainment: Video Games":
                categoryNumber = 15;
                break;
            default:
                categoryNumber = 0;
                break;
        }


        return categoryNumber;
    }

    /**
     * Set configuration for toolbar
     */
    private void configureToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Question Generator");
        binding.toolbar.setTitleTextColor(Color.WHITE);
        //display home icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationIcon(R.drawable.ic_home);
        binding.toolbar.setNavigationOnClickListener(click -> {
            finish();
        });

    }

    /**
     * Hide toolbar
     */
    public void hideToolbar() {
        toolbar.setVisibility(View.GONE);
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
                    .setMessage("Welcome to the Trivia App!\n\n" +
                            "To use this app, follow these steps:\n" +
                            "1. Select a category from the dropdown menu.\n" +
                            "2. Enter the number of questions you want to fetch.\n" +
                            "3. Click the ' Get Questions' button." +
                            "\nThe app will then fetch trivia questions based on your selection.")
                    .create().show();
        }
        return true;
    }


}