package edu.birzeit.www;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.birzeit.www.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity implements recyclerinterface {

DrawerLayout drawerLayout;
ImageButton imageButton;
NavigationView navigationView;
    private static final String get_url = "http://10.0.2.2:80/project_android/get_cars.php";
///
    private ActionBarDrawerToggle toggle;
////
RecyclerView recyclerView;
Adapter adapter;
    private final List<Car> cars = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        drawerLayout=findViewById(R.id.drawerlayout);
        imageButton= findViewById(R.id.buttonDrawer);
        navigationView=findViewById(R.id.navigationView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        adapter = new Adapter(MainActivity2.this ,cars , this);
        recyclerView.setAdapter(adapter);
        loadCars(adapter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId= menuItem.getItemId();
                if (itemId==R.id.AdminSettingOption){
                    Toast.makeText(MainActivity2.this, "Account Setting Option",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, manageAdminAcc_Activity.class);
                    startActivity(intent);
                }
                if (itemId==R.id.addCarOption){
                    Toast.makeText(MainActivity2.this, "Add Car Page",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, AddCarActivity.class);
                    startActivity(intent);
                }
                if (itemId==R.id.reportOption){
                    Toast.makeText(MainActivity2.this, "Report Page",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, ReportActivity.class);
                    startActivity(intent);
                }
                if(itemId==R.id.orders){
                    Toast.makeText(MainActivity2.this, "Admin Orders Page",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, AdminOrders.class);
                    startActivity(intent);
                }
                if(itemId==R.id.reservations){
                    Toast.makeText(MainActivity2.this, "Admin Orders Page",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, UserReservations.class);
                    startActivity(intent);
                }

                drawerLayout.close();
                return false;
            }
        });



    }

    private void loadCars(final Adapter adapter) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, get_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String name = object.optString("name", "Unknown");
                                String description = object.optString("description", "No description available");
                                String vinNumber = object.optString("vinNumber", "");
                                String fuelType = object.optString("fuelType", "");
                                String transmission = object.optString("transmission", "");
                                int numberOfSeats = object.optInt("numberOfSeats", 0);
                                double rentPrice = object.optDouble("rentPrice", 0.0);
                                String color = object.optString("color", "");
                                String model = object.optString("model", "");
                                int topSpeed = object.optInt("topSpeed", 0);
                                String imageUrl = object.optString("image", "http://10.0.2.2:80/test_and/images/default.png");

                                Car car = new Car(name, description, vinNumber, fuelType, transmission,
                                        numberOfSeats, rentPrice, color, model, topSpeed, imageUrl);
                                cars.add(car);
                            }

                            // Notify the adapter that data has been changed
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            Log.e("Volley Error", "JSON parsing error: " + e.getMessage());
                            Toast.makeText(MainActivity2.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", "Volley error: " + error.getMessage());
                Toast.makeText(MainActivity2.this, "Volley error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(MainActivity2.this).add(stringRequest);
    }

    @Override
    public void onitemclick(int position) {
        Intent intent=new Intent(MainActivity2.this , adminActivity.class);
        intent.putExtra("name", cars.get(position).getName());
        intent.putExtra("reprice", cars.get(position).getRentPrice());
        intent.putExtra("image", cars.get(position).getImageUrl());
        startActivity(intent);

    }
}