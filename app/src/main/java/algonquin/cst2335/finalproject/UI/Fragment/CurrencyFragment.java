package algonquin.cst2335.finalproject.UI.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.Entities.CurrencyResult;
import algonquin.cst2335.finalproject.Model.CurrencyDAO;
import algonquin.cst2335.finalproject.Model.CurrencyDatabase;
import algonquin.cst2335.finalproject.databinding.FragmentCurrencyDetailsLayoutBinding;

/**
 * The CurrencyFragment class represents a fragment that displays details of a currency conversion result.
 * This fragment is used to show additional information about a currency conversion result, such as the converted amounts and currencies.
 *
 * @version 1.0
 * @since 2023-08-04
 */
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

    /**
     * Constructs a new CurrencyFragment with the given context and currency conversion result.
     *
     * @param context The context of the fragment.
     * @param result The currency conversion result to display.
     */
    public CurrencyFragment(Context context, CurrencyResult result) {
        this.context = context;
        this.result = result;
    }

    /**
     * Inflates the layout for the fragment and initializes UI components with currency conversion result data.
     *
     * @param inflater The layout inflater.
     * @param container The parent view group.
     * @param savedInstanceState The saved instance state.
     * @return The root view of the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentCurrencyDetailsLayoutBinding.inflate(inflater);

        binding.amountBase.setText(result.getAmountFrom() + "");
        binding.currencyName.setText(result.getCurrencyFrom());

        binding.amountTarget.setText(result.getAmountTo()+ "");
        binding.targetCurrency.setText(result.getCurrencyTo()+ "");

        binding.idNumber.setText(result.getId()+"");

        return binding.getRoot();
    }
}
