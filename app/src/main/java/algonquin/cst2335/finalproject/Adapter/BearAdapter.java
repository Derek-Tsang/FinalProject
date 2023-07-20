package algonquin.cst2335.finalproject.Adapter;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.finalproject.R;

public class BearAdapter extends AppCompatActivity {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_IMAGE_SIZE = "image_size";
    private EditText editImageSize;
    private EditText editImageSize2;
    private Button btnGenerate;
    private Button btnHistory;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bear);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Initialize views
        editImageSize = findViewById(R.id.editImageSize);
        editImageSize2 = findViewById(R.id.editImageSize2);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnHistory = findViewById(R.id.btnHistory);

        // Load previous input from SharedPreferences
        String previousImageSize = sharedPreferences.getString(KEY_IMAGE_SIZE, "");
        editImageSize.setText(previousImageSize);

        // Implement Toast
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageSize = editImageSize.getText().toString();
                Toast.makeText(BearAdapter.this, "Image Size: " + imageSize, Toast.LENGTH_SHORT).show();
                // Save input to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_IMAGE_SIZE, imageSize);
                editor.apply();
            }
        });

        // Other code for Snackbar and AlertDialog remains the same
    }
}
