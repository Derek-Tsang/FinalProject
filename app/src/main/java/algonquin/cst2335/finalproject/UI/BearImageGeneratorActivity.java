package algonquin.cst2335.finalproject.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.Adapter.BearAdapter;
import algonquin.cst2335.finalproject.Entities.Bear;
import algonquin.cst2335.finalproject.Model.BearDAO;
import algonquin.cst2335.finalproject.Model.BearDatabase;
import algonquin.cst2335.finalproject.Model.DataSource;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.UI.Fragment.BearFragment;
import algonquin.cst2335.finalproject.Utilities.CommonSharedPreference;
import algonquin.cst2335.finalproject.databinding.ActivityBearBinding;

/**
 * Activity class for generating and displaying Bear images.
 * @author min
 * @version 1.0
 */
public class BearImageGeneratorActivity extends AppCompatActivity {
    ActivityBearBinding binding;
    List<Bear> bears = new ArrayList<>();
    BearAdapter adapter;
    Toolbar toolbar;
    JsonObjectRequest jsonObjRequest;
    protected RequestQueue queue = null;
    BearDAO dao;

    /**
     * Generates a new Bear image based on user input and displays it.
     * Fetches random width and height to create different-sized bear images.
     */
    private void generateBearImage() {
        // Fetch random width and height to generate different-sized bear images
        int width,height;
        try{
            width = Integer.parseInt(binding.editImageSizeWidth.getText().toString());
        }catch (Exception e){
            // Show a toast message for invalid height and clear the input
            Toast.makeText(BearImageGeneratorActivity.this, "Invalid Width, please re-enter width!", Toast.LENGTH_SHORT).show();
            binding.editImageSizeWidth.setText("");
            e.printStackTrace();
            return;
        }
        try{
            height = Integer.parseInt(binding.editImageSizeHeight.getText().toString());
        }catch (Exception e){
            Toast.makeText(BearImageGeneratorActivity.this, "Invalid Height, please re-enter height!", Toast.LENGTH_SHORT).show();
            binding.editImageSizeHeight.setText("");
            e.printStackTrace();
            return;
        }
        // Create a unique image path based on the width, height, and current time
        String imagePath = width+"x"+height+"_"+ System.currentTimeMillis() + ".png";
        // Create the URL with the random width and height for the bear image
        String pictureURL = "https://placebear.com/" + width + "/" + height;

        /**
         * Initiates an asynchronous request to fetch a bear image from the provided URL.
         *
         * @param pictureURL The URL of the bear image to be fetched.
         */
        ImageRequest imgReq = new ImageRequest(pictureURL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                // Add a new BearFragment and update the list of bears
                Bear bear = new Bear(width,height,imagePath);
                // Use an Executor to insert the new Bear into the database in a separate thread
                Executor thread = Executors.newSingleThreadExecutor();
                bears.add(bear);
                thread.execute(() ->{
                    dao.insertImage(bear);
                });
                // Display a toast message with the bitmap information
                Toast.makeText(BearImageGeneratorActivity.this, bitmap.toString(), Toast.LENGTH_SHORT).show();
                // Create a new BearFragment with the bear image and add it to the FragmentManager
                BearFragment bearFragment = new BearFragment(BearImageGeneratorActivity.this,bear,bitmap);
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentDetailBear, bearFragment).addToBackStack("").commit();

                thread.execute(() ->{
                    // Compress and save the bitmap image to internal storage
                    try {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                                BearImageGeneratorActivity.this.openFileOutput(imagePath, Activity.MODE_PRIVATE));

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    // Update the adapter on the UI thread to reflect the new data
                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                });
            }
        }, width, height, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors by displaying a toast message
                Toast.makeText(BearImageGeneratorActivity.this, "Error loading bear image", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the ImageRequest to the request queue for processing
        queue.add(imgReq);
    }


    /**
     * Generates a random integer within the specified range, inclusive.
     *
     * @param min The minimum value of the desired range.
     * @param max The maximum value of the desired range.
     * @return A randomly generated integer within the specified range [min, max].
     */
    private int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * After the user enters a specific width and height,
     * the bear image of the corresponding size is obtained through a network request and displayed on the interface.
     * At the same time, it also allows users to click on the picture to view detailed information,
     * as well as load previously saved bear picture data from the database
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.
     *                          <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //SharedPreference
        // Load shared preferences value for "lastCode"
        CommonSharedPreference.getsharedText(this, "lastCode");
        // Call the superclass method for activity creation
        super.onCreate(savedInstanceState);
        // Inflate the layout using the generated binding class
        binding = ActivityBearBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Initialize the Volley request queue
        queue = Volley.newRequestQueue(this);
        // Configure the toolbar for the activity
        configureToolbar();

        // Initialize the Room database and retrieve saved bear images
        BearDatabase db = DataSource.getInstance(this).getBearDB();

        dao = db.bearDAO();
        Executor thread = Executors.newSingleThreadExecutor();
        // Update the UI with the retrieved bear images using runOnUiThread
        thread.execute(() ->
        {
            bears = dao.getAllImage();
            // Update the UI with the retrieved bear images using runOnUiThread
            runOnUiThread(() -> {
                // Initialize the BearAdapter with the retrieved bear images
                adapter = new BearAdapter(this, bears);
                // Set the adapter for the RecyclerView and set its layout manager
                binding.recyclerviewSavedImages.setAdapter(adapter);
                binding.recyclerviewSavedImages.setLayoutManager(new LinearLayoutManager(this));
                // Set an item click listener for the adapter
                adapter.setOnItemClickListener(new BearAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(Bear bearImage, int position, Bitmap bitmap) {
                        // Create and display a BearFragment on item click
                        BearFragment bearFragment = new BearFragment(BearImageGeneratorActivity.this,bearImage,bitmap);
                        getSupportFragmentManager().beginTransaction().add(R.id.fragmentDetailBear, bearFragment).addToBackStack("").commit();
                    }
                });
                /**
                 * Sets a long click listener for items in the adapter, allowing users to delete a bear image.
                 *
                 * @param bearImage The Bear object representing the image to be deleted.
                 * @param position The position of the bear image in the list.
                 * @param bitmap The bitmap image associated with the bear image.
                 */
                adapter.setOnItemLongClickListener(new BearAdapter.OnItemLongClickListener(){
                    @Override
                    public void onItemLongClick(Bear bearImage, int position, Bitmap bitmap) {
                        // Create an AlertDialog.Builder to display a confirmation dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(BearImageGeneratorActivity.this);
                        // Set the dialog message and title
                        builder.setMessage("Do you want to delete the record?")
                                .setTitle("Question: ")
                                // Set a negative button (No) with no action
                                .setNegativeButton("No", (dialog, cl)->{})
                                //delete the message if Yes is clicked.
                                .setPositiveButton("Yes", (dialog,cl)->{
                                    // Create a new single-threaded executor
                                    Executor thread = Executors.newSingleThreadExecutor();
                                    // Execute the deletion and UI update operations on a separate thread
                                    thread.execute(() ->
                                    {
                                        // Delete the bear image from the database
                                        dao.deleteImage(bearImage);
                                        // Remove the bear image from the list
                                        bears.remove(position);
                                        // Update the UI on the main thread to reflect the changes
                                        runOnUiThread(() -> {
                                            adapter.notifyDataSetChanged();
                                        });
                                    });
                                })
                                // show the alert dialog to the screen
                                .create().show();
                    }
                });
            });
        });
        /**
         * Sets a click listener for the "Generate" button, which initiates the process of generating bear images.
         * Displays a confirmation AlertDialog to ensure the user's intention before generating images.
         *
         * @param v The View that was clicked (in this case, the "Generate" button).
         */
        binding.btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show an AlertDialog to confirm clicking "btnGenerate"
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BearImageGeneratorActivity.this);
                // Set the dialog title and message to confirm the user's intention
                alertDialogBuilder.setTitle("Confirm");
                alertDialogBuilder.setMessage("Are you sure you want to click btnGenerate?");
                // Set a positive button (Yes) to proceed with generating images
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    // Call the method to generate different-sized bear images
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Generate different-sized bear images from the URL
                        generateBearImage();

                    }
                });
                // Set a negative button (No) to dismiss the dialog if the user changes their mind
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Code to perform when the user clicks "No" on the AlertDialog.
                        // This is optional if you want to handle the "No" button click.
                        dialog.dismiss(); // Dismiss the AlertDialog.
                    }
                });
                // Create and show the AlertDialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    /**
     * Configures the toolbar for the activity, including setting the title, text color, and home icon.
     * The home icon is set to navigate back to the previous activity upon clicking.
     */
    private void configureToolbar() {
        // Set the support action bar to the toolbar
        setSupportActionBar(binding.toolbar);
        // Set the title of the toolbar
        getSupportActionBar().setTitle("Bear Image Generator");
        // Set the text color of the toolbar title
        binding.toolbar.setTitleTextColor(Color.WHITE);
        //display home icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set the home icon image and its click behavior to finish the current activity
        binding.toolbar.setNavigationIcon(R.drawable.ic_home);
        binding.toolbar.setNavigationOnClickListener(click -> {
            finish();
        });
    }

    /**
     * Hides the toolbar by setting its visibility to GONE.
     * Useful for cases where the toolbar should not be displayed on the screen.
     */
    public void hideToolbar() {
        toolbar.setVisibility(View.GONE);
    }

    /**
     * Initializes the options menu for the activity by inflating a menu resource.
     * This method is called when the options menu is first created.
     *
     * @param menu The menu object to populate with items.
     * @return Returns true to display the menu, false otherwise.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bear_menu, menu);
        return true;
    }

    /**
     * Handles selection events of menu items in the options menu.
     *
     * @param item The selected menu item.
     * @return Returns true if the item's selection has been handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.bearHelp) {
            // Display a help dialog when the "Help" menu item is selected
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("How to use")
                    .setMessage("Click Search to get the bear image\n" +
                            "1.The bear picture come from the internet\n" +
                            "2.You can click the ? button to get to know how to use it. \n " +
                            "3.Click the search Button to get the bear image \n" +
                            "4.You can click the generated bear image to know the height and width(pixels) \n" +
                            "5. Slow down to get the different output")
                    .setPositiveButton("Got it!", (dialog, cl) -> {
                    })
                    .create().show();
            return true;
        }else if (itemID == R.id.item_about) {
            // Display a toast message showing the app version and creator when "About" is selected
            Toast.makeText(this, "Version 1.0, created by M", Toast.LENGTH_LONG).show();
        }
        // Return the result of the super class method
        return super.onOptionsItemSelected(item);
    }


    /**
     * Navigates the user back to the main page (MainActivity) by creating an Intent and starting the activity.
     * Optionally, finishes the current activity to remove it from the back stack.
     */
    private void bearToHome() {
        // Create an Intent, specifying the main page's class to return to
        Intent intent = new Intent(this, MainActivity.class);
        // Launch the main page
        startActivity(intent);
        // Optional: Finish the current activity
        finish();
    }

}
