package algonquin.cst2335.finalproject.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.finalproject.databinding.ActivityBearBinding;

public class BearImageGeneratorActivity extends AppCompatActivity {

    ActivityBearBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBearBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}