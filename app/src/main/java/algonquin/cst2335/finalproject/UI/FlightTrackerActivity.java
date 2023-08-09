package algonquin.cst2335.finalproject.UI;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.Adapter.FlightAdapter;
import algonquin.cst2335.finalproject.Entities.Airport;
import algonquin.cst2335.finalproject.Entities.Flight;
import algonquin.cst2335.finalproject.Entities.FlightInfo;
import algonquin.cst2335.finalproject.Model.FlightViewModel;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.UI.Fragment.FlightDetailFragment;
import algonquin.cst2335.finalproject.Utilities.CommonSharedPreference;
import algonquin.cst2335.finalproject.databinding.ActivityFlightBinding;

/**
 *  The FlightTrackerActivity class represents the main activity for the flight tracking feature of the application.
 *  It displays flight information retrieved from an API and allows users to search for flights using airport codes.
 *  The class extends AppCompatActivity and implements various methods for initializing the activity, retrieving flight data,
 *  parsing JSON responses, and updating the UI accordingly.
 */
public class FlightTrackerActivity extends AppCompatActivity {
    /**
     * View binding for the activity.
     */
    ActivityFlightBinding binding;

    /**
     * ViewModel for managing flight data.
     */
    FlightViewModel flightModel;

    /**
     * JSON object request for retrieving flight information.
     */
    JsonObjectRequest jsonObjRequest;

    /**
     * Volley request queue for handling network requests.
     */
    private RequestQueue mVolleyQueue;

    /**
     * List of favorite flights.
     */
    ArrayList<FlightInfo> flights = new ArrayList<FlightInfo>();

    /**
     * Adapter for displaying flights in a RecyclerView.
     */
    FlightAdapter flightAdapter;

    /**
     * Tag used for logging and identifying this class.
     */
    private final String _TAG = "FLIGHT_TRACKER_TAG";

    /**
     * Toolbar for displaying UI elements and actions.
     */
    Toolbar toolbar;

