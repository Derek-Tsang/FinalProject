package algonquin.cst2335.finalproject.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.Adapter.BearAdapter;
import algonquin.cst2335.finalproject.Entities.Bear;
import algonquin.cst2335.finalproject.Entities.BearImageDetailsFragment;
import algonquin.cst2335.finalproject.Model.BearDAO;
import algonquin.cst2335.finalproject.Model.BearDatabase;
import algonquin.cst2335.finalproject.Model.BearModel;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.UI.Fragment.BearFragment;
import algonquin.cst2335.finalproject.Utilities.CommonSharedPreference;
import algonquin.cst2335.finalproject.databinding.ActivityBearBinding;

public abstract class BearImageGeneratorActivity extends AppCompatActivity {
    ActivityBearBinding binding;
    private ArrayList<Bear> images = new ArrayList<>();
    ;
    BearAdapter adapter;
    Toolbar toolbar;
    protected RequestQueue queue = null;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView myRecyclerView;
    BearDAO mDAO; // Declare the field

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //SharedPreference
        CommonSharedPreference.getsharedText(this, "lastCode");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bear);

        binding = ActivityBearBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        queue = Volley.newRequestQueue(this);
        adapter = new BearAdapter(this, images);
        binding.recyclerviewSavedImages.setAdapter(adapter);
        binding.recyclerviewSavedImages.setLayoutManager(new LinearLayoutManager(this));

        myRecyclerView = binding.recyclerviewSavedImages;
        myRecyclerView.setAdapter(myAdapter);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        configureToolbar();

        //Create database
        BearDatabase db = Room.databaseBuilder(getApplicationContext(), BearDatabase.class, "database-name").build();
        //Create DAO
        mDAO = db.bearDAO();


        //if(images == null) {
        //images = new ArrayList<>();
        // }
        // load image list from database start
        if (images == null || images.size() == 0) {
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {   //Once get the data from database
                images.addAll(mDAO.getAllImage());
                //load the RecyclerView
                runOnUiThread(() -> myRecyclerView.setAdapter(myAdapter));
            });
        }

        //load image list from db end
        BearModel.selectedImage.observe(this, (newImage) -> {
            if (newImage != null) {
                Log.d("Wow:", "new bear image is not null");
                BearImageDetailsFragment bearImageDetailsFragment = new BearImageDetailsFragment(newImage);
                //show the fragment on screen
                FragmentManager fMgr = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fMgr.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentLocation, bearImageDetailsFragment);
                fragmentTransaction.addToBackStack("Add to back stack");
                fragmentTransaction.commit();
            } else {
                Log.d("Wow:", "new bear image is  null");
            }
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
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.bearToHome) {
            bearToHome();
        } else if (item.getItemId() == R.id.bearDelete) {
            bearDelete();
        } else if (item.getItemId() == R.id.help) {
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

                runOnUiThread(() -> {
                    BearFragment bearFragment = new BearFragment(BearImageGeneratorActivity.this, bitmap);
                    getSupportFragmentManager().beginTransaction().add(R.id.fragmentDetailBear, bearFragment).addToBackStack("").commit();
                    images.add(new Bear());
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


    private void bearDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Bear deletedBear = BearModel.selectedImage.getValue();

        if (deletedBear != null) {
            builder.setTitle("Do you want to delete the bear?")
                    .setNegativeButton("No", (dialog, cl) -> {
                    })
                    .setPositiveButton("Yes", (dialog, cl) -> {
                        int position = images.indexOf(deletedBear); // 获取要删除的图片在列表中的位置

                        if (position != -1) {
                            images.remove(position);
                            adapter.notifyItemRemoved(position);

                            // 显示 Snackbar 提示
                            Snackbar.make(binding.getRoot(), "You deleted bear #" + (position + 1), Snackbar.LENGTH_LONG)
                                    .setAction("UNDO", (clk) -> {
                                        images.add(position, deletedBear);
                                        adapter.notifyItemInserted(position);
                                    })
                                    .show();
                        }
                    })
                    .create()
                    .show();
        }
    }

    private void bearToHome() {
        // 创建一个 Intent，指定要返回的主页面的类
        Intent intent = new Intent(this, MainActivity.class);
        // 启动主页面
        startActivity(intent);
        // 关闭当前活动（可选）
        finish();
    }
}