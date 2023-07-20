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
import algonquin.cst2335.finalproject.databinding.ActivityQuestionBinding;


public class TriviaQuestionDatabaseActivity extends AppCompatActivity {

    ActivityQuestionBinding binding;
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
            String amount = binding.amount.getText().toString();
            Toast.makeText(getApplicationContext(), "Selected category: " + selectedCategory + ", amount: " + amount, Toast.LENGTH_LONG).show();
            // store category and amount
            SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putInt("categoryNumber",getCategoryNumber(selectedCategory));
            editor.putString("amount", amount);
            editor.apply();

            startActivity(new Intent(this,TriviaQuestionActivity.class));



        });

    }

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

    public void hideToolbar() {
        toolbar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.help){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("How to use")
                    .setMessage("Welcome to the Trivia App!\n\nTo use this app, follow these steps:\n1. Select a category from the dropdown menu.\n2. Enter the number of questions you want to fetch.\n3. Click the ' Get Questions' button.\nThe app will then fetch trivia questions based on your selection.")
                    .create().show();
        }
        return true;
    }


}