package edu.birzeit.www;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class ApdateActivity extends AppCompatActivity {
    private static final String TAG = "AddCarActivity";
    private LinearLayout carInfoContent, rentalInfoContent, specificationsInfoContent;
    private Spinner carModelSpinner, yearSpinner, fuelTypeSpinner, numberOfSeatsSpinner, transmissionSpinner;
    private ImageView selectImageButton;
    private static final int PICK_IMAGE_REQUEST = 1;

    private ArrayAdapter<String> carModelAdapter;

    private ArrayAdapter<String> seatsAdapter;

    private static final String add_URL = "http://10.0.2.2:80/project_android/AddNewCar.php";
    private EditText descriptionEditText, vinEditText, RentalPriceEditText, colorEditText, topSpeedEditText;

    private Uri selectedImageUri;

    static  String model ;
    String imageUrl ;
    static int numberOfSeats ;
    static int topSpeed ;
    double rentPrice ;
    static String year ;
    String description ;
    String transmision;
    String fuel_type;
    String color;
    static String VIN_number ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_apdate);
        carInfoContent = findViewById(R.id.car_info_content);
        rentalInfoContent = findViewById(R.id.rental_info_content);
        specificationsInfoContent = findViewById(R.id.specifications_info_content);
        selectImageButton = findViewById(R.id.selectImageButton);

        TextView carInfoHeader = findViewById(R.id.car_info_header);
        TextView rentalInfoHeader = findViewById(R.id.rental_info_header);
        TextView specificationsInfoHeader = findViewById(R.id.specifications_info_header);

        carModelSpinner = findViewById(R.id.modelSpinner);
        yearSpinner = findViewById(R.id.yearSpinner);
        fuelTypeSpinner = findViewById(R.id.fuelTypeSpinner);
        numberOfSeatsSpinner = findViewById(R.id.numberOfSeatsSpinner);
        transmissionSpinner = findViewById(R.id.transmissionSpinner);


        descriptionEditText = findViewById(R.id.descriptionEditText);
        vinEditText = findViewById(R.id.vinEditText);
        RentalPriceEditText = findViewById(R.id.RentalPriceEditText);
        colorEditText = findViewById(R.id.colorEditText);
        topSpeedEditText = findViewById(R.id.topSpeedEditText);
        //--
        ArrayList<String> carModels = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.car_models)));
        carModelAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, carModels);
        carModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayList<String> carSeats = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.seatsNumber)));
        seatsAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, carSeats);
        seatsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //--
        ArrayAdapter<CharSequence> carYearAdapter = ArrayAdapter.createFromResource(this, R.array.years, R.layout.spinner_layout);
        carYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> fuelAdapter = ArrayAdapter.createFromResource(this, R.array.fuelType, R.layout.spinner_layout);
        fuelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        ArrayAdapter<CharSequence> seatsAdapter = ArrayAdapter.createFromResource(this, R.array.seatsNumber, R.layout.spinner_layout);
//        seatsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter<CharSequence> transmissionAdapter = ArrayAdapter.createFromResource(this, R.array.transType, R.layout.spinner_layout);
        transmissionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        carModelSpinner.setAdapter(carModelAdapter);
        transmissionSpinner.setAdapter(transmissionAdapter);
        yearSpinner.setAdapter(carYearAdapter);
        fuelTypeSpinner.setAdapter(fuelAdapter);
        numberOfSeatsSpinner.setAdapter(seatsAdapter);
        retrieveDataFromSharedPreferences();

    }
    public void backbtn(View view){
        Intent intent = new Intent(ApdateActivity.this, adminActivity.class );
        startActivity(intent);
    }

    private void retrieveDataFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("CarData", MODE_PRIVATE);

        // Retrieve data from SharedPreferences
        model = sharedPreferences.getString("model", "");
        imageUrl = sharedPreferences.getString("imageUrl", "");
        numberOfSeats = sharedPreferences.getInt("numberOfSeats", 0);
        topSpeed = sharedPreferences.getInt("topSpeed", 0);
        description = sharedPreferences.getString("description", "");
        transmision = sharedPreferences.getString("transmission", "");
        VIN_number = sharedPreferences.getString("VIN_number", "");
        fuel_type = sharedPreferences.getString("fuel_type", "");
        color = sharedPreferences.getString("color", "");
        year = sharedPreferences.getString("year", "");
        rentPrice = sharedPreferences.getFloat("rentPrice", 0.0f);
    }
}