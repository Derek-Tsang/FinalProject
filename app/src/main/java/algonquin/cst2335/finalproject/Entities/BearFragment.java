package algonquin.cst2335.finalproject.Entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.finalproject.databinding.BearDetailedLayoutBinding;

/**
 * A Fragment class to display detailed information about a Bear image.
 * @author min
 * @version 1.0
 */
public class BearFragment extends Fragment {
    BearDetailedLayoutBinding binding;
    Bitmap bitmap;
    Bear selected;

    /**
     * Creates a new instance of BearFragment with the specified bear image details.
     * applicationContext The application context.
     * bear The Bear object containing image information.
     * bm The Bitmap of the bear image.
     * @param applicationContext
     * @param bear
     * @param bm
     */
    public BearFragment(Context applicationContext, Bear bear, Bitmap bm ) {
        selected = bear;
        bitmap = bm;
    }

    /**
     * The overridden onCreateView method.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = BearDetailedLayoutBinding.inflate(inflater);
        binding.imageViewDetail.setImageBitmap(bitmap);
        binding.Detail.setText(selected.getWidthGenerated() + " px x "+ selected.getHeightGenerated()+ " px");
        return binding.getRoot();
    }
}
