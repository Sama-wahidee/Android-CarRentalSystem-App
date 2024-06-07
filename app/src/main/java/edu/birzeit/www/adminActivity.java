package edu.birzeit.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class adminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_activity);
        ImageView carImage = findViewById(R.id.carimg);
        TextView carName = findViewById(R.id.nametxt);
        // TextView carSeats = findViewById(R.id.car_seats);
        // TextView carSpeed = findViewById(R.id.car_speed);
        TextView carPrice = findViewById(R.id.pricetxt);

        TextView carDescription = findViewById(R.id.car_description);

        // Retrieve data from intent
        String name = getIntent().getStringExtra("model");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        int numberOfSeats = getIntent().getIntExtra("numberOfSeats", 0);
        int topSpeed = getIntent().getIntExtra("topSpeed", 0);
        double rentPrice = getIntent().getDoubleExtra("rentPrice", 0.0);
        String model = getIntent().getStringExtra("year");
        String description = getIntent().getStringExtra("description");

        // Set data to views
        Glide.with(this).load(imageUrl).into(carImage);
        carName.setText(name);
        // carSeats.setText("Seats: " + numberOfSeats);
        // carSpeed.setText("Top Speed: " + topSpeed + " km/h");
        carPrice.setText("Price per day: $" + rentPrice);
        // carModel.setText("Model: " + model);
        carDescription.setText("Description: " + description);

    }
    public void backbtn(View view){
        Intent intent = new Intent(adminActivity.this, MainActivity2.class );
        startActivity(intent);
    }
    public void updatebtn(View view){
        Intent intent = new Intent(adminActivity.this, ApdateActivity.class );
        startActivity(intent);
    }

}