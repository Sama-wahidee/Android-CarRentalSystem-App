package edu.birzeit.www;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_admin_account);

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

                // URL of the PHP script
                String url = "http://10.0.2.2:80/project_android/update_user.php";

                // Using Volley to send a POST request
                RequestQueue queue = Volley.newRequestQueue(manageAdminAcc_Activity.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        response -> {
                            // Handle the server's response
                            Toast.makeText(manageAdminAcc_Activity.this, response, Toast.LENGTH_SHORT).show();
                        },
                        error -> {
                            // Handle error
                            Toast.makeText(manageAdminAcc_Activity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                        }
                ) {
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

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
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
}
