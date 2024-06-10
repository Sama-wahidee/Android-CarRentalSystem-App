package edu.birzeit.www;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

public class ReportActivity extends AppCompatActivity {
    private TextView totalCarsTxt, repairCarTxt, rentedCarsTxt, availableCarsTxt,customerTxt;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton imageButton;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainr);
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

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.AdminSettingOption) {
                    Toast.makeText(ReportActivity.this, "Account Setting Option", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReportActivity.this, manageAdminAcc_Activity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.addCarOption) {
                    Toast.makeText(ReportActivity.this, "Add Car Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReportActivity.this, AddCarActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.reportOption) {
                    Toast.makeText(ReportActivity.this, "Report Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReportActivity.this, ReportActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.orders) {
                    Toast.makeText(ReportActivity.this, "Admin Orders Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReportActivity.this, AdminOrders.class);
                    startActivity(intent);
                }
                if (itemId == R.id.reservations) {
                    Toast.makeText(ReportActivity.this, "Reservations Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReportActivity.this, UserReservations.class);
                    startActivity(intent);
                }
                if (itemId == R.id.ContactUsOption) {
                    Toast.makeText(ReportActivity.this, "Contact Us Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReportActivity.this, ContacUsActivity.class);
                    startActivity(intent);
                }

                if (itemId == R.id.logout) {
                    Toast.makeText(ReportActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();
                    getSharedPreferences("loginPrefs", MODE_PRIVATE).edit()
                            .clear()
                            .apply();

                    Intent intent = new Intent(ReportActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (itemId == R.id.home) {
                    Toast.makeText(ReportActivity.this, "Home Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReportActivity.this, MainActivity2.class);
                    startActivity(intent);
                }
                drawerLayout.close();
                return false;
            }


        });
        totalCarsTxt = findViewById(R.id.TotalCarsTxt);
        rentedCarsTxt = findViewById(R.id.rentedCarsTxt);
        availableCarsTxt = findViewById(R.id.AvilabileCarsTxt);
        customerTxt = findViewById(R.id.customerTxt);
        repairCarTxt = findViewById(R.id.repairCarTxt);

        fetchData();
    }

    private void fetchData() {
        String url = "http://10.0.2.2:80/project_android/GetReportData.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        int totalCars = response.getInt("totalCars");
                        int rentedCars = response.getInt("rentedCars");
                        int availableCars = response.getInt("availableCars");
                        int gmailUsers = response.getInt("gmailUsers");
                        int repairCars = response.getInt("under_maintenance");

                        totalCarsTxt.setText(String.valueOf(totalCars));
                        rentedCarsTxt.setText(String.valueOf(rentedCars));
                        availableCarsTxt.setText(String.valueOf(availableCars));
                        customerTxt.setText(String.valueOf(gmailUsers));
                        repairCarTxt.setText(String.valueOf(repairCars));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle error
                    Log.e("ReportActivity", "Error fetching data: " + error.getMessage());
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(request);
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

        MenuItem reverItem = menu.findItem(R.id.reservations);
        reportItem.setVisible(login.isAdmin);
        return true;
    }
}

