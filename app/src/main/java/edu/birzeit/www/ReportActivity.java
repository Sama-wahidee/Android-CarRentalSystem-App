package edu.birzeit.www;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class ReportActivity extends AppCompatActivity {
    private TextView totalCarsTxt, rentedCarsTxt, availableCarsTxt,customerTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        totalCarsTxt = findViewById(R.id.TotalCarsTxt);
        rentedCarsTxt = findViewById(R.id.rentedCarsTxt);
        availableCarsTxt = findViewById(R.id.AvilabileCarsTxt);
        customerTxt = findViewById(R.id.customerTxt);

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

                        totalCarsTxt.setText(String.valueOf(totalCars));
                        rentedCarsTxt.setText(String.valueOf(rentedCars));
                        availableCarsTxt.setText(String.valueOf(availableCars));
                        customerTxt.setText(String.valueOf(gmailUsers));

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
}

