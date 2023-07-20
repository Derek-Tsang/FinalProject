package algonquin.cst2335.finalproject.UI.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.finalproject.Entities.FlightInfo;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.BearDetailedLayoutBinding;
import algonquin.cst2335.finalproject.databinding.FragmentFlightDetailsLayoutBinding;

public class BearFragment extends Fragment {
    BearDetailedLayoutBinding binding;

    public BearFragment(Context applicationContext) {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = BearDetailedLayoutBinding.inflate(inflater);
        return binding.getRoot();
    }
}
