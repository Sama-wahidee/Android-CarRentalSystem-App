package edu.birzeit.www;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextPhone;
    private EditText editTextAddress;
    private Button signupButton;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "SignupPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_CONFIRM_PASSWORD = "confirmPassword";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_ADDRESS = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewById(R.id.libt).setOnClickListener(v -> {
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
        });

        // Initialize the views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextAddress = findViewById(R.id.editTextAddress);
        signupButton = findViewById(R.id.signup);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Restore the saved state
        if (sharedPreferences.contains(KEY_USERNAME)) {
            editTextUsername.setText(sharedPreferences.getString(KEY_USERNAME, ""));
            editTextEmail.setText(sharedPreferences.getString(KEY_EMAIL, ""));
            editTextPassword.setText(sharedPreferences.getString(KEY_PASSWORD, ""));
            editTextConfirmPassword.setText(sharedPreferences.getString(KEY_CONFIRM_PASSWORD, ""));
            editTextPhone.setText(sharedPreferences.getString(KEY_PHONE, ""));
            editTextAddress.setText(sharedPreferences.getString(KEY_ADDRESS, ""));
        }

        // Set click listener on the signup button
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save the current state of the EditTexts
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, editTextUsername.getText().toString());
        editor.putString(KEY_EMAIL, editTextEmail.getText().toString());
        editor.putString(KEY_PASSWORD, editTextPassword.getText().toString());
        editor.putString(KEY_CONFIRM_PASSWORD, editTextConfirmPassword.getText().toString());
        editor.putString(KEY_PHONE, editTextPhone.getText().toString());
        editor.putString(KEY_ADDRESS, editTextAddress.getText().toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Restore the state of the EditTexts
        editTextUsername.setText(sharedPreferences.getString(KEY_USERNAME, ""));
        editTextEmail.setText(sharedPreferences.getString(KEY_EMAIL, ""));
        editTextPassword.setText(sharedPreferences.getString(KEY_PASSWORD, ""));
        editTextConfirmPassword.setText(sharedPreferences.getString(KEY_CONFIRM_PASSWORD, ""));
        editTextPhone.setText(sharedPreferences.getString(KEY_PHONE, ""));
        editTextAddress.setText(sharedPreferences.getString(KEY_ADDRESS, ""));
    }

    private void createUser() {
        // Read the values from the EditTexts
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        // Check if any field is empty
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() ||
                confirmPassword.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            showDialog("All fields are required");
            return;
        }

        // Validate the input
        if (!isValidEmail(email)) {
            showDialog("Invalid email ");
            return;
        }

        if (password.length() < 8 || password.length() > 20) {
            showDialog("Password must be between 8 and 20 characters");
            return;
        }

        if (!isValidPhone(phone)) {
            showDialog("Invalid phone number ");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showDialog("Passwords do not match");
            return;
        }

        // Check if the email already exists
        if (emailExists(email)) {
            showDialog("Email already exists");
            return;
        }

        // Create the User object
        User user = new User(username, email, password, phone, address);

        // Send data to the server
        registerUser(user);
    }

    // Method to validate email format
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Method to validate phone number format
    private boolean isValidPhone(String phone) {
        return phone.matches("^\\d{12}$");
    }

    private void registerUser(final User user) {
        String url = "http://10.0.2.2:80/project_android/sign_up.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the server response
                        showDialog("Response: " + response);
                        // Add the user to the list if registration is successful
                        login.users.add(user);
                        Intent intent = new Intent(signup.this, login.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        showDialog("Error: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("UserName", user.getUsername());
                params.put("Email", user.getEmail());
                params.put("Password", user.getPassword());
                params.put("phone", user.getPhone());
                params.put("Address", user.getAddress());
                return params;
            }
        };

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public static boolean emailExists(String email) {
        for (User user : login.users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do nothing
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
