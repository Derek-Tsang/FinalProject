package algonquin.cst2335.finalproject.UI.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.Entities.FlightInfo;
import algonquin.cst2335.finalproject.Model.DataSource;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.FragmentFlightDetailsLayoutBinding;

/**
 *
 * A dialog fragment for displaying flight details and performing actions related to the flight.
 *
 */
public class FlightDetailFragment extends Fragment {

    FlightInfo flight;

    Context context;

    boolean isFromFavorite;

    FragmentFlightDetailsLayoutBinding binding;
    private DialogInterface.OnDismissListener mOnClickListener;

    public FlightDetailFragment() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("flight", flight);
        outState.putBoolean("isFromFavorite", isFromFavorite);
    }

    /**
     * Constructs a new instance of FlightDetailDialogFragment.
     *
     * @param context          The context.
     * @param flight           The FlightInfo object.
     * @param isFromFavorite   Determines if the flight is from the favorites list.
     */
    public FlightDetailFragment(Context context, FlightInfo flight, boolean isFromFavorite) {
        this();
        this.context = context;
        this.flight = flight;
        this.isFromFavorite = isFromFavorite;
    }
    /**
     * Sets the dismiss listener for the dialog fragment.
     * To notify recycleview update data.
     *
     * @param listener The OnDismissListener.
     */
    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        this.mOnClickListener = listener;
    }



    /**
     * Called to create the view for the dialog fragment.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState The previously saved state of the fragment.
     * @return The view for the dialog fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFlightDetailsLayoutBinding.inflate(inflater);

        if(savedInstanceState!=null){
            flight = (FlightInfo) savedInstanceState.getSerializable("flight");
            isFromFavorite = savedInstanceState.getBoolean("isFromFavorite");
        }
        if(isFromFavorite){
            binding.btnAddFav.setText(getString(R.string.rmFav));
        }else{
            binding.btnAddFav.setText(getString(R.string.addFav));
        }
        displayDetail(flight);
        return binding.getRoot();
    }
    /**
     * Displays the flight details on the dialog fragment.
     *
     * @param selected The FlightInfo object to display.
     */
    public void displayDetail(FlightInfo selected){
        binding.tvFromTo.setText(selected.getDepartureAirport().getIata() + " - " + selected.getArrivalAirport().getIata());

        try{
            binding.tvDration.setText(calculateMinsGap(context,selected.getDepartureAirport().getScheduled(),selected.getArrivalAirport().getScheduled()));
        }catch (Exception e){
            e.printStackTrace();
        }
        binding.tvDate.setText(selected.getFlight().getFlight_date());
        binding.tvAirline.setText(selected.getFlight().getAirline_name());
        binding.tvFlight.setText(selected.getFlight().getFlight_iata());
        binding.tvDeTime.setText(selected.getDepartureAirport().getScheduled().substring(11,16));
        binding.tvDeAirportCode.setText(selected.getDepartureAirport().getIata());
        binding.tvDeAirportName.setText(selected.getDepartureAirport().getAirport() +" Airport");
        if(selected.getDepartureAirport().getTerminal() == null || "null".equals(selected.getDepartureAirport().getTerminal())){
            binding.tvDeTerminal.setText("-");
        }else{
            binding.tvDeTerminal.setText(selected.getDepartureAirport().getTerminal());
        }
        if(selected.getDepartureAirport().getGate() == null || "null".equals(selected.getDepartureAirport().getGate())){
            binding.tvDeGate.setText("-");
        }else{
            binding.tvDeGate.setText(selected.getDepartureAirport().getGate());
        }
        if(selected.getDepartureAirport().getDelay() == null || "null".equals(selected.getDepartureAirport().getDelay())){
            binding.tvDeDelay.setText("-");
        }else{
            binding.tvDeDelay.setText(""+selected.getDepartureAirport().getDelay());
        }
        binding.tvArTime.setText(selected.getArrivalAirport().getScheduled().substring(11,16));
        binding.tvArAirportCode.setText(selected.getArrivalAirport().getIata());
        binding.tvArAirportName.setText(selected.getArrivalAirport().getAirport() +" Airport");
        if(selected.getArrivalAirport().getTerminal() == null || "null".equals(selected.getArrivalAirport().getTerminal())){
            binding.tvArTerminal.setText("-");
        }else{
            binding.tvArTerminal.setText(selected.getArrivalAirport().getTerminal());
        }
        if(selected.getArrivalAirport().getGate() == null || "null".equals(selected.getArrivalAirport().getGate())){
            binding.tvArGate.setText("-");
        }else{
            binding.tvArGate.setText(selected.getArrivalAirport().getGate());
        }
        if(selected.getArrivalAirport().getBaggage() == null || "null".equals(selected.getArrivalAirport().getBaggage())){
            binding.tvArBaggage.setText("-");
        }else{
            binding.tvArBaggage.setText(selected.getArrivalAirport().getBaggage());
        }
        //close fragment
        binding.closeButton.setOnClickListener(clk -> {
            dismissFragment();
        });
        //save flightinfo into database.
        binding.btnAddFav.setOnClickListener(clk -> {
            if(isFromFavorite){
                removeFavorite(selected);
            }else{
                addFavorite(selected);
            }
        });
    }
    /**
     * Adds the selected FlightInfo to the favorites database.
     *
     * @param selected The FlightInfo to be added as a favorite.
     */
    private void addFavorite(FlightInfo selected){
        try {
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                long departure_airport_id = DataSource.getInstance(context).getFlgithDB()
                        .flightDAO()
                        .addAirport(selected.getDepartureAirport());
                long arrival_airport_id = DataSource.getInstance(context).getFlgithDB()
                        .flightDAO()
                        .addAirport(selected.getArrivalAirport());
                selected.getFlight().setDepartureId(departure_airport_id);
                selected.getFlight().setArrivalId(arrival_airport_id);
                DataSource.getInstance(context).getFlgithDB()
                        .flightDAO()
                        .addFlight(selected.getFlight());
            });
            Toast.makeText(context,R.string.toast_add_fav_success,Toast.LENGTH_SHORT).show();
            dismissFragment();
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,R.string.toast_add_fav_fail,Toast.LENGTH_SHORT).show();
        }
    }

    boolean isDelete = false;
    /**
     * Removes the selected FlightInfo from the favorites database.
     *
     * @param selected The FlightInfo to be removed from favorites.
     */
    private void removeFavorite(FlightInfo selected){
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );
            builder.setMessage(R.string.alert_remove_message)
                    .setTitle(R.string.question)
                    .setPositiveButton(R.string.alert_yes, (dialog,cl) -> {
                        isDelete = true;
                        Executor thread = Executors.newSingleThreadExecutor();
                        thread.execute(() ->
                        {
                            DataSource.getInstance(context).getFlgithDB()
                                    .flightDAO()
                                    .deleteFlight(selected.flight);
                        });
                        dismissFragment();
                    })
                    .setNegativeButton(R.string.alert_no, (dialog,cl) -> {
                        isDelete = false;
                    })
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if(mOnClickListener != null && isDelete) {
                                mOnClickListener.onDismiss(dialog);
                            }
                        }
                    })
                    .create().show();


        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void dismissFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public static String calculateMinsGap(Context context, String departureDate,String arrivalDate) {
        // Calculate the duration between the two date-times
        long secondsGap = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            OffsetDateTime departure = OffsetDateTime.parse(departureDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            OffsetDateTime arrival = OffsetDateTime.parse(arrivalDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            Duration duration = Duration.between(departure, arrival);
            // Get the duration in seconds
            secondsGap = duration.getSeconds();
        }
        if(secondsGap>60){
            if((secondsGap / 60) > 60){
                return secondsGap / 3600 + context.getResources().getString(R.string.string_hour) + (secondsGap % 3600 / 60) + context.getResources().getString(R.string.string_min);
            }else{
                return secondsGap / 60 + context.getResources().getString(R.string.string_min);
            }
        }else{
            return secondsGap + context.getResources().getString(R.string.string_sec);
        }
    }

}