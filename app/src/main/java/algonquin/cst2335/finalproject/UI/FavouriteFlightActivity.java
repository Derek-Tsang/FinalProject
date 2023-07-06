package algonquin.cst2335.finalproject.UI;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.Adapter.FlightAdapter;
import algonquin.cst2335.finalproject.Entities.FlightInfo;
import algonquin.cst2335.finalproject.Model.DataSource;
import algonquin.cst2335.finalproject.Model.FlightViewModel;
import algonquin.cst2335.finalproject.databinding.ActivityFlightFavouriteBinding;
/**
 * The FavouriteFlightActivity class is an activity that displays a list of favorite flights.
 * It allows users to search for flights based on airport code and view the corresponding flight information.
 The activity uses a ViewModel to handle data and a RecyclerView to display the list of flights.
 */
public class FavouriteFlightActivity extends AppCompatActivity {

    ActivityFlightFavouriteBinding binding;
    FlightViewModel flightModel;

    ArrayList<FlightInfo> favoriteFlights = new ArrayList<FlightInfo>();
    FlightAdapter flightAdapter;
    private final String _TAG = "FAVOURITE_FLIGHT_TAG";

    /**
     * Called when the activity is starting. Performs initialization of the activity.
     * @param savedInstanceState The saved instance state Bundle, or null if the activity is being started fresh.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlightFavouriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        flightModel = new ViewModelProvider(this).get(FlightViewModel.class);

        favoriteFlights =flightModel.flights.getValue();
        if(favoriteFlights == null || favoriteFlights.isEmpty())
        {
            flightModel.flights.setValue(favoriteFlights = new ArrayList<>());
        }

        flightAdapter = new FlightAdapter(this,favoriteFlights);

        binding.rvFlights.setAdapter(flightAdapter);
        getFlightDataFromDatabase(binding.etAirportCode.getText().toString());
        binding.rvFlights.setLayoutManager(new LinearLayoutManager(this));
        binding.btnSearch.setOnClickListener(click->{
            if(!binding.etAirportCode.getText().equals("") || binding.etAirportCode.getText()!=null){
                getFlightDataFromDatabase(binding.etAirportCode.getText().toString());
            }else{
                Toast.makeText(FavouriteFlightActivity.this, "please input Search keyword!", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         *
         */
        binding.etAirportCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getFlightDataFromDatabase(s.toString());
            }
        });
    }

    /**
     * Retrieves flight data from the database based on the provided keyword.
     *
     * @param keyword The search keyword to filter flight data.
     */
    private void getFlightDataFromDatabase(String keyword) {
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->
        {
            favoriteFlights.addAll(DataSource.getInstance(FavouriteFlightActivity.this).getFlgithDB()
                    .flightDAO()
                    .getFlightsWithAirports(keyword));

            runOnUiThread( () -> {
                flightAdapter.flights = favoriteFlights;
                flightAdapter.notifyDataSetChanged();
            });
        });
        favoriteFlights.clear();

    }
}