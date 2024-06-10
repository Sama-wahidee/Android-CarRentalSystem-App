package edu.birzeit.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class userActivty extends AppCompatActivity {
    static String VIN_number ;
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

    List<CardModel> cardList = new ArrayList<>();

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton imageButton;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mainua);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
        imageButton = findViewById(R.id.buttonDrawer);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu = navigationView.getMenu();
                onCreateOptionsMenu(menu);
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.AdminSettingOption) {
                    Toast.makeText(userActivty.this, "Account Setting Option", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(userActivty.this, manageAdminAcc_Activity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.addCarOption) {
                    Toast.makeText(userActivty.this, "Add Car Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(userActivty.this, AddCarActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.reportOption) {
                    Toast.makeText(userActivty.this, "Report Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(userActivty.this, ReportActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.orders) {
                    Toast.makeText(userActivty.this, "Admin Orders Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(userActivty.this, AdminOrders.class);
                    startActivity(intent);
                }
                if (itemId == R.id.reservations) {
                    Toast.makeText(userActivty.this, "Reservations Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(userActivty.this, UserReservations.class);
                    startActivity(intent);
                }
                if (itemId == R.id.ContactUsOption) {
                    Toast.makeText(userActivty.this, "Contact Us Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(userActivty.this, ContacUsActivity.class);
                    startActivity(intent);
                }

                if (itemId == R.id.logout) {
                    Toast.makeText(userActivty.this, "Logging out...", Toast.LENGTH_SHORT).show();
                    getSharedPreferences("loginPrefs", MODE_PRIVATE).edit()
                            .clear()
                            .apply();

                    Intent intent = new Intent(userActivty.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (itemId == R.id.home) {
                    Toast.makeText(userActivty.this, "Home Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(userActivty.this, MainActivity2.class);
                    startActivity(intent);
                }
                drawerLayout.close();
                return false;
            }


        });

        RecyclerView recyclerView = findViewById(R.id.recyclerInfo);
        // Set layout manager to horizontal
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ImageView carImage = findViewById(R.id.carimg);
        TextView carName = findViewById(R.id.modeltxt);
        // TextView carSeats = findViewById(R.id.car_seats);
        // TextView carSpeed = findViewById(R.id.car_speed);
        TextView carPrice = findViewById(R.id.pricetxt);

        TextView carDescription = findViewById(R.id.car_description);
        Button bookBtn = findViewById(R.id.bookbtn);
        model = getIntent().getStringExtra("model");
        imageUrl = getIntent().getStringExtra("imageUrl");
        numberOfSeats = getIntent().getIntExtra("numberOfSeats", 0);
        topSpeed = getIntent().getIntExtra("topSpeed", 0);
        rentPrice = getIntent().getDoubleExtra("rentPrice", 0.0);
        year = String.valueOf(getIntent().getIntExtra("year",0));
        description = getIntent().getStringExtra("description");
        transmision= getIntent().getStringExtra("transmission");
        VIN_number = getIntent().getStringExtra("vin");
        fuel_type=getIntent().getStringExtra("fuel_type");
        color=getIntent().getStringExtra("color");

        // Set data to views
        Glide.with(this).load(imageUrl).into(carImage);
        carName.setText(model);
        // carSeats.setText("Seats: " + numberOfSeats);
        // carSpeed.setText("Top Speed: " + topSpeed + " km/h");
        carPrice.setText("Price per day: $" + rentPrice);
        // carModel.setText("Model: " + model);
        carDescription.setText("Description: " + description);
        initializeCardList(recyclerView);
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userActivty.this, BookingDetails.class);
                startActivity(intent);
            }
        });

    }

    public void backbtn(View view){
        Intent intent = new Intent(userActivty.this, MainActivity2.class );
        startActivity(intent);
    }
    private void initializeCardList(RecyclerView recyclerView) {

        cardList.add(new CardModel(R.drawable.fuel_type, "Fuel Type",fuel_type ));
        cardList.add(new CardModel(R.drawable.year, "Year", String.valueOf(year)));
        cardList.add(new CardModel(R.drawable.capacity, "Capacity", String.valueOf(numberOfSeats)));
        cardList.add(new CardModel(R.drawable.speed, "Top Speed", String.valueOf(topSpeed)));
        cardList.add(new CardModel(R.drawable.gear, "Transmission",transmision));
        cardList.add(new CardModel(R.drawable.numcar, "Car Number",VIN_number));
        cardList.add(new CardModel(R.drawable.colour, "Color",color));
        cardList.add(new CardModel(R.drawable.price, "Rent Price",String.valueOf(rentPrice)));

        CardAdapter adapter = new CardAdapter(this,cardList);
        recyclerView.setAdapter(adapter);
    }

//    public void bookbtn(View view){


    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();

        getMenuInflater().inflate(R.menu.drawer_items, menu);

        MenuItem addCarItem = menu.findItem(R.id.addCarOption);
        addCarItem.setVisible(login.isAdmin); // Hide/show add car menu item based on isAdmin value

        MenuItem ordersItem = menu.findItem(R.id.orders);
        ordersItem.setVisible(login.isAdmin); // Hide/show orders menu item based on isAdmin value

        MenuItem reportItem = menu.findItem(R.id.reportOption);
        reportItem.setVisible(login.isAdmin); // Hide/show report menu item based on isAdmin value

        MenuItem reverItem = menu.findItem(R.id.reservations);
        reportItem.setVisible(login.isAdmin);
        return true;

    }
}