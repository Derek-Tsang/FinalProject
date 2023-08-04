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
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.UI.Fragment.BearFragment;
import algonquin.cst2335.finalproject.Utilities.CommonSharedPreference;
import algonquin.cst2335.finalproject.databinding.ActivityBearBinding;

public class BearImageGeneratorActivity extends AppCompatActivity {
    ActivityBearBinding binding;
    List<Bear> bears = new ArrayList<>();
    BearAdapter adapter;
    Toolbar toolbar;
    JsonObjectRequest jsonObjRequest;
    protected RequestQueue queue = null;
    BearDAO dao;
    private void generateBearImage() {
        // Fetch random width and height to generate different-sized bear images
        int width,height;
        try{
            width = Integer.parseInt(binding.editImageSizeWidth.getText().toString());
        }catch (Exception e){
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

        String imagePath = width+"x"+height+"_"+ System.currentTimeMillis() + ".png";
//        File file = new File(imagePath);
        // Create the URL with the random width and height
        String pictureURL = "https://placebear.com/" + width + "/" + height;

        ImageRequest imgReq = new ImageRequest(pictureURL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                // Add a new BearFragment and update the list of bears
                Bear bear = new Bear(width,height,imagePath);
                Executor thread = Executors.newSingleThreadExecutor();
                bears.add(bear);
                thread.execute(() ->{
                    dao.insertImage(bear);
                });
                Toast.makeText(BearImageGeneratorActivity.this, bitmap.toString(), Toast.LENGTH_SHORT).show();
                BearFragment bearFragment = new BearFragment(BearImageGeneratorActivity.this,bear,bitmap);
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentDetailBear, bearFragment).addToBackStack("").commit();

                thread.execute(() ->{
                    try {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                                BearImageGeneratorActivity.this.openFileOutput(imagePath, Activity.MODE_PRIVATE));

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                });
            }
        }, width, height, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors, if any
                Toast.makeText(BearImageGeneratorActivity.this, "Error loading bear image", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(imgReq);
    }

    // Helper method to generate a random number in a specified range
    private int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //SharedPreference
        CommonSharedPreference.getsharedText(this, "lastCode");
        super.onCreate(savedInstanceState);
        binding = ActivityBearBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        queue = Volley.newRequestQueue(this);

        configureToolbar();

        BearDatabase db = Room.databaseBuilder(getApplicationContext(), BearDatabase.class, "database-name").build();
        dao = db.bearDAO();
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->
        {
            bears = dao.getAllImage();

            runOnUiThread(() -> {
                adapter = new BearAdapter(this, bears);
                binding.recyclerviewSavedImages.setAdapter(adapter);
                binding.recyclerviewSavedImages.setLayoutManager(new LinearLayoutManager(this));
                adapter.setOnItemClickListener(new BearAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(Bear bearImage, int position, Bitmap bitmap) {
                        BearFragment bearFragment = new BearFragment(BearImageGeneratorActivity.this,bearImage,bitmap);
                        getSupportFragmentManager().beginTransaction().add(R.id.fragmentDetailBear, bearFragment).addToBackStack("").commit();
                    }
                });
                adapter.setOnItemLongClickListener(new BearAdapter.OnItemLongClickListener(){
                    @Override
                    public void onItemLongClick(Bear bearImage, int position, Bitmap bitmap) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BearImageGeneratorActivity.this);
                        builder.setMessage("Do you want to delete the record?")
                                .setTitle("Question: ")
                                .setNegativeButton("No", (dialog, cl)->{})
                                //delete the message if Yes is clicked.
                                .setPositiveButton("Yes", (dialog,cl)->{

                                    Executor thread = Executors.newSingleThreadExecutor();
                                    //run on a second thread
                                    thread.execute(() ->
                                    {
                                        dao.deleteImage(bearImage);
                                        bears.remove(position);
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
        binding.btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show an AlertDialog to confirm clicking "btnGenerate"
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BearImageGeneratorActivity.this);
                alertDialogBuilder.setTitle("Confirm");
                alertDialogBuilder.setMessage("Are you sure you want to click btnGenerate?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Generate different-sized bear images from the URL
                        generateBearImage();

                    }
                });
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

    private void configureToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Bear Image Generator");
        binding.toolbar.setTitleTextColor(Color.WHITE);
        //display home icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationIcon(R.drawable.ic_home);
        binding.toolbar.setNavigationOnClickListener(click -> {
            finish();
        });
    }

    public void hideToolbar() {
        toolbar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bear_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.bearToHome) {
            bearToHome();
            return true;
        } else if (itemID == R.id.bearHelp) {
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
            Toast.makeText(this, "Version 1.0, created by M", Toast.LENGTH_LONG).show();
        }
        // Return the result of the super class method
        return super.onOptionsItemSelected(item);
    }


    private void bearToHome() {
        // Create an Intent, specifying the main page's class to return to
        Intent intent = new Intent(this, MainActivity.class);
        // Launch the main page
        startActivity(intent);
        // Optional: Finish the current activity
        finish();
    }

}
