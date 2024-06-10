package edu.birzeit.www;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

    private static final String TAG = "AdminOrders";
    private static final String FETCH_ORDERS_URL = "http://192.168.56.1/project_android/admin_orders.php";
    private static final String FETCH_ORDER_DETAILS_URL = "http://192.168.56.1/project_android/order_details.php?order_id=";
    private ListView ordersListView;
    private ArrayAdapter<String> ordersAdapter;
    private List<String> orderTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);

        ordersListView = findViewById(R.id.ordersListView);
        ordersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orderTitles);
        ordersListView.setAdapter(ordersAdapter);

        fetchOrders();

        ordersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String orderId = orderTitles.get(position).replace("Order : ", "");
                fetchOrderDetails(orderId);
            }
        });
    }

    private void fetchOrders() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                FETCH_ORDERS_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        orderTitles.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                String orderId = response.getString(i);
                                orderTitles.add("Order : " + orderId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ordersAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdminOrders.this, "Failed to fetch orders", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error: " + error.getMessage());
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    private void fetchOrderDetails(String orderId) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = FETCH_ORDER_DETAILS_URL + orderId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showOrderDetailsBottomSheet(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdminOrders.this, "Failed to fetch order details", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error: " + error.getMessage());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void showOrderDetailsBottomSheet(JSONObject orderDetails) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.activity_bottom_dialog_orders, null);

        try {
            ((EditText) bottomSheetView.findViewById(R.id.username)).setText(orderDetails.getString("UserName"));
            ((EditText) bottomSheetView.findViewById(R.id.userphone)).setText(orderDetails.getString("phone"));
            ((EditText) bottomSheetView.findViewById(R.id.rentdate)).setText(orderDetails.getString("startDate") + " - " + orderDetails.getString("endDate"));
            ((EditText) bottomSheetView.findViewById(R.id.rentcost)).setText(orderDetails.getString("rent_cost"));

            ((EditText) bottomSheetView.findViewById(R.id.carvin)).setText(orderDetails.getString("VIN_number"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
}
