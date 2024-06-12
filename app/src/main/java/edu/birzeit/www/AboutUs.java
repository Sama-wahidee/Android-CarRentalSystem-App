package edu.birzeit.www;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AboutUs extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton imageButton;
    private Menu menu;
    TextView textViewUsername, textViewEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mainaau);
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
            Toast.makeText(AboutUs.this, "Failed to Fetch", Toast.LENGTH_SHORT).show();
        });
        queue.add(jsonArrayRequest);
//--------------------------** SHAHD EDIT **-----------------------------

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.AdminSettingOption) {
                    Toast.makeText(AboutUs.this, "Account Setting Option", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AboutUs.this, manageAdminAcc_Activity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.addCarOption) {
                    Toast.makeText(AboutUs.this, "Add Car Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AboutUs.this, AddCarActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.reportOption) {
                    Toast.makeText(AboutUs.this, "Report Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AboutUs.this, ReportActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.orders) {
                    Toast.makeText(AboutUs.this, "Admin Orders Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AboutUs.this, AdminOrders.class);
                    startActivity(intent);
                }
                if (itemId == R.id.reservations) {
                    Toast.makeText(AboutUs.this, "Reservations Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AboutUs.this, UserReservations.class);
                    startActivity(intent);
                }
                if (itemId == R.id.ContactUsOption) {
                    Toast.makeText(AboutUs.this, "Contact Us Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AboutUs.this, ContacUsActivity.class);
                    startActivity(intent);
                }

                if (itemId == R.id.logout) {
                    Toast.makeText(AboutUs.this, "Logging out...", Toast.LENGTH_SHORT).show();
                    getSharedPreferences("loginPrefs", MODE_PRIVATE).edit()
                            .clear()
                            .apply();

                    Intent intent = new Intent(AboutUs.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (itemId == R.id.home) {
                    Toast.makeText(AboutUs.this, "Home Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AboutUs.this, MainActivity2.class);
                    startActivity(intent);
                }
                if (itemId == R.id.navaboutUs) {
                    Toast.makeText(AboutUs.this, "About Us", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AboutUs.this, AboutUs.class);
                    startActivity(intent);
                }
                drawerLayout.close();
                return false;
            }


        });

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