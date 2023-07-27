package algonquin.cst2335.finalproject.UI.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
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
    Bitmap bitmap;

    public BearFragment(Context applicationContext, Bitmap bm ) {
        bitmap = bm;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = BearDetailedLayoutBinding.inflate(inflater);
        binding.imageView.setImageBitmap(bitmap);
        return binding.getRoot();
    }
}
