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

/**
 * defines a custom BearAdapter class that extends RecyclerView.
 * Adapter to display a list of bear images in a RecyclerView
 * @author min
 * @version 1.0
 */
public class BearAdapter extends RecyclerView.Adapter<BearAdapter.ViewHolder> {
    //An instance variable to hold the reference to the parent activity.
    Activity context;
    //A list to store Bear objects (presumably containing bear image data)
    List<Bear> bears = new ArrayList<>();
    //Interfaces to handle item click and long-click events.
    private BearAdapter.OnItemClickListener listener;
    private BearAdapter.OnItemLongClickListener long_listener;
    //A Bitmap variable to store the loaded image temporarily
    Bitmap savedImage = null ;
    //An integer variable to keep track of the item position
    int position;

    /**
     * Constructor that takes an Activity context and a list of Bear objects.
     * It initializes the member variables.
     * @param applicationcontext
     * @param bears
     */
    public BearAdapter(Activity applicationcontext, List<Bear> bears) {
        this.context = applicationcontext;
        this.bears = bears;
    }

    /**
     * This method is responsible for inflating the layout for each item in the RecyclerView.
     * It returns a ViewHolder object.
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the layout defined in bear_item_layout.xml to represent each item in the RecyclerView.
        View itemview = LayoutInflater.from(context).inflate(R.layout.bear_item_layout, parent, false);
        return new ViewHolder(itemview);
    }

    /**
     * This method binds the data to the ViewHolder and is called for each item in the list.
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Gets the Bear object at the current position.
        Bear bearImage = bears.get(position);
        //Sets the text for the detail TextView with the width and height information of the bear image.
        holder.detail.setText("Width:  " + bearImage.getWidthGenerated() + "pixels \nHeight:" + bearImage.getHeightGenerated()+"pixels");
        //Retrieves the image bitmap for the bear and assigns it to the savedImage variable.
        savedImage = getBitmap(bearImage);
        //Sets the retrieved image bitmap to the ImageView in the ViewHolder.
        holder.imageViewDetail.setImageBitmap(savedImage);
    }

    /**
     * This method gets a bitmap image for a given Bear object.
     * Checks if the image file exists in the device storage and decodes it into a Bitmap.
     * If the file doesn't exist, it decodes a default bear image from resources.
     * @param bearImage
     * @return
     */
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

    /**
     * An interface to handle item click events.
     */
    public interface OnItemClickListener {
        void onItemClick(Bear item, int position, Bitmap bitmap);
    }

    /**
     * An interface to handle item long-click events.
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(Bear item, int position, Bitmap bitmap);
    }

    /**
     * Methods to set the listeners.
     * @param listener
     */
    public void setOnItemClickListener(BearAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Methods to set the listeners.
     * @param listener
     */
    public void setOnItemLongClickListener(BearAdapter.OnItemLongClickListener listener) {
        this.long_listener = listener;
    }

    /**
     * Returns the number of items in the bears list.
     * @return
     */
    @Override
    public int getItemCount() {
        return bears.size();
    }

    /**
     *  A nested class that extends RecyclerView.
     *  ViewHolder to hold the view elements for each item.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewDetail;
        TextView detail;

        public ViewHolder(final View view) {
            super(view);
            imageViewDetail = view.findViewById(R.id.imageView);
            detail = view.findViewById(R.id.width_height);

            /**
             * Sets an OnClickListener for the item view to handle item clicks.
             * It retrieves the clicked Bear object and
             * invokes the OnItemClickListener's onItemClick method.
             */
            view.setOnClickListener(click -> {
                position = getAbsoluteAdapterPosition();
                Bear selected = bears.get(position);
                listener.onItemClick(selected,position,getBitmap(selected));
            });

            /**
             * Sets an OnLongClickListener for the item view to handle long-click events. It retrieves the clicked Bear object
             * and invokes the OnItemLongClickListener's onItemLongClick method.
             */
            view.setOnLongClickListener(click -> {
                position = getAbsoluteAdapterPosition();
                Bear selected = bears.get(position);
                long_listener.onItemLongClick(selected,position,getBitmap(selected));
                return true;
            });
        }
    }
}
