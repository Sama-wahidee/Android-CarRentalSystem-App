package edu.birzeit.www;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class UserReservations extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Orders> orderList;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservations);

        // Retrieve user email from shared preferences
        // Access SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", "example@example.com");  // Use default value if not found


        recyclerView = findViewById(R.id.userOrdersRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, orderList);
        recyclerView.setAdapter(orderAdapter);

        loadOrders(savedEmail);
    }

    private void loadOrders(String email) {
        String getUrl = "http://10.0.2.2:80/project_android/getUserOrders.php?email="+email;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);

                                int orderId = object.getInt("order_id");
                                long vinNumber = object.getLong("VIN_number");
                                String rentCost = object.getString("rent_cost");
                                String userName = object.getString("UserName");
                                String email = object.getString("Email");
                                long phone = object.getLong("phone");
                                String startDate = object.getString("startDate");
                                String endDate = object.getString("endDate");
                                String imageUrl = object.optString("image", "http://10.0.2.2:80/project_android/images/default.png");

                                Orders order = new Orders(orderId, vinNumber, rentCost, userName, email, phone, startDate, endDate, imageUrl);
                                orderList.add(order);
                            }
                            orderAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Toast.makeText(UserReservations.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleVolleyError(error);
            }
        });

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void handleVolleyError(VolleyError error) {
        if (error.networkResponse != null && error.networkResponse.data != null) {
            String errorMessage = new String(error.networkResponse.data);
            Log.e("Volley Error", errorMessage);
            Toast.makeText(this, "Server error: " + errorMessage, Toast.LENGTH_LONG).show();
        } else {
            Log.e("Volley Error", "Network error: " + error.getMessage());
            Toast.makeText(this, "Network error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
