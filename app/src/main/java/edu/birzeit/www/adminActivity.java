package edu.birzeit.www;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adminActivity extends AppCompatActivity {

    static String VIN_number;
    private static final String DELETE_URL = "http://10.0.2.2:80/project_android/delete_car.php?VIN_number=";
    private static final String UPDATE_URL = "http://10.0.2.2:80/project_android/update_car.php";
    static String model;
    String imageUrl;
    static int numberOfSeats;
    static int topSpeed;
    double rentPrice;
    static String year;
    String description;
    String transmission;
    String fuel_type;
    String color;
    private CardAdapter adapter;
    private Button btnupdate;
    RecyclerView recyclerView;
    Button confirmUpdateButton;
    EditText desText;
    EditText modelTxt;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
     String updatedVin ;
    String updatedFuelType ;
    String updatedTransmission ;
     String updatedNumberOfSeats ;
    String updatedRentPrice ;
     String updatedColor ;
    String updatedYear ;
     String updatedTopSpeed ;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_activity);

        recyclerView = findViewById(R.id.recyclerDetails);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        desText = findViewById(R.id.car_description);
        modelTxt = findViewById(R.id.modeltxt);

        ImageView carImage = findViewById(R.id.carimg);
        TextView carName = findViewById(R.id.modeltxt);
        TextView carDescription = findViewById(R.id.car_description);

        // Retrieve data from intent
        model = getIntent().getStringExtra("model");
        imageUrl = getIntent().getStringExtra("imageUrl");
        numberOfSeats = getIntent().getIntExtra("numberOfSeats", 0);
        topSpeed = getIntent().getIntExtra("topSpeed", 0);
        rentPrice = getIntent().getDoubleExtra("rentPrice", 0.0);
        year = String.valueOf(getIntent().getIntExtra("year", 0));
        description = getIntent().getStringExtra("description");
        transmission = getIntent().getStringExtra("transmission");
        VIN_number = getIntent().getStringExtra("vin");
        fuel_type = getIntent().getStringExtra("fuel_type");
        color = getIntent().getStringExtra("color");

        Glide.with(this).load(imageUrl).into(carImage);
        carName.setText(model);
        carDescription.setText("Description: " + description);

        initializeCardList(recyclerView);

        ImageView deleteButton = findViewById(R.id.btndelete);
        deleteButton.setOnClickListener(this::showConfirmationDialog);
        confirmUpdateButton = findViewById(R.id.save_button);
        ImageView selectImageButton = findViewById(R.id.selectImageButton);

        selectImageButton.setOnClickListener(v -> selectImage());

    }

    public void backbtn(View view) {
        Intent intent = new Intent(adminActivity.this, MainActivity2.class);
        startActivity(intent);
    }

    public void setConfirmUpdateButton(View view) {
        updateCarDetails();
    }

    public void updatebtn(View view) {

        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
        LinearLayout photolayout = findViewById(R.id.photolayout);
        modelTxt.setFocusable(true);
        modelTxt.setFocusableInTouchMode(true);
        desText.setFocusable(true);
        desText.setFocusableInTouchMode(true);
        buttonLayout.setVisibility(View.VISIBLE);
        photolayout.setVisibility(View.VISIBLE);
        if (adapter != null) {
            adapter.setClickable(true);
            adapter.notifyDataSetChanged();
        }
    }

    private void initializeCardList(RecyclerView recyclerView) {
        List<CardModel> cardList = new ArrayList<>();
        cardList.add(new CardModel(R.drawable.fuel_type, "Fuel Type", fuel_type));
        cardList.add(new CardModel(R.drawable.year, "Year", String.valueOf(year)));
        cardList.add(new CardModel(R.drawable.capacity, "Capacity", String.valueOf(numberOfSeats)));
        cardList.add(new CardModel(R.drawable.speed, "Top Speed", String.valueOf(topSpeed)));
        cardList.add(new CardModel(R.drawable.gear, "transmission", transmission));
        cardList.add(new CardModel(R.drawable.numcar, "Car Number", VIN_number));
        cardList.add(new CardModel(R.drawable.colour, "Color", color));
        cardList.add(new CardModel(R.drawable.price, "Rent Price", String.valueOf(rentPrice)));

        adapter = new CardAdapter(this, cardList);
        recyclerView.setAdapter(adapter);
    }



    private void showConfirmationDialog(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Car")
                .setMessage("Are you sure you want to delete this car?")
                .setPositiveButton("Yes", (dialog, which) -> deleteCar())
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteCar() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = DELETE_URL + VIN_number;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    switch (response.trim()) {
                        case "success":
                            Toast.makeText(this, "Car deleted successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(adminActivity.this, MainActivity2.class);
                            startActivity(intent);
                            break;
                        case "error":
                            Toast.makeText(this, "Failed to delete car", Toast.LENGTH_LONG).show();
                            break;
                        case "missing_vin":
                            Toast.makeText(this, "VIN number is missing", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(this, "Unexpected response: " + response, Toast.LENGTH_LONG).show();
                            break;
                    }
                },
                error -> Toast.makeText(this, "Error deleting car: " + error.getMessage(), Toast.LENGTH_LONG).show());

        queue.add(stringRequest);
    }

    public void discardbtn(View view) {
        initializeCardList(recyclerView);
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            ImageView carImage = findViewById(R.id.carimg);
            carImage.setImageURI(selectedImageUri); // Display selected image
        }
    }

    private void updateCarDetails() {
        List<CardModel> updatedCardList = adapter.getUpdatedCardList();
        final String updatedModel = modelTxt.getText().toString();
        final String updatedDescription = desText.getText().toString();
        for (CardModel cardModel : updatedCardList) {
            String value = cardModel.getValue();
            int imageId = cardModel.getImageResId(); // Get the imageId
            if (imageId == R.drawable.fuel_type) {
                updatedFuelType = value;
            } else if (imageId == R.drawable.year) {
                updatedYear = value;
            } else if (imageId == R.drawable.capacity) {
                updatedNumberOfSeats = value;
            } else if (imageId == R.drawable.speed) {
                updatedTopSpeed = value;
            } else if (imageId == R.drawable.gear) {
                updatedTransmission = value;
            } else if (imageId == R.drawable.numcar) {
                updatedVin = value;
            } else if (imageId == R.drawable.colour) {
                updatedColor = value;
            } else if (imageId == R.drawable.price) {
                updatedRentPrice = value;
            }
            // Add more else-if blocks for other image IDs if needed
        }


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_URL,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String message = jsonResponse.getString("message");
                        if ("Car updated successfully".equals(message)) {
                            Toast.makeText(adminActivity.this, "Car Updated Successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(adminActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("UpdateCar", "Error: " + error.getMessage())) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("model", updatedModel);
                params.put("description", updatedDescription);
                params.put("vin", updatedVin);
                params.put("fuelType", updatedFuelType);
                params.put("transmission", updatedTransmission);
                params.put("numberOfSeats", updatedNumberOfSeats);
                params.put("rentPrice", updatedRentPrice);
                params.put("color", updatedColor);
                params.put("year", updatedYear);
                params.put("topSpeed", updatedTopSpeed);
                if (selectedImageUri != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        byte[] bytes = new byte[inputStream.available()];
                        inputStream.read(bytes);
                        inputStream.close();
                        String imageBase64 = Base64.encodeToString(bytes, Base64.DEFAULT);
                        params.put("image", imageBase64);
                        params.put("imageUpdated", "true");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    params.put("image", imageUrl);
                    params.put("imageUpdated", "false");
                }
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