    /**
     * Initializes the FlightTrackerActivity by setting the content view,
     * initializing the FlightViewModel, and setting up the RecyclerView and click listeners.
     *
     * @param savedInstanceState The saved instance state Bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        configureToolbar();

        flightModel = new ViewModelProvider(this).get(FlightViewModel.class);
        flights =flightModel.flights.getValue();
        if(flights == null || flights.isEmpty())
        {
            flightModel.flights.setValue(flights = new ArrayList<>());
        }

        binding.etAirportCode.setText(CommonSharedPreference.getsharedText(this, "lastCode"));

        //initialize the volley queue
        mVolleyQueue = Volley.newRequestQueue(this);
        flightAdapter = new FlightAdapter(this,flights);
        binding.rvFlights.setAdapter(flightAdapter);
        binding.rvFlights.setLayoutManager(new LinearLayoutManager(this));

        binding.btnSearch.setOnClickListener(click->{
            if(!binding.etAirportCode.getText().equals("") || binding.etAirportCode.getText()!=null){
                Log.e(_TAG, binding.etAirportCode.getText().toString());
                //display progress loading bar
                binding.progressBar.setVisibility(View.VISIBLE);
                //save to Preferences
                CommonSharedPreference.setsharedText(this, "lastCode", binding.etAirportCode.getText().toString());
                getFlightDataFromInternet(binding.etAirportCode.getText().toString());
            }else{
                Toast.makeText(FlightTrackerActivity.this, R.string.toast_input_airport_code, Toast.LENGTH_SHORT).show();
            }


        });
        binding.btnFavourite.setOnClickListener(click->{
            startActivity( new Intent(this, FavouriteFlightActivity.class));
        });

        flightAdapter.setOnItemClickListener(new FlightAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FlightInfo item, int position) {
                FlightDetailFragment fragment = new FlightDetailFragment(getApplicationContext(),item,false);
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentDetail, fragment).addToBackStack("") .commit();
            }
        });

        getFlightDataFromInternet(CommonSharedPreference.getsharedText(this, "lastCode"));
    }

    /**
     * Retrieves flight data from the internet using the provided airport code.
     * Makes a GET request to the AviationStack API and parses the JSON response.
     * @param airportCode The airport code used to search for flights.
     */
    private void getFlightDataFromInternet(String airportCode) {
        String url = "http://api.aviationstack.com/v1/flights";
        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("access_key", "19df7bca6a2430a9e93b5d723ce027cc");
        builder.appendQueryParameter("dep_iata", airportCode);
        // scheduled, active, landed, cancelled, incident, diverted
//        builder.appendQueryParameter("flight_status", "diverted");
        builder.appendQueryParameter("limit", String.valueOf(20));

        Log.e(_TAG, "url = " + builder.toString());
        jsonObjRequest = new JsonObjectRequest(Request.Method.GET, builder.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    parseFlightResponse(response);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(FlightTrackerActivity.this, R.string.toast_json_parse_error, Toast.LENGTH_LONG).show();
                }
            }
        }, error -> {
            Toast.makeText(FlightTrackerActivity.this, R.string.toast_api_error, Toast.LENGTH_LONG).show();
            binding.progressBar.setVisibility(View.GONE);
        });
        jsonObjRequest.setTag(_TAG);
        mVolleyQueue.add(jsonObjRequest);
    }
    /**
     *  Parses the flight data from the JSON response and updates the flights ArrayList.
     *  @param response The JSON response received from the API.
     *  @throws JSONException If there's an error parsing the JSON response.
     */
    private void parseFlightResponse(JSONObject response) throws JSONException {
        flights = new ArrayList<FlightInfo>();

        if(response.has("data")) {
            try {
                JSONArray data = response.getJSONArray("data");
                for(int i = 0 ; i < data.length(); i++) {
                    FlightInfo info = new FlightInfo();
                    Flight flight = new Flight();
                    Airport departure = new Airport();
                    Airport arrival = new Airport();
                    JSONObject jsonObj = data.getJSONObject(i);
                    flight.setFlight_date(jsonObj.getString("flight_date"));
                    flight.setFlight_status(jsonObj.getString("flight_status"));
                    if(jsonObj.has("departure")) {
                        JSONObject departureObj = jsonObj.getJSONObject("departure");
                        departure.setAirport(departureObj.getString("airport"));
                        departure.setTimezone(departureObj.getString("timezone"));
                        departure.setIata(departureObj.getString("iata"));
                        departure.setIcao(departureObj.getString("icao"));
                        departure.setTerminal(departureObj.getString("terminal"));
                        departure.setGate(departureObj.getString("gate"));
                        departure.setDelay(departureObj.getString("delay"));
                        departure.setScheduled(departureObj.getString("scheduled"));
                        departure.setEstimated(departureObj.getString("estimated"));
                        departure.setActual(departureObj.getString("actual"));
                        departure.setEstimatedRunway(departureObj.getString("estimated_runway"));
                        departure.setActualRunway(departureObj.getString("actual_runway"));
                    }
                    if(jsonObj.has("arrival")) {
                        JSONObject arrivalObj = jsonObj.getJSONObject("arrival");
                        arrival.setAirport(arrivalObj.getString("airport"));
                        arrival.setTimezone(arrivalObj.getString("timezone"));
                        arrival.setIata(arrivalObj.getString("iata"));
                        arrival.setIcao(arrivalObj.getString("icao"));
                        arrival.setTerminal(arrivalObj.getString("terminal"));
                        arrival.setGate(arrivalObj.getString("gate"));
                        arrival.setBaggage(arrivalObj.getString("baggage"));
                        arrival.setDelay(arrivalObj.getString("delay"));
                        arrival.setScheduled(arrivalObj.getString("scheduled"));
                        arrival.setEstimated(arrivalObj.getString("estimated"));
                        arrival.setActual(arrivalObj.getString("actual"));
                        arrival.setEstimatedRunway(arrivalObj.getString("estimated_runway"));
                        arrival.setActualRunway(arrivalObj.getString("actual_runway"));
                    }
                    if(jsonObj.has("airline")) {
                        JSONObject airline = jsonObj.getJSONObject("airline");
                        flight.setAirline_name(airline.getString("name"));
                        flight.setAirline_iata(airline.getString("iata"));
                        flight.setAirline_icao(airline.getString("icao"));
                    }
                    if(jsonObj.has("flight")) {
                        JSONObject f = jsonObj.getJSONObject("flight");
                        flight.setFlight_number(f.getString("number"));
                        flight.setFlight_iata(f.getString("iata"));
                        flight.setFlight_icao(f.getString("icao"));
                    }
                    info.setFlight(flight);
                    info.setDepartureAirport(departure);
                    info.setArrivalAirport(arrival);
                    flights.add(info);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            flightAdapter.flights = flights;
            //save the flights keep the results for screen orientation change
            flightModel.flights.postValue(flights);
            flightAdapter.notifyDataSetChanged();
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * configure Toolbar
     */
    private void configureToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(R.string.aviation_stack_flight_tracker);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        //display home icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationIcon(R.drawable.ic_home);
        binding.toolbar.setNavigationOnClickListener(click -> {
            finish();
        });

    }
    /**
     * hide Toolbar
     */
    public void hideToolbar() {
        toolbar.setVisibility(View.GONE);
    }
    /**
     * Create Options Menu
     * @param menu The options menu in which you place your items.
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    /**
     * Options Item Selected
     * @param item The menu item that was selected.
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.help){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.howToUse)
                    .setMessage(R.string.flight_tracker_help)
                    .setPositiveButton(R.string.gotIt, (dialog,cl) -> {

                    })
                    .create().show();
        }
        return true;
    }
}