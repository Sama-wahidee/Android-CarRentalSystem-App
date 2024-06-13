package edu.birzeit.www;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminOrders extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Orders> orderList;
    String email;
    String vin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);

        recyclerView = findViewById(R.id.orderRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, orderList);
        recyclerView.setAdapter(orderAdapter);

        loadOrders(orderAdapter);
    }

    private void loadOrders(final OrderAdapter adapter) {
        final List<Orders> orders = new ArrayList<>();

        String getUrl = "http://10.0.2.2:80/project_android/order_get.php?";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, getUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);

                                int orderId = object.optInt("order_id", 0);
                                long vinNumber = object.optLong("vin_number", 0);
                                String rentCost = object.optString("rent_cost", "0.00");
                                String userName = object.optString("UserName", "No name available");
                                String email = object.optString("Email", "No email available");
                                long phone = object.optLong("phone", 0);
                                String startDate = object.optString("startDate", "No start date available");
                                String endDate = object.optString("endDate", "No end date available");
                                String imageUrl = object.optString("image", "http://10.0.2.2:80/project_android/images/default.png");

                                Orders order = new Orders(orderId, vinNumber, rentCost, userName, email, phone, startDate, endDate, imageUrl);
                                orders.add(order);
                            }

                            // Notify the adapter that data has been changed
                            adapter.updateOrders(orders);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AdminOrders.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Log.e("Volley Error", "Timeout Error: " + error.getMessage());
                    Toast.makeText(AdminOrders.this, "Timeout Error: Please check your internet connection", Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {
                    Log.e("Volley Error", "No Connection Error: " + error.getMessage());
                    Toast.makeText(AdminOrders.this, "No Connection Error: Please check your internet connection", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Log.e("Volley Error", "Server Error: " + error.getMessage());
                    Toast.makeText(AdminOrders.this, "Server Error: Please try again later", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Log.e("Volley Error", "Network Error: " + error.getMessage());
                    Toast.makeText(AdminOrders.this, "Network Error: Please check your internet connection", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Log.e("Volley Error", "Parse Error: " + error.getMessage());
                    Toast.makeText(AdminOrders.this, "Parse Error: Please try again later", Toast.LENGTH_LONG).show();
                } else {
                    Log.e("Volley Error", "Unknown Volley error: " + error.getMessage());
                    Toast.makeText(AdminOrders.this, "Unknown Volley error: Please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });

        Volley.newRequestQueue(AdminOrders.this).add(stringRequest);
    }
}

