package edu.birzeit.www;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements recyclerinterface {

    private DrawerLayout drawerLayout;
    private ImageButton imageButton;
    private NavigationView navigationView;
    private static final String get_url = "http://10.0.2.2:80/project_android/get_cars.php";
    private ActionBarDrawerToggle toggle;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private ImageButton search;
    private ImageButton filter;
    ImageButton refresh;
    final List<Car> cars = new ArrayList<>();
    Menu menu;
    TextView textViewUsername, textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        drawerLayout = findViewById(R.id.drawerlayout);
        imageButton = findViewById(R.id.buttonDrawer);
        navigationView = findViewById(R.id.navigationView);
        recyclerView = findViewById(R.id.recyclerInfo);
        search = findViewById(R.id.search);
        filter = findViewById(R.id.filter);
        refresh = findViewById(R.id.refresh);
//--------------------------** SHAHD EDIT **-----------------------------
        // to show name & email on tool bar..
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewEmail = findViewById(R.id.textViewEmail);


//
        NavigationView navigationView = findViewById(R.id.navigationView);
        View headerView = navigationView.getHeaderView(0);  // This gets the header view from the NavigationView

        TextView textViewUsername = headerView.findViewById(R.id.textViewUsername);
        TextView textViewEmail = headerView.findViewById(R.id.textViewEmail);

        // Access SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", "example@example.com");  // Use default value if not found

        textViewEmail.setText(savedEmail);


        // Make HTTP request to fetch user data
        String getUserUrl = "http://10.0.2.2:80/project_android/get_users.php?email=" + savedEmail;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getUserUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        JSONObject user = response.getJSONObject(0); //  there's only one user returned
                        String fetchedUsername = user.getString("UserName");

                        // Autofill EditTexts
                        textViewUsername.setText(fetchedUsername);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, error -> {
            // Handle error
            Toast.makeText(MainActivity2.this, "Failed to Fetch", Toast.LENGTH_SHORT).show();
        });
        queue.add(jsonArrayRequest);
//--------------------------** SHAHD EDIT **-----------------------------

        //-----shahd end
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(MainActivity2.this, cars, this);
        recyclerView.setAdapter(adapter);
        loadCars(adapter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu = navigationView.getMenu();
                onCreateOptionsMenu(menu);
                drawerLayout.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.AdminSettingOption) {
                    Toast.makeText(MainActivity2.this, "Account Setting Option", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, manageAdminAcc_Activity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.addCarOption) {
                    Toast.makeText(MainActivity2.this, "Add Car Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, AddCarActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.reportOption) {
                    Toast.makeText(MainActivity2.this, "Report Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, ReportActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.orders) {
                    Toast.makeText(MainActivity2.this, "Admin Orders Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, AdminOrders.class);
                    startActivity(intent);
                }
                if (itemId == R.id.reservations) {
                    Toast.makeText(MainActivity2.this, "Admin Orders Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, UserReservations.class);
                    startActivity(intent);
                }
                if (itemId == R.id.ContactUsOption) {
                    Toast.makeText(MainActivity2.this, "Contact Us Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, ContacUsActivity.class);
                    startActivity(intent);
                }

                if (itemId == R.id.logout) {
                    Toast.makeText(MainActivity2.this, "Logging out...", Toast.LENGTH_SHORT).show();
                    getSharedPreferences("loginPrefs", MODE_PRIVATE).edit()
                            .clear()
                            .apply();

                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (itemId == R.id.home) {
                    Toast.makeText(MainActivity2.this, "Home Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, MainActivity2.class);
                    startActivity(intent);
                }
                drawerLayout.close();
                return false;
            }


        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCars(adapter);
                SideDrawerFragment sideDrawerFragment = new SideDrawerFragment();
                sideDrawerFragment.show(getSupportFragmentManager(), "SideDrawerFragment");
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCars(adapter);
                SideDrawerFragment1 sideDrawerFragment = new SideDrawerFragment1();
                sideDrawerFragment.setCarList(cars);
                sideDrawerFragment.show(getSupportFragmentManager(), "SideDrawerFragment");
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCars(adapter);
            }
        });
    }

    private void loadCars(final Adapter adapter) {
        cars.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, get_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                int year = object.optInt("Year", 0);
                                String description = object.optString("description", "No description available");
                                String vinNumber = object.optString("VIN_number", "");
                                String fuelType = object.optString("fuelType", "");
                                String transmission = object.optString("Transmission", "");
                                int numberOfSeats = object.optInt("numberOfSeats", 0);
                                int rentPrice = object.optInt("rentPrice", 0);
                                String color = object.optString("color", "");
                                String model = object.optString("model", "");
                                int topSpeed = object.optInt("topSpeed", 0);
                                String imageUrl = object.optString("image", "http://10.0.2.2:80/test_and/images/default.png");

                                Car car = new Car(description, vinNumber, fuelType, transmission,
                                        numberOfSeats, rentPrice, color, model, topSpeed, imageUrl, year);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();

        getMenuInflater().inflate(R.menu.drawer_items, menu);

        MenuItem addCarItem = menu.findItem(R.id.addCarOption);
        addCarItem.setVisible(login.isAdmin); // Hide/show add car menu item based on isAdmin value

        MenuItem ordersItem = menu.findItem(R.id.orders);
        ordersItem.setVisible(login.isAdmin); // Hide/show orders menu item based on isAdmin value

        MenuItem reportItem = menu.findItem(R.id.reportOption);
        reportItem.setVisible(login.isAdmin); // Hide/show report menu item based on isAdmin value

        return true;
    }


    public void updateCarList(List<Car> filteredCars) {
        cars.clear();
        cars.addAll(filteredCars);
        adapter.notifyDataSetChanged();
    }
}
