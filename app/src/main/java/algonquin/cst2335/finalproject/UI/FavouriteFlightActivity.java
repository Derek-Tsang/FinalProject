package algonquin.cst2335.finalproject.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.Adapter.FlightAdapter;
import algonquin.cst2335.finalproject.Entities.FlightInfo;
import algonquin.cst2335.finalproject.Model.DataSource;
import algonquin.cst2335.finalproject.Model.FlightViewModel;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.UI.Fragment.FlightDetailFragment;
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
    FlightDetailFragment fragment;
    private final String _TAG = "FAVOURITE_FLIGHT_TAG";
    Toolbar toolbar;

    /**
     * Called when the activity is starting. Performs initialization of the activity.
     * @param savedInstanceState The saved instance state Bundle, or null if the activity is being started fresh.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlightFavouriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        configureToolbar();
        flightModel = new ViewModelProvider(this).get(FlightViewModel.class);

        if(flightModel.searchKeyword.getValue()!=null) {
            binding.etAirportCode.setText(flightModel.searchKeyword.getValue().toString());
        }
        //
        flightAdapter = new FlightAdapter(this,favoriteFlights);
        binding.rvFlights.setAdapter(flightAdapter);
        if(flightModel.favoriteFlights.getValue() == null){
            getFlightDataFromDatabase(binding.etAirportCode.getText().toString());
        }
        binding.rvFlights.setLayoutManager(new LinearLayoutManager(this));
        binding.btnSearch.setOnClickListener(click->{
            if(!binding.etAirportCode.getText().equals("") || binding.etAirportCode.getText()!=null){
                getFlightDataFromDatabase(binding.etAirportCode.getText().toString());
            }else{
                Toast.makeText(FavouriteFlightActivity.this, R.string.toast_input_search_keyword, Toast.LENGTH_SHORT).show();
            }
        });

        flightAdapter.setOnItemClickListener(new FlightAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FlightInfo item,int position) {

                fragment = new FlightDetailFragment(getApplicationContext(),item,true);
                if(fragment!=null) {
                    getSupportFragmentManager().beginTransaction().add(R.id.fragmentDetail, fragment).addToBackStack("").commit();
                    fragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            favoriteFlights.remove(position);
                            flightAdapter.notifyItemRemoved(position);
                            Snackbar.make(binding.rvFlights,R.string.snacker_remove_success, Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }

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
            favoriteFlights = new ArrayList<>();
            favoriteFlights.addAll(DataSource.getInstance(FavouriteFlightActivity.this).getFlgithDB()
                    .flightDAO()
                    .getFlightsWithAirports(keyword));

            runOnUiThread( () -> {
                flightAdapter.flights = favoriteFlights;
                flightModel.favoriteFlights.postValue(favoriteFlights);
                flightAdapter.notifyDataSetChanged();
            });
        });


    }


    private void configureToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(R.string.Flight_Details);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        //display home icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationIcon(R.drawable.ic_left);
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
            builder.setTitle(R.string.howToUse)
                    .setMessage(R.string.flight_tracker_fav_help)
                    .setPositiveButton(R.string.gotIt, (dialog,cl) -> {

                    })
                    .create().show();
        }
        return true;
    }
}