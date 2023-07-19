package algonquin.cst2335.finalproject.UI;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.ActivityBearBinding;

public class BearImageGeneratorActivity extends AppCompatActivity {
    ActivityBearBinding binding;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_WIDTH = "width";
    private static final String KEY_HEIGHT = "height";

    private EditText widthEditText;
    private EditText heightEditText;
    private Button generateButton;

    private int width;
    private int height;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBearBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        configureToolbar();

        widthEditText = findViewById(R.id.etImageSize);
        heightEditText = findViewById(R.id.etImageSize);
        generateButton = findViewById(R.id.btnGenerate);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        width = prefs.getInt(KEY_WIDTH, 0);
        height = prefs.getInt(KEY_HEIGHT, 0);

        widthEditText.setText(String.valueOf(width));
        heightEditText.setText(String.valueOf(height));
    }

    generateButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String widthStr = widthEditText.getText().toString();
            String heightStr = heightEditText.getText().toString();

            if (widthStr.isEmpty() || heightStr.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter width and height", Toast.LENGTH_SHORT).show();
                return;
            }

            width = Integer.parseInt(widthStr);
            height = Integer.parseInt(heightStr);

            // Save the values in SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(KEY_WIDTH, width);
            editor.putInt(KEY_HEIGHT, height);
            editor.apply();

            String imageUrl = "https://placebear.com/" + width + "/" + height;

            // Generate the image or save it to the database
            // TODO: Implement the logic for image generation or saving to a database
        }
    }

    private void configureToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Bear Image Generator");
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
                    .setMessage("Add your own descrition!")
                    .setPositiveButton("Got it!", (dialog,cl) -> {

                    })
                    .create().show();
        }
        return true;
    }
}