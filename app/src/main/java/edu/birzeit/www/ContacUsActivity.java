package edu.birzeit.www;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ContacUsActivity extends AppCompatActivity {

    EditText nameField, emailField;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        nameField = findViewById(R.id.nameField);
        emailField = findViewById(R.id.emailField);
        submitButton=findViewById(R.id.submitButton);


        // Retrieve email from SharedPreferences
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
                        JSONObject user = response.getJSONObject(0); //  there's only one user returned
                        String fetchedUsername = user.getString("UserName");
                        String fetchedEmail = user.getString("Email");

                        // Autofill EditTexts
                        nameField.setText(fetchedUsername);
                        emailField.setText(fetchedEmail);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, error -> {
            // Handle error
            Toast.makeText(ContacUsActivity.this, "Failed to Fetch", Toast.LENGTH_SHORT).show();
        });
        queue.add(jsonArrayRequest);
    }

    public void subBtnOpen(View view) {
        EditText nameField = findViewById(R.id.nameField);
        EditText emailField = findViewById(R.id.emailField);
        EditText subjectField = findViewById(R.id.subjectField);
        EditText messageField = findViewById(R.id.messageField);

        // Check if any of the fields are empty
        if (nameField.getText().toString().isEmpty() ||
                emailField.getText().toString().isEmpty() ||
                subjectField.getText().toString().isEmpty() ||
                messageField.getText().toString().isEmpty()) {
            // If any field is empty, show a toast message
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            // All fields are filled, show the "Message sent" toast message
            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();

            // Add your logic to handle the form submission here
        }
    }


}
