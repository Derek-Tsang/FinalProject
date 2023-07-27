package algonquin.cst2335.finalproject.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import algonquin.cst2335.finalproject.Adapter.BearAdapter;
import algonquin.cst2335.finalproject.Entities.Bear;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.UI.Fragment.BearFragment;
import algonquin.cst2335.finalproject.databinding.ActivityBearBinding;
import algonquin.cst2335.finalproject.Utilities.CommonSharedPreference;

public class BearImageGeneratorActivity extends AppCompatActivity {
    ActivityBearBinding binding;
    ArrayList<Bear> bears = new ArrayList<>();
    BearAdapter adapter;
    Toolbar toolbar;
    JsonObjectRequest jsonObjRequest;
    protected RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //SharedPreference
        CommonSharedPreference.getsharedText(this, "lastCode");
        super.onCreate(savedInstanceState);
        binding = ActivityBearBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        queue = Volley.newRequestQueue(this);
        adapter = new BearAdapter(this, bears);
        binding.recyclerviewSavedImages.setAdapter(adapter);
        binding.recyclerviewSavedImages.setLayoutManager(new LinearLayoutManager(this));

        configureToolbar();

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
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.help) {
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
        }
        return super.onOptionsItemSelected(item);
    }


    private void generateBearImage() {
        // Fetch random width and height to generate different-sized bear images
        int randomWidth = getRandomNumberInRange(200, 800);
        int randomHeight = getRandomNumberInRange(200, 800);

        // Create the URL with the random width and height
        String pictureURL = "https://placebear.com/" + randomWidth + "/" + randomHeight;

        ImageRequest imgReq = new ImageRequest(pictureURL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                // Add a new BearFragment and update the list of bears

                runOnUiThread( () -> {
                    BearFragment bearFragment = new BearFragment(BearImageGeneratorActivity.this,bitmap);
                    getSupportFragmentManager().beginTransaction().add(R.id.fragmentDetailBear, bearFragment).addToBackStack("").commit();
                    bears.add(new Bear());
                    adapter.notifyDataSetChanged();
                });

            }

            //把网络获取的图片文件路径保存到数据库，recycleview去读取数据库并显示图片
            //数据库里面存的就是地址，bitmap week10天气预报就有 file读取本地路径，new路径赋取，通过pass显现 file-bitmap（item） path
        }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
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

}
