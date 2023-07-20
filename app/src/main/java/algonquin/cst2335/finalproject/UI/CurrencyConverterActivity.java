package algonquin.cst2335.finalproject.UI;

import android.app.AlertDialog;
import android.content.Context;
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

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.Utilities.CommonSharedPreference;
import algonquin.cst2335.finalproject.databinding.ActivityCurrencyBinding;

public class CurrencyConverterActivity extends AppCompatActivity {

    ActivityCurrencyBinding binding;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrencyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        configureToolbar();

        binding.amountFrom.setText(CommonSharedPreference.getsharedText(this, "amount"));

        String[] from = {"USD", "RMB"};
        ArrayAdapter adapterFrom = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, from);
        binding.currenciesSpinnerFrom.setAdapter(adapterFrom);

        String[] to = {"CAD", "RMB"};
        ArrayAdapter adapterTo = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, to);
        binding.currenciesSpinnerTo.setAdapter(adapterTo);


        binding.runConversion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if("".equals(binding.amountFrom.getText().toString()) && "".equals(binding.amountTo.getText().toString())){
                    Toast.makeText(getApplicationContext(),"please enter amount",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!"".equals(binding.amountFrom.getText().toString())  &&  !"".equals(binding.amountTo.getText().toString())){
                    Snackbar.make(binding.amountFrom,"you can not enter both amounts!",Snackbar.LENGTH_LONG).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(CurrencyConverterActivity.this );
                builder.setMessage("Do you want to convert?").setTitle("Question: ").setPositiveButton("YES", (dialog, cl) -> {

                    Double tot;
                    Double amount = Double.parseDouble(binding.amountFrom.getText().toString());
                    CommonSharedPreference.setsharedText(getApplicationContext(), "amount",binding.amountFrom.getText().toString());
                    if(binding.currenciesSpinnerFrom.getSelectedItem().toString().equals("USD")
                            && binding.currenciesSpinnerTo.getSelectedItem().toString().equals("CAD")) {
                        tot = amount * 1.32;
                        //Currency item;
                        //item.set()
                        //currencyList.add(item);
                        Toast.makeText(getApplicationContext(), tot.toString(), Toast.LENGTH_LONG).show();
                        Snackbar.make(binding.amountFrom, tot.toString(),Snackbar.LENGTH_LONG).show();
                    }

                }).setNegativeButton("NO", (dialog, cl) -> {

                }).create().show();

            }
        });
    }

    private void configureToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Currency Converter");
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