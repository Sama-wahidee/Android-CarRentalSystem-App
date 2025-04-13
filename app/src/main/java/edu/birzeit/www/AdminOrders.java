package edu.birzeit.www;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminOrders extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Orders> orderList;
    String email;
    String vin;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton imageButton;
    private Menu menu;
    TextView textViewUsername, textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainaao);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
        imageButton = findViewById(R.id.buttonDrawer);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu = navigationView.getMenu();
                onCreateOptionsMenu(menu);
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
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
            Toast.makeText(AdminOrders.this, "Failed to Fetch", Toast.LENGTH_SHORT).show();
        });
        queue.add(jsonArrayRequest);
//--------------------------** SHAHD EDIT **-----------------------------

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.AdminSettingOption) {
                    Toast.makeText(AdminOrders.this, "Account Setting Option", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminOrders.this, manageAdminAcc_Activity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.addCarOption) {
                    Toast.makeText(AdminOrders.this, "Add Car Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminOrders.this, AddCarActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.reportOption) {
                    Toast.makeText(AdminOrders.this, "Report Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminOrders.this, ReportActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.orders) {
                    Toast.makeText(AdminOrders.this, "Admin Orders Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminOrders.this, AdminOrders.class);
                    startActivity(intent);
                }
                if (itemId == R.id.reservations) {
                    Toast.makeText(AdminOrders.this, "Reservations Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminOrders.this, UserReservations.class);
                    startActivity(intent);
                }
                if (itemId == R.id.ContactUsOption) {
                    Toast.makeText(AdminOrders.this, "Contact Us Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminOrders.this, ContacUsActivity.class);
                    startActivity(intent);
                }

                if (itemId == R.id.logout) {
                    Toast.makeText(AdminOrders.this, "Logging out...", Toast.LENGTH_SHORT).show();
                    getSharedPreferences("loginPrefs", MODE_PRIVATE).edit()
                            .clear()
                            .apply();

                    Intent intent = new Intent(AdminOrders.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (itemId == R.id.home) {
                    Toast.makeText(AdminOrders.this, "Home Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminOrders.this, MainActivity2.class);
                    startActivity(intent);
                }
                if (itemId == R.id.navaboutUs) {
                    Toast.makeText(AdminOrders.this, "About Us", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminOrders.this, AboutUs.class);
                    startActivity(intent);
                }
                drawerLayout.close();
                return false;
            }


        });
        recyclerView = findViewById(R.id.orderRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, orderList);
        recyclerView.setAdapter(orderAdapter);

        loadOrders(orderAdapter);
    }

    private void loadOrders(final OrderAdapter adapter) {
        final List<Orders> orders = new ArrayList<>();

        String getUrl = "http://10.0.2.2:80/project_android/order_get.php?";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, getUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);

                                int orderId = object.optInt("order_id", 0);
                                long vinNumber = object.optLong("vin_number", 0);
                                String rentCost = object.optString("rent_cost", "0.00");
                                String userName = object.optString("UserName", "No name available");
                                String email = object.optString("Email", "No email available");
                                long phone = object.optLong("phone", 0);
                                String startDate = object.optString("startDate", "No start date available");
                                String endDate = object.optString("endDate", "No end date available");
                                String imageUrl = object.optString("image", "http://10.0.2.2:80/project_android/images/default.png");

                                Orders order = new Orders(orderId, vinNumber, rentCost, userName, email, phone, startDate, endDate, imageUrl);
                                orders.add(order);
                            }

                            // Notify the adapter that data has been changed
                            adapter.updateOrders(orders);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AdminOrders.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Log.e("Volley Error", "Timeout Error: " + error.getMessage());
                    Toast.makeText(AdminOrders.this, "Timeout Error: Please check your internet connection", Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {
                    Log.e("Volley Error", "No Connection Error: " + error.getMessage());
                    Toast.makeText(AdminOrders.this, "No Connection Error: Please check your internet connection", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Log.e("Volley Error", "Server Error: " + error.getMessage());
                    Toast.makeText(AdminOrders.this, "Server Error: Please try again later", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Log.e("Volley Error", "Network Error: " + error.getMessage());
                    Toast.makeText(AdminOrders.this, "Network Error: Please check your internet connection", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Log.e("Volley Error", "Parse Error: " + error.getMessage());
                    Toast.makeText(AdminOrders.this, "Parse Error: Please try again later", Toast.LENGTH_LONG).show();
                } else {
                    Log.e("Volley Error", "Unknown Volley error: " + error.getMessage());
                    Toast.makeText(AdminOrders.this, "Unknown Volley error: Please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });

        Volley.newRequestQueue(AdminOrders.this).add(stringRequest);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();

        getMenuInflater().inflate(R.menu.drawer_items, menu);

        MenuItem addCarItem = menu.findItem(R.id.addCarOption);
        addCarItem.setVisible(login.isAdmin); // Hide/show add car menu item based on isAdmin value

        MenuItem ordersItem = menu.findItem(R.id.orders);
        ordersItem.setVisible(login.isAdmin); // Hide/show orders menu item based on isAdmin value

        MenuItem reportItem = menu.findItem(R.id.reportOption);
        reportItem.setVisible(login.isAdmin); // Hide/show report menu item based on isAdmin value

        MenuItem reservItem = menu.findItem(R.id.reservations);
        reservItem.setVisible(!(login.isAdmin));
        MenuItem contactItem = menu.findItem(R.id.ContactUsOption);
        contactItem.setVisible(!(login.isAdmin));
        return true;
}
}
