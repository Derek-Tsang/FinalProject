package algonquin.cst2335.finalproject.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
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
import algonquin.cst2335.finalproject.Utilities.CommonSharedPreference;
import algonquin.cst2335.finalproject.databinding.ActivityFlightBinding;

/**
 *  The FlightTrackerActivity class represents the main activity for the flight tracking feature of the application.
 *  It displays flight information retrieved from an API and allows users to search for flights using airport codes.
 *  The class extends AppCompatActivity and implements various methods for initializing the activity, retrieving flight data,
 *  parsing JSON responses, and updating the UI accordingly.
 */
public class FlightTrackerActivity extends AppCompatActivity {

    ActivityFlightBinding binding;
    FlightViewModel flightModel;
    JsonObjectRequest jsonObjRequest;
    private RequestQueue mVolleyQueue;

    ArrayList<FlightInfo> flights = new ArrayList<FlightInfo>();
    FlightAdapter flightAdapter;
    private final String _TAG = "FLIGHT_TRACKER_TAG";

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
                Toast.makeText(FlightTrackerActivity.this, "please input airport code!", Toast.LENGTH_SHORT).show();
            }

        });
        binding.btnFavourite.setOnClickListener(click->{
            startActivity( new Intent(this, FavouriteFlightActivity.class));
        });

    }
    /**
     * Retrieves flight data from the internet using the provided airport code.
     * Makes a GET request to the AviationStack API and parses the JSON response.
     * @param airportCode The airport code used to search for flights.
     */
    private void getFlightDataFromInternet(String airportCode) {

        String url = "http://api.aviationstack.com/v1/flights";
        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("access_key", "cf23e26b3d64c7ce69febab26c4802d2");
        builder.appendQueryParameter("dep_iata", airportCode);
        builder.appendQueryParameter("limit", String.valueOf(20));

        Log.e(_TAG, "url = " + builder.toString());

        jsonObjRequest = new JsonObjectRequest(Request.Method.GET, builder.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    parseFlightResponse(response);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(FlightTrackerActivity.this, "JSON parse error", Toast.LENGTH_LONG).show();
                }
            }
        }, error -> {

            if( error instanceof NetworkError) {
            } else if( error instanceof ClientError) {
            } else if( error instanceof ServerError) {
            } else if( error instanceof AuthFailureError) {
            } else if( error instanceof ParseError) {
            } else if( error instanceof NoConnectionError) {
            } else if( error instanceof TimeoutError) {
            }
            Toast.makeText(FlightTrackerActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
            flightAdapter.notifyDataSetChanged();
            binding.progressBar.setVisibility(View.GONE);
        }
    }
}