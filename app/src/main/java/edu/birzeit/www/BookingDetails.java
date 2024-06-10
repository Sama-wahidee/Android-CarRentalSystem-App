package edu.birzeit.www;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.datepicker.MaterialDatePicker;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        VINEditText = findViewById(R.id.vin);
        userNameEditText = findViewById(R.id.fullname);
        phoneEditText = findViewById(R.id.phone);
        emailEditText = findViewById(R.id.email);
        rentCostEditText = findViewById(R.id.rentcost);
        rentalperiod = findViewById(R.id.rentalperiod);
        bookCarButton = findViewById(R.id.bookBtn);
        calendarimage = findViewById(R.id.calendarimage);

        // Retrieve user email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", "");

        // Make HTTP request to fetch user data
        String getUserUrl = "http://10.0.2.2:80/project_android/get_users.php?email=" + savedEmail;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getUserUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        JSONObject user = response.getJSONObject(0); // Assuming only one user is returned
                        String fetchedUsername = user.getString("UserName");
                        String fetchedEmail = user.getString("Email");
                        String fetchedPhone = user.getString("Phone");
//                        String fetchedAddress = user.getString("Address");

                        // Autofill EditTexts
                        userNameEditText.setText(fetchedUsername);
                        emailEditText.setText(fetchedEmail);
                        phoneEditText.setText(fetchedPhone);
                        // Address field in BookingDetails layout should be added
                        // Assuming the ID of address EditText is "address"
//                        EditText addressEditText = findViewById(R.id.address);
//                        addressEditText.setText(fetchedAddress);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Toast.makeText(BookingDetails.this, "Failed to Fetch", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);

        // Handle booking button click
        bookCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookCar();
            }
        });

        String vinNumber = getIntent().getStringExtra("vinNumber");
       double rentPrice = getIntent().getDoubleExtra("rentPrice", 0.0);

        // Use the values as needed
        // For example, set them to TextViews
        EditText vinNumberTextView = findViewById(R.id.vin);
        vinNumberTextView.setText("VIN Number: " + vinNumber);

         rentPriceTextView = findViewById(R.id.rentcost);
        rentPriceTextView.setText("Rent Price: " + rentPrice);



        // Handle calendar image click
        calendarimage.setOnClickListener(view -> DatePickerdialog());
    }

    private void bookCar() {
        // Get values from EditText fields
        final String VIN_number = VINEditText.getText().toString().trim();
        final String UserName = userNameEditText.getText().toString().trim();
        final String phone = phoneEditText.getText().toString().trim();
        final String Email = emailEditText.getText().toString().trim();
        final String order_date = rentalperiod.getText().toString().trim();
        final String rent_cost = rentCostEditText.getText().toString().trim();

        // Make sure all fields are filled
        if (UserName.isEmpty() || phone.isEmpty() || Email.isEmpty() || order_date.isEmpty() || rent_cost.isEmpty() || VIN_number.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Make HTTP request to book the car
        String BOOKING_URL = "http://192.168.56.1/project_android/booking_car.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BOOKING_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String message = jsonResponse.getString("message");
                    Log.d(TAG, "Response: " + message);
                    Toast.makeText(BookingDetails.this, message, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.toString());
                Toast.makeText(BookingDetails.this, "Booking failed. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("VIN_number", VIN_number);
                params.put("rent_cost", rent_cost);
                params.put("UserName", UserName);
                params.put("Email", Email);
                params.put("phone", phone);
                params.put("startDate", startDate);
                params.put("endDate", endDate);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    String startDate;
    String endDate ;
    private void DatePickerdialog() {
        // Creating a MaterialDatePicker builder for selecting a date range
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select a date range");

        // Apply custom style here
        builder.setTheme(R.style.DatePickerDialogTheme);

        // Building the date picker dialog
        MaterialDatePicker<Pair<Long, Long>> datePicker = builder.build();

        // Setting up the listener for the positive button click
        datePicker.addOnPositiveButtonClickListener(selection -> {
            try {
                // Retrieving the selected start and end dates
                Long startDateV = selection.first;
                Long endDateV = selection.second;

                if (startDateV != null && endDateV != null) {
                    // Formatting the selected dates as strings
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String startDate = sdf.format(new Date(startDateV));
                    String endDate = sdf.format(new Date(endDateV));

                    // Creating the date range string
                    String selectedDateRange = startDate + " - " + endDate;

                    // Displaying the selected date range in the TextView
                    rentalperiod.setText(selectedDateRange);

                    // Calculate the number of days between the selected dates
                    long diffInMillis = endDateV - startDateV;
                    long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);

                    // Parse the rent price from the rentPriceTextView
                    String rentPriceText = rentPriceTextView.getText().toString().trim();
                    Log.d("DatePickerdialog", "Rent Price Text: " + rentPriceText);

                    // Ensure the rent price text is a valid number
                    if (rentPriceText.startsWith("Rent Price: ")) {
                        rentPriceText = rentPriceText.replace("Rent Price: ", "").trim();
                    }

                    double rentPrice = Double.parseDouble(rentPriceText);

                    // Calculate the total rent price
                    double totalRentPrice = diffInDays * rentPrice;

                    // Set the total rent price in the totalRentTextView
                    rentPriceTextView.setText("Total Rent Price: " + totalRentPrice);
                } else {
                    Toast.makeText(this, "Please select a valid date range", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Error parsing the rent price: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("DatePickerdialog", "Error parsing rent price", e);
            } catch (Exception e) {
                Toast.makeText(this, "An unexpected error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("DatePickerdialog", "Unexpected error", e);
            }
        });

        // Showing the date picker dialog
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }





}