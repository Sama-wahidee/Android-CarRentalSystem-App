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
        String rentCost = rentCostEditText.getText().toString().trim();

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
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            rentalperiod.setText(sdf.format(new Date(selection.first)) + " - " + sdf.format(new Date(selection.second)));
        });
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }
}
