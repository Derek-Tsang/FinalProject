package algonquin.cst2335.finalproject.UI.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.finalproject.Entities.Bear;
import algonquin.cst2335.finalproject.databinding.BearDetailedLayoutBinding;

/**
 * A Fragment class to display detailed information about a Bear image.
 * @author min
 * @version 1.0
 */
public class BearFragment extends Fragment {
    BearDetailedLayoutBinding binding;
    Bitmap bitmap;

    /**
     * Creates a new instance of BearFragment with the specified bear image details.
     *
     * @param applicationContext The application context.
     * @param bearImage The Bear object containing image information.
     * @param bm The Bitmap of the bear image.
     */
    public BearFragment(Context applicationContext, Bear bearImage, Bitmap bm ) {
        bitmap = bm;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = BearDetailedLayoutBinding.inflate(inflater);
        binding.imageViewDetail.setImageBitmap(bitmap);
        return binding.getRoot();
    }
}
