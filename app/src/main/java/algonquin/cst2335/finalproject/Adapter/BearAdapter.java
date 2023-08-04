package algonquin.cst2335.finalproject.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.text.BreakIterator;
import java.util.ArrayList;

import algonquin.cst2335.finalproject.Entities.Bear;
import algonquin.cst2335.finalproject.R;

public class BearAdapter extends RecyclerView.Adapter<BearAdapter.ViewHolder> {
    Context context;
    ArrayList<Bear> bears = new ArrayList<>();
    private String imageWidth;
    private String imageHeight;

    public BearAdapter(Context applicationcontext, ArrayList<Bear> bears) {
        this.context = applicationcontext;
        this.bears = bears;
    }

    public static int getPosition() {
        return 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // view to displayed for each question
        View itemview = LayoutInflater.from(context).inflate(R.layout.bear_item_layout, parent, false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //this initializes a row
        Bear bearImage = bears.get(position);
        holder.sizeTextView.setText(bearImage.getWidthGenerated().toString() + " X " +
                bearImage.getHeightGenerated().toString());
        //holder.bearListImageView.set
        Bitmap savedImage = null ;
        //load image file from device start
        Log.d("*onCreate, open file *", getFilesDir() + File.separator);
        File file = new File( getFilesDir() + File.separator);

        if(file.exists()){
            savedImage = BitmapFactory.decodeFile(file.getAbsolutePath());
        } else {
            Log.d("*onCreate, open file *", "File does not exist");
        }
        //load image file from device end

        if (savedImage != null) {
            // show image to imageview
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(savedImage, 150, 150, true);
            holder.bearImageView.setImageBitmap(resizedBitmap);
        }

        // Set a click listener on the image to show a Snackbar with the image's dimensions.
        holder.image.setOnClickListener(view -> {
            String message = "Image Width: " + imageWidth + " pixels\nImage Height: " + imageHeight + " pixels";
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        });
    }

    private String getFilesDir() {
        return null;
    }

    @Override
    public int getItemCount() {
        return bears.size();
    }

    //
    public class ViewHolder extends RecyclerView.ViewHolder {
        public BreakIterator sizeTextView;
        public ImageView bearImageView;
        ImageView image;

        public ViewHolder(final View view) {
            super(view);
            image = view.findViewById(R.id.imageViewDetail);
        }
    }
}
