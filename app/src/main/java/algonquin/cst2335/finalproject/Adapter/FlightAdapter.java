package algonquin.cst2335.finalproject.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.Entities.FlightInfo;
import algonquin.cst2335.finalproject.Model.DataSource;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.UI.FavouriteFlightActivity;
import algonquin.cst2335.finalproject.UI.FlightTrackerActivity;
import algonquin.cst2335.finalproject.UI.Fragment.FlightDetailFragment;

/**
 *  The FlightAdapter class is responsible for providing data to the RecyclerView in the FlightTrackerActivity and FavouriteFlightActivity.
 *  It also handles the click events on each item in the RecyclerView and displays a dialog fragment with detailed flight information.
 */
public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder> {
    /**
     * The application context.
     */
    private Context context;

    /**
     * List of FlightInfo objects.
     */
    public List<FlightInfo> flights;

    /**
     * The listener for item click events.
     */
    private OnItemClickListener listener;

    /**
     * The position of the item.
     */
    private int position;

    /**
     * Constructs a new FlightAdapter with the specified application context and list of flights.
     *
     * @param applicationcontext The application context.
     * @param flights The list of flights to be displayed.
     */
    public FlightAdapter(Context applicationcontext, List<FlightInfo> flights) {
        this.context = applicationcontext;
        // list of flights
        this.flights = flights;
    }

    /**
     * Interface definition for a callback to be invoked when an item in the RecyclerView is clicked.
     */
    public interface OnItemClickListener {

        /**
         * abstract method of onItemClick
          * @param item
         * @param position
         */
        void onItemClick(FlightInfo item, int position);
    }

    /**
     * Sets the listener for item click events.
     *
     * @param listener The listener to set.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // view to displayed for each question

        View itemview = LayoutInflater.from(context).inflate(R.layout.flight_item_layout, parent, false);
        return new ViewHolder(itemview);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder that should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        // setting data and handling radio buttons for each view holder
        holder.tvAirline.setText(flights.get(position).getFlight().getAirline_name());
        holder.tvFlightIdata.setText(flights.get(position).getFlight().getAirline_iata()+ " " + flights.get(position).getFlight().getFlight_number());
        holder.tvDate.setText(flights.get(position).getFlight().getFlight_date());
        holder.tvDepartureAirport.setText(flights.get(position).getDepartureAirport().getIata());
        holder.tvDepartureTime.setText(flights.get(position).getDepartureAirport().getScheduled().substring(11,16));
        holder.tvArrivalAirport.setText(flights.get(position).getArrivalAirport().getIata());
        holder.tvArrivalTime.setText(flights.get(position).getArrivalAirport().getScheduled().substring(11,16));
        holder.tvSchedule.setText(flights.get(position).getFlight().getFlight_status());
        holder.setIsRecyclable(true);

    }
    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return flights.size();
    }


    /**
     * The ViewHolder class represents each item in the RecyclerView.
     * It holds and initializes the views for each item, and handles click events on each item.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         *TextViews for displaying flight information
          */
        TextView tvAirline,tvFlightIdata, tvDate, tvDepartureAirport,
                tvDepartureTime,tvArrivalAirport,tvArrivalTime,tvSchedule;

        /**
         * ViewHolder of flight adapter
         * @param view
         */
        public ViewHolder(final View view) {
            super(view);
            tvDate = view.findViewById(R.id.tvDate);
            tvAirline = view.findViewById(R.id.tvAirline);
            tvDepartureAirport = view.findViewById(R.id.tvDepartureAirport);
            tvDepartureTime = view.findViewById(R.id.tvDepartureTime);
            tvArrivalAirport = view.findViewById(R.id.tvArrivalAirport);
            tvArrivalTime = view.findViewById(R.id.tvArrivalTime);
            tvFlightIdata = view.findViewById(R.id.tvFlightIdata);
            tvSchedule = view.findViewById(R.id.tvSchedule);

            view.setOnClickListener(click -> {
                position = getAbsoluteAdapterPosition();
                FlightInfo selected = flights.get(position);
                listener.onItemClick(selected,position);
             });

        }
    }

}
