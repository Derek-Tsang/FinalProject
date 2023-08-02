package algonquin.cst2335.finalproject.UI.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.Entities.CurrencyResult;
import algonquin.cst2335.finalproject.Model.CurrencyDAO;
import algonquin.cst2335.finalproject.Model.CurrencyDatabase;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.FragmentCurrencyDetailsLayoutBinding;
import algonquin.cst2335.finalproject.databinding.FragmentFlightDetailsLayoutBinding;

public class CurrencyFragment extends Fragment {
    /**
     * position in listView.
     */
    private int position;

    CurrencyResult result;

    Context context;

    FragmentCurrencyDetailsLayoutBinding binding;

    private RecyclerView.Adapter myAdapter;
    CurrencyDatabase db;
    CurrencyDAO dao;

    List<CurrencyResult> list = new ArrayList<CurrencyResult>();

    public CurrencyFragment(Context context, CurrencyResult result) {
//        this.context = context;
//        this.result = result;

        //database
        db = Room.databaseBuilder(context, CurrencyDatabase.class, "database-name").build();
        dao = db.cDAO();
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->
        {
            list = dao.getAllCurrency();
        });
    }

    /**
     * @param inflater           to display XML layout.
     * @param container          used to display layout.
     * @param savedInstanceState not used.
     * @return View that will be displayed onto phone or tablet.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentCurrencyDetailsLayoutBinding.inflate(inflater);

        binding.amountBase.setText(String.valueOf(result.getAmountFrom()));
        binding.currencyName.setText( result.getCurrencyFrom());

        binding.amountTarget.setText(String.valueOf(result.getAmountTo()));
        binding.targetCurrency.setText(result.getCurrencyTo());

        return binding.getRoot();
//        dataFromActivity = getArguments();
//        assert dataFromActivity != null;
//        id = dataFromActivity.getLong(CurrencyConverter.ITEM_ID);
//        position = dataFromActivity.getInt(CurrencyConverter.ITEM_POSITION);
//
//        String baseCurrency = dataFromActivity.getString(CurrencyConverter.BASE_CURRENCY);
//        String targetCurrency = dataFromActivity.getString(CurrencyConverter.TARGET_CURRENCY);
//
//        View result = inflater.inflate(R.layout.fragment_currency, container, false);
//
//        TextView currency = result.findViewById(R.id.currency_name);
//        currency.setText(String.format("%s", baseCurrency));
//
//        TextView target = result.findViewById(R.id.target_currency);
//        target.setText(String.format("%s", targetCurrency));
//
//        //show the id:
//        TextView idView = result.findViewById(R.id.id_number);
//        idView.setText("" + id);
//
//        // get the delete button, and add a click listener:
//        Button deleteButton = result.findViewById(R.id.deleteButton);
//        deleteButton.setOnClickListener(clk -> {
//            if (isTablet) { //both the list and details are on the screen:
//                CurrencyConverter parent = (CurrencyConverter) getActivity();
//                assert parent != null;
//                parent.deleteMessageId((int) id, position); //this deletes the item and updates the list
//
//                //now remove the fragment since you deleted it from the database:
//                // this is the object to be removed, so remove(this):
//                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
//            }
//            //for Phone:
//            else {
//                CurrencyEmptyActivity parent = (CurrencyEmptyActivity) getActivity();
//                Intent backToFragmentExample = new Intent();
//                backToFragmentExample.putExtra(CurrencyConverter.ITEM_ID, dataFromActivity.getLong(CurrencyConverter.ITEM_ID));
//                backToFragmentExample.putExtra(CurrencyConverter.ITEM_POSITION, dataFromActivity.getInt(CurrencyConverter.ITEM_POSITION));
//                backToFragmentExample.putExtra(CurrencyConverter.BASE_CURRENCY, dataFromActivity.getString(CurrencyConverter.BASE_CURRENCY));
//                backToFragmentExample.putExtra(CurrencyConverter.TARGET_CURRENCY, dataFromActivity.getString(CurrencyConverter.TARGET_CURRENCY));
//
//                assert parent != null;
//                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
//                parent.finish(); //go back
//            }
//        });

    }
}
