package edu.birzeit.www;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

import java.util.HashMap;
import java.util.Map;

public class manageAdminAcc_Activity extends AppCompatActivity {
    EditText usernameEditText;
    EditText emailEditText;
    EditText phoneEditText;
    EditText addressEditText, password_edit_text;
    Button saveButton;
    private ImageView usernameEditIcon, passwordEditIcon, emailEditIcon, phoneEditIcon, addressEditIcon;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton imageButton;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainma);
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
                    Toast.makeText(manageAdminAcc_Activity.this, "Account Setting Option", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(manageAdminAcc_Activity.this, manageAdminAcc_Activity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.addCarOption) {
                    Toast.makeText(manageAdminAcc_Activity.this, "Add Car Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(manageAdminAcc_Activity.this, AddCarActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.reportOption) {
                    Toast.makeText(manageAdminAcc_Activity.this, "Report Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(manageAdminAcc_Activity.this, ReportActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.orders) {
                    Toast.makeText(manageAdminAcc_Activity.this, "Admin Orders Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(manageAdminAcc_Activity.this, AdminOrders.class);
                    startActivity(intent);
                }
                if (itemId == R.id.reservations) {
                    Toast.makeText(manageAdminAcc_Activity.this, "Reservations Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(manageAdminAcc_Activity.this, UserReservations.class);
                    startActivity(intent);
                }
                if (itemId == R.id.ContactUsOption) {
                    Toast.makeText(manageAdminAcc_Activity.this, "Contact Us Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(manageAdminAcc_Activity.this, ContacUsActivity.class);
                    startActivity(intent);
                }

                if (itemId == R.id.logout) {
                    Toast.makeText(manageAdminAcc_Activity.this, "Logging out...", Toast.LENGTH_SHORT).show();
                    getSharedPreferences("loginPrefs", MODE_PRIVATE).edit()
                            .clear()
                            .apply();

                    Intent intent = new Intent(manageAdminAcc_Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (itemId == R.id.home) {
                    Toast.makeText(manageAdminAcc_Activity.this, "Home Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(manageAdminAcc_Activity.this, MainActivity2.class);
                    startActivity(intent);
                }
                drawerLayout.close();
                return false;
            }


        });
        // Initialize EditTexts
        usernameEditText = findViewById(R.id.username_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        addressEditText = findViewById(R.id.address_edit_text);
        password_edit_text = findViewById(R.id.password_edit_text);

        usernameEditIcon = findViewById(R.id.username_edit_icon);
        passwordEditIcon = findViewById(R.id.password_edit_icon);
//        emailEditIcon = findViewById(R.id.email_edit_icon);
        phoneEditIcon = findViewById(R.id.phone_edit_icon);
        addressEditIcon = findViewById(R.id.address_edit_icon);

        // Initialize the Save button
        saveButton = findViewById(R.id.save_button);

        // Set click listeners for ImageViews
        setupEditIcon(usernameEditIcon, usernameEditText);
        setupEditIcon(passwordEditIcon, password_edit_text);
//        setupEditIcon(emailEditIcon, emailEditText);
        setupEditIcon(phoneEditIcon, phoneEditText);
        setupEditIcon(addressEditIcon, addressEditText);

        // Set click listener for the Save button
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Get data from EditTexts
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String password = password_edit_text.getText().toString();

                // Save username and email to SharedPreferences to use them in tool bar [mainactivity2]
                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("email", email);
                editor.apply();
                ////////////////////
                // URL of the PHP script
                String url = "http://10.0.2.2:80/project_android/update_user.php";

                // Using Volley to send a POST request
                RequestQueue queue = Volley.newRequestQueue(manageAdminAcc_Activity.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url, response -> {
                    // Handle the server's response
                    Toast.makeText(manageAdminAcc_Activity.this, response, Toast.LENGTH_SHORT).show();
                }, error -> {
                    // Handle error
                    Toast.makeText(manageAdminAcc_Activity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", username);
                        params.put("email", email);
                        params.put("phone", phone);
                        params.put("address", address);
                        params.put("password", password);
                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });

        //*********************************************************
        // Retrieve email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        // Make HTTP request to fetch user data
        String url = "http://10.0.2.2:80/project_android/get_users.php?email=" + email;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        JSONObject user = response.getJSONObject(0); //  there's only one user returned
                        String fetchedUsername = user.getString("UserName");
                        String email = user.getString("Email");
                        String phone = user.getString("Phone");
                        String address = user.getString("Address");
                        String password = user.getString("Password"); // Fetch the password

                        // Autofill EditTexts
                        usernameEditText.setText(fetchedUsername);
                        emailEditText.setText(email);
                        phoneEditText.setText(phone);
                        addressEditText.setText(address);
                        password_edit_text.setText(password);

                        // Set InputType to display actual password
                        password_edit_text.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonArrayRequest);
    }

    private void setupEditIcon(ImageView editIcon, EditText editText) {
        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.isEnabled()) {
                    editText.setEnabled(true);
                    editText.requestFocus();
                }
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

        MenuItem reverItem = menu.findItem(R.id.reservations);
        reportItem.setVisible(login.isAdmin);
        return true;
    }
}
