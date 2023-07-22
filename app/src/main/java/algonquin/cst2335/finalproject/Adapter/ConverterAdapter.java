package algonquin.cst2335.finalproject.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.Entities.CurrencyResult;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.UI.CurrencyConverterActivity;
import algonquin.cst2335.finalproject.UI.Fragment.CurrencyFragment;

/**
 *  The FlightAdapter class is responsible for providing data to the RecyclerView in the FlightTrackerActivity and FavouriteFlightActivity.
 *  It also handles the click events on each item in the RecyclerView and displays a dialog fragment with detailed flight information.
 */
public class ConverterAdapter extends RecyclerView.Adapter<ConverterAdapter.ViewHolder> {
    private Context context;
    public List<CurrencyResult> results;
//    private CurrencyDAO currencyDAO;

    /**
     * Constructs a new FlightAdapter with the specified application context and list of flights.
     *
     * @param applicationcontext The application context.
     * @param results The list of results to be displayed.
     */
    public ConverterAdapter(Context applicationcontext, List<CurrencyResult> results) {
        this.context = applicationcontext;
        // list of flights
        this.results = results;
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

        View itemview = LayoutInflater.from(context).inflate(R.layout.currency_item_layout, parent, false);
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
        holder.tvCurrencyFrom.setText(results.get(position).getCurrencyFrom());
        holder.tvAmountFrom.setText(results.get(position).getAmountFrom()+"");
        holder.tvCurrencyTo.setText(results.get(position).getCurrencyTo());
        holder.tvAmountTo.setText(results.get(position).getAmountTo()+"");
    }
    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return results.size();
    }

    /**
     * The ViewHolder class represents each item in the RecyclerView.
     * It holds and initializes the views for each item, and handles click events on each item.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCurrencyFrom, tvAmountFrom, tvCurrencyTo, tvAmountTo;

        public ViewHolder(final View view) {
            super(view);
            tvCurrencyFrom = view.findViewById(R.id.tvCurrencyFrom);
            tvAmountFrom = view.findViewById(R.id.tvAmountFrom);
            tvCurrencyTo = view.findViewById(R.id.tvCurrencyTo);
            tvAmountTo = view.findViewById(R.id.tvAmountTo);

            view.setOnClickListener(click -> {
                int position = getAbsoluteAdapterPosition();
                CurrencyResult selected = results.get(position);
                CurrencyFragment fragment = new CurrencyFragment(context,selected);

                ((CurrencyConverterActivity)context).getSupportFragmentManager().beginTransaction()
                        .add(R.id.currencyFragment,fragment).addToBackStack("").commit();
            });

//            view.setOnLongClickListener(clk -> {
//                int position1 = getAbsoluteAdapterPosition();
//                CurrencyResult selected = results.get(position1);
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setMessage("Do you want to delete the record ( from "
//                                + selected.getAmountFrom() + " to " + selected.getAmountTo() + " )")
//                        .setTitle("Question: ")
//                        .setNegativeButton("No", (dialog, cl)->{})
//                        //delete the message if Yes is clicked.
//                        .setPositiveButton("Yes", (dialog,cl)->{
//
//                            Executor thread = Executors.newSingleThreadExecutor();
//                            //run on a second thread
//                            thread.execute(() -> {
//                                //delete message from the database
//                                currencyDAO.deleteCurrency(selected);
//                            });
//                            //delete the message from the array list
//                            results.remove(position1);             //remove the message from the arraylist
//                            notifyItemRemoved(position1);
//                            Snackbar.make(tvCurrencyFrom, "You deleted message #" + position1,
//                                            Snackbar.LENGTH_LONG)
//                                    .setAction("Undo", clk2->{
//                                        results.add(position1,selected);
//                                        Executor thread1 = Executors.newSingleThreadExecutor();
//                                        //run on a second thread
//                                        thread.execute(() -> {
//                                            //delete message from the database
//                                            currencyDAO.insertCurrency(selected);
//                                        });
//                                        notifyItemRemoved(position1);
//                                    }).show();
//                        })
//                       // show the alert dialog to the screen
//                        .create().show();
//
//                return true; //why return statement is needed here??
//            });

        }
    }

}
