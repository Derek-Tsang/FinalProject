package algonquin.cst2335.finalproject.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;

import algonquin.cst2335.finalproject.databinding.ActivityBearBinding;

public class BearImageDetailsFragment extends Fragment {
    Bear selected;

    public BearImageDetailsFragment(Bear m) {
        selected = m;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        ActivityBearBinding binding =  ActivityBearBinding.inflate(inflater, container, false);

        binding.editImageSize.setText( selected.widthGenerated);
        binding.editImageSize2.setText( selected.heightGenerated );

        Bitmap bitmap =  null;
        //File file = new File(getContext().getFilesDir(), "my_image.jpg");
        File file = new File( getContext().getFilesDir()  + File.separator);

        if(file.exists()){
            bitmap =  BitmapFactory.decodeFile(file.getAbsolutePath());
        } else {
            Log.d("*onCreate, open file *", "File does not exist");
        }

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, Integer.parseInt(selected.widthGenerated), Integer.parseInt(selected.heightGenerated) , true);
        binding.icon.setImageBitmap(resizedBitmap);
        return binding.getRoot();
    }
}
