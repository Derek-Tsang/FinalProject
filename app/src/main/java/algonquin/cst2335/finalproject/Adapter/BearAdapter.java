package algonquin.cst2335.finalproject.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import algonquin.cst2335.finalproject.R;

public class BearAdapter extends RecyclerView.Adapter<BearAdapter.ViewHolder> {
    private List<SavedImage> savedImagesList;

    public BearAdapter(List<SavedImage> savedImagesList) {
        this.savedImagesList = savedImagesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_image_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SavedImage savedImage = savedImagesList.get(position);
        // Bind savedImage data to the ViewHolder views
        holder.ivSavedImage.setImageURI(savedImage.getImageUri());
        holder.tvWidth.setText("Width: " + savedImage.getWidth());
        holder.tvHeight.setText("Height: " + savedImage.getHeight());
    }

    @Override
    public int getItemCount() {
        return savedImagesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSavedImage;
        TextView tvWidth, tvHeight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSavedImage = itemView.findViewById(R.id.ivSavedImage);
            tvWidth = itemView.findViewById(R.id.tvWidth);
            tvHeight = itemView.findViewById(R.id.tvHeight);
        }
    }
}

