package algonquin.cst2335.finalproject.UI.Fragment;

import android.app.Activity;
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

import java.io.Serializable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.Entities.FlightInfo;
import algonquin.cst2335.finalproject.Model.DataSource;
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
    public void setOnDismissListener(DialogInterface.OnDismissListener listener){
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
            binding.btnAddFav.setText("Remove Favorite");
        }else{
            binding.btnAddFav.setText("Add to Favorite");
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
        binding.tvDate.setText(selected.getFlight().getFlight_date());
        binding.tvAirline.setText(selected.getFlight().getAirline_name());
        binding.tvFlight.setText(selected.getFlight().getFlight_number());
        binding.tvDeTimeAndDeAirportCode.setText(selected.getDepartureAirport().getScheduled().substring(11,16)+" - "+ selected.getDepartureAirport().getIata());
        binding.tvDeAirportName.setText(selected.getDepartureAirport().getAirport());
        binding.tvDeTerminal.setText("Terminal: "+selected.getDepartureAirport().getTerminal());
        binding.tvDeGate.setText("Gate: "+selected.getDepartureAirport().getGate());
        if(selected.getDepartureAirport().getDelay() == null){
            binding.tvDeDelay.setText("Delay: N/A");
        }else{
            binding.tvDeDelay.setText("Delay: "+selected.getDepartureAirport().getDelay());
        }
        binding.tvArTimeAndArAirportCode.setText(selected.getArrivalAirport().getScheduled().substring(11,16)+" - "+ selected.getArrivalAirport().getIata());
        binding.tvArAirportName.setText(selected.getArrivalAirport().getAirport());
        binding.tvArTerminal.setText("Terminal: "+selected.getArrivalAirport().getTerminal());
        binding.tvArGate.setText("Gate: "+selected.getArrivalAirport().getGate());
        if(selected.getArrivalAirport().getDelay() == null){
            binding.tvArDelay.setText("Delay: N/A");
        }else{
            binding.tvArDelay.setText("Delay: "+selected.getArrivalAirport().getDelay());
        }
        //close fragment
        binding.closeButton.setOnClickListener(clk -> {
            dismiss();
        });
        //save flightinfo into database.
        binding.btnAddFav.setOnClickListener(clk -> {
            if(isFromFavorite){
                removeFavorite(selected);
            }else{
                addFavorite(selected);
            }
            dismiss();
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
                getActivity().runOnUiThread( () ->
                        Toast.makeText(context,"Add Favorite Flight Success!",Toast.LENGTH_SHORT));
            });

        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT);
        }
    }
    /**
     * Removes the selected FlightInfo from the favorites database.
     *
     * @param selected The FlightInfo to be removed from favorites.
     */
    private void removeFavorite(FlightInfo selected){
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder( context );
            builder.setMessage("Do you want to delete this flight?")
                    .setTitle("Question:")
                    .setPositiveButton("Yes", (dialog,cl) -> {
                        Executor thread = Executors.newSingleThreadExecutor();
                        thread.execute(() ->
                        {
                            DataSource.getInstance(context).getFlgithDB()
                                    .flightDAO()
                                    .deleteFlight(selected.flight);
                            ((Activity)context).runOnUiThread( () -> {
                                dismiss();
                            });

                        });
                    })
                    .setNegativeButton("No", (dialog,cl) -> {
                    })
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if(mOnClickListener != null) {
                                mOnClickListener.onDismiss(dialog);
                            }
                        }
                    })
                    .create().show();


        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT);
        }
    }

    public void dismiss() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        getActivity().getSupportFragmentManager().popBackStack();
    }

}