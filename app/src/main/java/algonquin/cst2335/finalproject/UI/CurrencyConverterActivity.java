package algonquin.cst2335.finalproject.UI;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.Adapter.ConverterAdapter;
import algonquin.cst2335.finalproject.Entities.CurrencyResult;
import algonquin.cst2335.finalproject.Model.CurrencyDAO;
import algonquin.cst2335.finalproject.Model.CurrencyDatabase;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.Utilities.CommonSharedPreference;
import algonquin.cst2335.finalproject.databinding.ActivityCurrencyBinding;

/**
 * The CurrencyConverterActivity class represents the main activity for currency conversion.
 * This activity allows users to convert currency amounts between different currencies using a web API.
 *
 * @version 1.0
 * @since 2023-08-04
 */
public class CurrencyConverterActivity extends AppCompatActivity {

    ActivityCurrencyBinding binding;
    Toolbar toolbar;
    List<CurrencyResult> results = new ArrayList<CurrencyResult>();
    CurrencyDAO dao;
    ConverterAdapter adapter;
    RequestQueue queue = null;
//    long id;

    /**
     * Initializes the activity and sets up the UI components and listeners.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrencyBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        configureToolbar();

        SharedPreferences pref = getSharedPreferences("Final", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        binding.amountFrom.setText(pref.getString("amount",""));

        queue = Volley.newRequestQueue(this);

        String[] from = {"USD", "AUD", "CAD"};
        ArrayAdapter adapterFrom = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, from);
        binding.currenciesSpinnerFrom.setAdapter(adapterFrom);

        String[] to = {"CAD", "AUD", "USD"};
        ArrayAdapter adapterTo = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, to);
        binding.currenciesSpinnerTo.setAdapter(adapterTo);

        //database
        CurrencyDatabase db = Room.databaseBuilder(getApplicationContext(), CurrencyDatabase.class, "database-name").build();
        dao = db.cDAO();
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->
        {
            results = dao.getAllCurrency();

            runOnUiThread(() -> {
                adapter = new ConverterAdapter(this,results);
                binding.conversionList.setAdapter(adapter);
                binding.conversionList.setLayoutManager(new LinearLayoutManager(this));});
        });

        binding.runConversion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if("".equals(binding.amountFrom.getText().toString())){
                    Snackbar.make(binding.amountFrom, "Please enter amount to be converted",Snackbar.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"please enter amount",Toast.LENGTH_SHORT).show();
                    return;
                }

                Double amountTo;
                Double amountFrom = Double.parseDouble(binding.amountFrom.getText().toString());
                CommonSharedPreference.setsharedText(getApplicationContext(), "amount",binding.amountFrom.getText().toString());
                RequestFromHttpToDevice();
            }
        });
    }

    /**
     * Configures the toolbar for the activity.
     */
    private void configureToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(R.string.currency_converter);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        //display home icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationIcon(R.drawable.ic_home);
        binding.toolbar.setNavigationOnClickListener(click -> {
            finish();
        });

    }

    /**
     * Hides the toolbar.
     */
    public void hideToolbar() {
        toolbar.setVisibility(View.GONE);
    }

    /**
     * Inflates the menu options in the toolbar.
     * @param menu The menu to inflate.
     * @return Whether the menu is created successfully.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /**
     * Handles menu item selections from the toolbar menu.
     * @param item The selected menu item.
     * @return Whether the item is handled successfully.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.help){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.howToUse)
                    .setMessage(R.string.helpConverter)
                    .setPositiveButton(R.string.gotIt, (dialog,cl) -> {

                    })
                    .create().show();
        }
        return true;
    }

    /**
     * Sends a currency conversion request to a web API and processes the response.
     */
    private void RequestFromHttpToDevice(){
        //RequestQueue initialized
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String amountFrom = binding.amountFrom.getText().toString();
        String currencyFrom = binding.currenciesSpinnerFrom.getSelectedItem().toString();
        String currencyTo = binding.currenciesSpinnerTo.getSelectedItem().toString();

        String url = "https://currency-converter5.p.rapidapi.com/currency/convert";
        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("format", "json");
        builder.appendQueryParameter("from", currencyFrom);
        builder.appendQueryParameter("to", currencyTo);
        builder.appendQueryParameter("amount", amountFrom);

        //String Request initialized
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, builder.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG,"response :" + response.toString());
                // display the repsonse to the screen
                // it is only for example, you should parse the string
                //  and set the fields in recyclerView
                CurrencyResult currency = new CurrencyResult();
                currency.setCurrencyFrom(currencyFrom);
                currency.setCurrencyTo(currencyTo);
                currency.setAmountFrom(Double.parseDouble(amountFrom));
                JSONObject rates = null;
                try {
                    rates = response.getJSONObject("rates");
                    JSONObject position0 = rates.getJSONObject(currencyTo);
                    String rate_for_amount = position0.getString("rate_for_amount");
                    currency.setAmountTo(Double.parseDouble(rate_for_amount));
                    Toast.makeText(getApplicationContext(),"Convert " + currencyFrom +" "+ Double.parseDouble(amountFrom) +" to " + currencyTo +" \n" +
                            "result is " + rate_for_amount, Toast.LENGTH_LONG).show();

                    binding.amountTo.setText(rate_for_amount.toString());

                    Executor thread = Executors.newSingleThreadExecutor();
                    thread.execute(() ->
                    {
                        dao.insertCurrency(currency); //insert result into database
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,"Error :" + error.toString());
            }
        }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("X-RapidAPI-Key", "3507213528mshdd511c16dee84fbp106387jsn4982d4dd8c14");
                    params.put("X-RapidAPI-Host", "currency-converter5.p.rapidapi.com");

                    return params;
                }
            };
        requestQueue.add(request);
    }
}