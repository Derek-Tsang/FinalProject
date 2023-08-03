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
        this.context = context;
        this.result = result;

//        //database
//        db = Room.databaseBuilder(context, CurrencyDatabase.class, "database-name").build();
//        dao = db.cDAO();
//        Executor thread = Executors.newSingleThreadExecutor();
//        thread.execute(() ->
//        {
//            list = dao.getAllCurrency();
//        });
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

        binding.amountBase.setText(result.getAmountFrom() + "");
        binding.currencyName.setText( result.getCurrencyFrom());

        binding.amountTarget.setText(result.getAmountTo()+ "");
        binding.targetCurrency.setText(result.getCurrencyTo()+ "");

        binding.idText.setText(result.getId()+"");

        return binding.getRoot();
    }
}
