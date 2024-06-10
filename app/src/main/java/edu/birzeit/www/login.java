package edu.birzeit.www;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class login extends AppCompatActivity {
    private static final String GET_USERS_URL = "http://10.0.2.2:80/project_android/getAllusers.php";
    public static List<User> users = new ArrayList<>();
    private EditText emailEditText;
    private EditText passwordEditText;
    public static boolean isAdmin;
    public  static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextTextPassword);

        // Check login state
        if (getSharedPreferences("loginPrefs", MODE_PRIVATE).getBoolean("isLoggedIn", false)) {
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
            finish(); // Close the login activity
            return;
        }






        //Shahd Update starts
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", emailEditText.getText().toString());
        editor.apply();
        //Shahd Update ends


        // Fetch user data
        loadUsers();

        // Navigate to signup activity
        findViewById(R.id.subt).setOnClickListener(v -> {
            Intent intent = new Intent(this, signup.class);
            startActivity(intent);
        });

        // Handle login button click
        findViewById(R.id.libt).setOnClickListener(v -> {
            handleLogin();
        });

        // Apply window insets
        applyWindowInsets();
    }


    private void applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadUsers() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_USERS_URL,
                response -> new Thread(() -> parseUserData(response)).start(),
                error -> {
                    Log.e("Volley Error", "Volley error: " + error.getMessage());
                    Toast.makeText(login.this, "Volley error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                });
        requestQueue.add(stringRequest);
    }

    private void parseUserData(String response) {
        try {
            JSONArray array = new JSONArray(response);

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String username = object.optString("UserName", "Unknown");
                String email = object.optString("Email", "No email available");
                String password = object.optString("Password", "");
                String phone = object.optString("Phone", "");
                String address = object.optString("Address", "");

                User user = new User( username, email, password, phone, address);
                users.add(user);
            }

            runOnUiThread(() -> {
                Log.d("UserData", "User List Size: " + users.size());
                //Toast.makeText(getApplicationContext(), "User List Size: " + users.size(), Toast.LENGTH_LONG).show();
            });

        } catch (JSONException e) {
            Log.e("JSON Parsing Error", "JSON parsing error: " + e.getMessage());
            runOnUiThread(() -> Toast.makeText(login.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show());
        }
    }

    private void handleLogin() {
        String enteredEmail = emailEditText.getText().toString().trim();
        String enteredPassword = passwordEditText.getText().toString().trim();

        if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        User foundUser = null;
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(enteredEmail)) {
                foundUser = user;
                break;
            }
        }

        if (foundUser == null) {
            Toast.makeText(this, "Email is incorrect", Toast.LENGTH_SHORT).show();
            emailEditText.setText("");
            passwordEditText.setText("");
        } else if (!foundUser.getPassword().equals(enteredPassword)) {
            Toast.makeText(this, "Password is incorrect", Toast.LENGTH_SHORT).show();
            passwordEditText.setText("");
        } else {
            // Check if the user is an admin
            isAdmin = enteredEmail.endsWith("@a.rent");
            currentUser = foundUser;

            // Save login state
            getSharedPreferences("loginPrefs", MODE_PRIVATE).edit()
                    .putBoolean("isLoggedIn", true)
                    .putString("userEmail", enteredEmail)
                    .apply();

            // Save email to user_prefs
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", enteredEmail);
            editor.apply();

            if (isAdmin) {
                Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "User Login Successful", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        }
    }

    ///
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
