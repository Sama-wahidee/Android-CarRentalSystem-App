package edu.birzeit.www;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BookingDetails extends AppCompatActivity {
    private EditText userNameEditText, phoneEditText, emailEditText, rentCostEditText, rentalperiod, VINEditText;
    private Button bookCarButton;
    private ImageView calendarimage;
    EditText rentPriceTextView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton imageButton;
    TextView textViewUsername, textViewEmail;
    String rentCost;

    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainabd);
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
                    Toast.makeText(BookingDetails.this, "Account Setting Option", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookingDetails.this, manageAdminAcc_Activity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.addCarOption) {
                    Toast.makeText(BookingDetails.this, "Add Car Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookingDetails.this, AddCarActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.reportOption) {
                    Toast.makeText(BookingDetails.this, "Report Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookingDetails.this, ReportActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.orders) {
                    Toast.makeText(BookingDetails.this, "Admin Orders Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookingDetails.this, AdminOrders.class);
                    startActivity(intent);
                }
                if (itemId == R.id.reservations) {
                    Toast.makeText(BookingDetails.this, "Reservations Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookingDetails.this, UserReservations.class);
                    startActivity(intent);
                }
                if (itemId == R.id.ContactUsOption) {
                    Toast.makeText(BookingDetails.this, "Contact Us Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookingDetails.this, ContacUsActivity.class);
                    startActivity(intent);
                }

                if (itemId == R.id.logout) {
                    Toast.makeText(BookingDetails
                            .this, "Logging out...", Toast.LENGTH_SHORT).show();
                    getSharedPreferences("loginPrefs", MODE_PRIVATE).edit()
                            .clear()
                            .apply();

                    Intent intent = new Intent(BookingDetails.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (itemId == R.id.home) {
                    Toast.makeText(BookingDetails.this, "Home Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookingDetails.this, MainActivity2.class);
                    startActivity(intent);
                }
                if (itemId == R.id.navaboutUs) {
                    Toast.makeText(BookingDetails.this, "About Us", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookingDetails.this, AboutUs.class);
                    startActivity(intent);
                }
                drawerLayout.close();
                return false;
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
            Toast.makeText(BookingDetails.this, "Failed to Fetch", Toast.LENGTH_SHORT).show();
        });
        queue.add(jsonArrayRequest);
//--------------------------** SHAHD EDIT **-----------------------------

        initializeViews();
        fetchUserData();
        setupButtonListeners();
    }

    private void initializeViews() {
        // Initializing views from layout
        VINEditText = findViewById(R.id.vin);
        userNameEditText = findViewById(R.id.fullname);
        phoneEditText = findViewById(R.id.phone);
        emailEditText = findViewById(R.id.email);
        rentCostEditText = findViewById(R.id.rentcost);
        rentalperiod = findViewById(R.id.rentalperiod);
        bookCarButton = findViewById(R.id.bookBtn);
        calendarimage = findViewById(R.id.calendarimage);
        rentPriceTextView = findViewById(R.id.rentcost);
    }

    private void fetchUserData() {
        // Fetch user data from server
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", "");
        String getUserUrl = "http://10.0.2.2:80/project_android/get_users.php?email=" + savedEmail;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getUserUrl, null,
                response -> {
                    try {
                        if (response.length() > 0) {
                            JSONObject user = response.getJSONObject(0);
                            userNameEditText.setText(user.getString("UserName"));
                            emailEditText.setText(user.getString("Email"));
                            phoneEditText.setText(user.getString("Phone"));
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON parsing error", e);
                    }
                },
                error -> Toast.makeText(BookingDetails.this, "Failed to fetch user data.", Toast.LENGTH_SHORT).show()
        );
        queue.add(jsonArrayRequest);
        String vinNumber = getIntent().getStringExtra("vinNumber");
        double rentPrice = getIntent().getDoubleExtra("rentPrice", 0.0);

        // Use the values as needed
        // For example, set them to TextViews
        EditText vinNumberTextView = findViewById(R.id.vin); //edit text - shahd
        vinNumberTextView.setText(vinNumber);//shahd edit

        rentPriceTextView = findViewById(R.id.rentcost);
        rentPriceTextView.setText("" + rentPrice); //shahd edit

    }

    private void setupButtonListeners() {
        // Setting up listeners for buttons
        bookCarButton.setOnClickListener(v -> bookCar());
        calendarimage.setOnClickListener(v -> DatePickerdialog());
    }

    private void bookCar() {
        // Prepare and send booking data to server
        String VIN_number = VINEditText.getText().toString().trim();
        String userName = userNameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String startDate = rentalperiod.getText().toString().split(" - ")[0];  // Assuming date format is "start - end"
        String endDate = rentalperiod.getText().toString().split(" - ")[1];
         rentCost = rentCostEditText.getText().toString().trim();

        if (fieldsAreValid(userName, phone, email, startDate, endDate, rentCost, VIN_number)) {
            sendBookingToServer(VIN_number, rentCost, userName, email, phone, startDate, endDate);
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean fieldsAreValid(String... fields) {
        // Check if any fields are empty
        for (String field : fields) {
            if (field.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void sendBookingToServer(String VIN_number, String rentCost, String userName, String email, String phone, String startDate, String endDate) {
        // Send booking data to server via POST request
        String bookingUrl = "http://10.0.2.2:80/project_android/booking_car.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, bookingUrl,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        Toast.makeText(BookingDetails.this, jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON Exception", e);
                        Toast.makeText(BookingDetails.this, "Error parsing server response.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e(TAG, "Volley Error: " + error.toString());
                    Toast.makeText(BookingDetails.this, "Booking failed. Please try again.", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Prepare data to be sent via POST
                Map<String, String> params = new HashMap<>();
                params.put("VIN_number", VIN_number);
                params.put("rent_cost", rentCost);
                params.put("UserName", userName);
                params.put("Email", email);
                params.put("phone", phone);
                params.put("startDate", startDate);
                params.put("endDate", endDate);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void DatePickerdialog() {
        // Date picker dialog setup
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select a date range").setTheme(R.style.DatePickerDialogTheme);
        MaterialDatePicker<Pair<Long, Long>> datePicker = builder.build();
        datePicker.addOnPositiveButtonClickListener(selection -> {
            // Format and display the selected date range
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            rentalperiod.setText(sdf.format(new Date(selection.first)) + " - " + sdf.format(new Date(selection.second)));

            // Calculate the number of days between the selected dates
            long startDate = selection.first;
            long endDate = selection.second;
            long diffInMillies = endDate - startDate;
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        double rentPrice=Double.parseDouble(String.valueOf(rentPriceTextView.getText()));
            // Calculate the total rental price
            double totalRentalPrice = diffInDays * rentPrice;

            // Set the calculated rental price in the EditText
            rentPriceTextView.setText(String.valueOf(totalRentalPrice));
        });
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
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
