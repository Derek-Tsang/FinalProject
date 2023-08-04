package algonquin.cst2335.finalproject.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.Entities.Bear;
import algonquin.cst2335.finalproject.R;

public class BearAdapter extends RecyclerView.Adapter<BearAdapter.ViewHolder> {
    Activity context;
    List<Bear> bears = new ArrayList<>();
    private String imageWidth;
    private String imageHeight;
    private BearAdapter.OnItemClickListener listener;
    private BearAdapter.OnItemLongClickListener long_listener;
    Bitmap savedImage = null ;

    int position;

    public BearAdapter(Activity applicationcontext, List<Bear> bears) {
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
        //this initializes a row
        Bear bearImage = bears.get(position);
        holder.detail.setText("Width:  " + bearImage.getWidthGenerated() + "pixels \nHeight:" + bearImage.getHeightGenerated()+"pixels");

        //load image file from device start
        savedImage = getBitmap(bearImage);
        holder.imageViewDetail.setImageBitmap(savedImage);

    }

    private Bitmap getBitmap(Bear bearImage){
        File file = new File(context.getFilesDir() + File.separator + bearImage.getPath());
        Bitmap bitmap;
        if(file.exists()){
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bear_pic);
        }

        //load image file from device end
        if (bitmap != null) {
            // show image to imageview
            bitmap = Bitmap.createScaledBitmap(bitmap, bearImage.getWidthGenerated(), bearImage.getHeightGenerated(), true);
        }
        return bitmap;
    }

    public interface OnItemClickListener {
        void onItemClick(Bear item, int position, Bitmap bitmap);
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(Bear item, int position, Bitmap bitmap);
    }
    public void setOnItemClickListener(BearAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    public void setOnItemLongClickListener(BearAdapter.OnItemLongClickListener listener) {
        this.long_listener = listener;
    }
    @Override
    public int getItemCount() {
        return bears.size();
    }

    //
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewDetail;
        TextView detail;

        public ViewHolder(final View view) {
            super(view);
            imageViewDetail = view.findViewById(R.id.imageView);
            detail = view.findViewById(R.id.width_height);
            view.setOnClickListener(click -> {
                position = getAbsoluteAdapterPosition();
                Bear selected = bears.get(position);
                listener.onItemClick(selected,position,getBitmap(selected));
            });

            view.setOnLongClickListener(click -> {
                position = getAbsoluteAdapterPosition();
                Bear selected = bears.get(position);
                long_listener.onItemLongClick(selected,position,getBitmap(selected));
                return true;
            });
        }
    }
}
