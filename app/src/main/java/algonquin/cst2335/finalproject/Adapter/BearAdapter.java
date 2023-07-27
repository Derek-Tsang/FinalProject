package algonquin.cst2335.finalproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.Entities.Bear;
import algonquin.cst2335.finalproject.R;

public class BearAdapter extends RecyclerView.Adapter<BearAdapter.ViewHolder> {
    Context context;
    ArrayList<Bear> bears = new ArrayList<>();
    public BearAdapter(Context applicationcontext, ArrayList<Bear> bears) {
        this.context = applicationcontext;
        this.bears = bears;
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
        //Bear bear = bears.get(position);
        int imageWidth = 100;
        int imageHeight = 10;

        // Set a click listener on the image to show a Snackbar with the image's dimensions.
        holder.image.setOnClickListener(view -> {
            String message = "Image Width: " + imageWidth + " pixels\nImage Height: " + imageHeight + " pixels";
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        });
    }

    @Override
    public int getItemCount() {
        return bears.size();
    }

    //
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ViewHolder(final View view) {
            super(view);
            image = view.findViewById(R.id.imageView);
        }
    }
}
