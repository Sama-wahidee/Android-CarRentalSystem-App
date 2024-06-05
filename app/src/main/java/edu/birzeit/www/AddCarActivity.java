package edu.birzeit.www;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import edu.birzeit.www.R;

public class AddCarActivity extends AppCompatActivity {
    private static final String TAG = "AddCarActivity";
    private LinearLayout carInfoContent, rentalInfoContent, specificationsInfoContent;
    private Spinner carModelSpinner, yearSpinner, carMakeSpinner, fuelTypeSpinner, numberOfSeatsSpinner, availabilityStatusSpinner;
    private EditText engineTypeEditText, transmissionTypeEditText, mileageEditText, colorEditText;
    private ImageView selectImageButton;
    private static final int PICK_IMAGE_REQUEST = 1;

    // Define the adapter as a mutable list
    private ArrayAdapter<String> carModelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car);

        // Initialize views for car info section
        carInfoContent = findViewById(R.id.car_info_content);
        rentalInfoContent = findViewById(R.id.rental_info_content);
        specificationsInfoContent = findViewById(R.id.specifications_info_content); // Initialize specifications info content
        selectImageButton = findViewById(R.id.selectImageButton);

        // Find the header TextViews
        TextView carInfoHeader = findViewById(R.id.car_info_header);
        TextView rentalInfoHeader = findViewById(R.id.rental_info_header);
        TextView specificationsInfoHeader = findViewById(R.id.specifications_info_header); // Initialize specifications info header

        // Get references to the Spinners
//        carMakeSpinner = findViewById(R.id.makeSpinner);
        carModelSpinner = findViewById(R.id.modelSpinner);
        yearSpinner = findViewById(R.id.yeaSpinner);
        fuelTypeSpinner = findViewById(R.id.fuelTypeSpinner);
        numberOfSeatsSpinner = findViewById(R.id.numberOfSeatsSpinner);
//        availabilityStatusSpinner = findViewById(R.id.availabilityStatusSpinner);

        // Get references to the EditTexts
//        engineTypeEditText = findViewById(R.id.engineTypeEditText);
        transmissionTypeEditText = findViewById(R.id.transmissionTypeEditText);
        mileageEditText = findViewById(R.id.mileageEditText);
        colorEditText = findViewById(R.id.colorEditText);

        // Create ArrayAdapter for car make with custom spinner item layout
//        ArrayAdapter<CharSequence> carMakeAdapter = ArrayAdapter.createFromResource(this,
//                R.array.car_makes, R.layout.spinner_layout);
//        carMakeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create a mutable list for car models
        ArrayList<String> carModels = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.car_models)));
        carModelAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, carModels);
        carModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create ArrayAdapter for year with custom spinner item layout
        ArrayAdapter<CharSequence> carYearAdapter = ArrayAdapter.createFromResource(this,
                R.array.years, R.layout.spinner_layout);
        carYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create ArrayAdapter for status with custom spinner item layout
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.avilability_status, R.layout.spinner_layout);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create ArrayAdapter for fuel type with custom spinner item layout
        ArrayAdapter<CharSequence> fuelAdapter = ArrayAdapter.createFromResource(this,
                R.array.fuelType, R.layout.spinner_layout);
        fuelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create ArrayAdapter for number of seats with custom spinner item layout
        ArrayAdapter<CharSequence> seatsAdapter = ArrayAdapter.createFromResource(this,
                R.array.seatsNumber, R.layout.spinner_layout);
        seatsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter to the Spinners
//        carMakeSpinner.setAdapter(carMakeAdapter);
        carModelSpinner.setAdapter(carModelAdapter);
        yearSpinner.setAdapter(carYearAdapter);
//        availabilityStatusSpinner.setAdapter(statusAdapter);
        fuelTypeSpinner.setAdapter(fuelAdapter);
        numberOfSeatsSpinner.setAdapter(seatsAdapter);

        // Set OnClickListener to toggle visibility for car info header
        carInfoHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carInfoContent.getVisibility() == View.VISIBLE) {
                    carInfoContent.setVisibility(View.GONE);
                } else {
                    carInfoContent.setVisibility(View.VISIBLE);
                }
            }
        });

        // Set click listener for rental info header
        rentalInfoHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rentalInfoContent.getVisibility() == View.VISIBLE) {
                    rentalInfoContent.setVisibility(View.GONE);
                } else {
                    rentalInfoContent.setVisibility(View.VISIBLE);
                }
            }
        });

        // Set click listener for specifications info header
        specificationsInfoHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (specificationsInfoContent.getVisibility() == View.VISIBLE) {
                    specificationsInfoContent.setVisibility(View.GONE);
                } else {
                    specificationsInfoContent.setVisibility(View.VISIBLE);
                }
            }
        });

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), PICK_IMAGE_REQUEST);
            }
        });

        // Handle spinner item selection
        carModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("New Model")) {
                    showNewModelDialog();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
    //////////////////////////////////////////////////////////////////////////
    private void showNewModelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setTitle("Enter New Model");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
//    input.setBackgroundResource(R.drawable.dialog_shape); // Set the background here
        builder.setView(input);

        builder.setPositiveButton("OK",  new DialogInterface.OnClickListener()  {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newModel = input.getText().toString();
                Log.d(TAG, "New model entered: " + newModel);
                addNewModel(newModel);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });



        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                if (positiveButton != null && negativeButton != null) {
                    positiveButton.setTextColor(getResources().getColor(R.color.darkgray));
                    negativeButton.setTextColor(getResources().getColor(R.color.darkgray));
                }
            }
        });

        dialog.show();
    }

    /////////////////////////////////////////////////////////////////////
    private void addNewModel(String newModel) {
        try {
            // Ensure the adapter is of the correct type
            if (carModelSpinner.getAdapter() instanceof ArrayAdapter) {
                carModelAdapter.add(newModel);
                carModelAdapter.notifyDataSetChanged();
                carModelSpinner.setSelection(carModelAdapter.getPosition(newModel)); // Select the new model
                Log.d(TAG, "New model added to spinner: " + newModel);
            } else {
                Log.e(TAG, "Adapter is not an instance of ArrayAdapter");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error adding new model: ", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Handle the selected image here
            Uri imageUri = data.getData();
            // Do something with the imageUri, such as displaying it in an ImageView
        }
    }

//    public void openUserManage(View view) {
//        Intent intent = new Intent(this, manageUserAcc_Activity.class);
//        startActivity(intent);
//    }
}
