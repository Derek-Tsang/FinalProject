package algonquin.cst2335.finalproject.UI;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.Adapter.BearAdapter;
import algonquin.cst2335.finalproject.Entities.Bear;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.UI.Fragment.BearFragment;
import algonquin.cst2335.finalproject.UI.Fragment.FlightDetailFragment;
import algonquin.cst2335.finalproject.databinding.ActivityBearBinding;
import kotlin.reflect.KClassesImplKt;

public class BearImageGeneratorActivity extends AppCompatActivity {

    ActivityBearBinding binding;
    ArrayList<Bear> bears = new ArrayList<>();
    BearAdapter adapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBearBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        configureToolbar();

        binding.btnGenerate.setOnClickListener(click -> {
            BearFragment bearFragment = new BearFragment(getApplicationContext());
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentDetailBear,bearFragment).addToBackStack("") .commit();
            bears.add(new Bear());
            adapter.notifyDataSetChanged();
        });
        adapter = new BearAdapter(this,bears);
        binding.recyclerviewSavedImages.setAdapter(adapter);
        binding.recyclerviewSavedImages.setLayoutManager(new LinearLayoutManager(this));
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