package edu.birzeit.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import edu.birzeit.www.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity implements recyclerinterface {

DrawerLayout drawerLayout;
ImageButton imageButton;
NavigationView navigationView;
///
    private ActionBarDrawerToggle toggle;
////
RecyclerView recyclerView;
Adapter adapter;
List<Car> cars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        drawerLayout=findViewById(R.id.drawerlayout);
        imageButton= findViewById(R.id.buttonDrawer);
        navigationView=findViewById(R.id.navigationView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cars = new ArrayList<Car>();
        Car newCar = new Car("BMW", R.drawable.bmwc, 5, 200, 100,"m4");
        Car newCar1 = new Car("Cadillac ", R.drawable.cadillacc, 7, 200, 200,"Escalade ");

        cars.add(newCar);
        cars.add(newCar1);

        adapter = new Adapter(cars , this);
        recyclerView.setAdapter(adapter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId= menuItem.getItemId();
                if (itemId==R.id.AdminSettingOption){
                    Toast.makeText(MainActivity2.this, "Account Setting Option",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, manageAdminAcc_Activity.class);
                    startActivity(intent);
                }
                if (itemId==R.id.addCarOption){
                    Toast.makeText(MainActivity2.this, "Add Car Page",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, AddCarActivity.class);
                    startActivity(intent);
                }

                drawerLayout.close();
                return false;
            }
        });



    }


    @Override
    public void onitemclick(int position) {
        Intent intent=new Intent(MainActivity2.this , adminActivity.class);
        intent.putExtra("name", cars.get(position).getName());
        intent.putExtra("reprice", cars.get(position).getPricePerDay());
        intent.putExtra("image", cars.get(position).getImageUrl());
        startActivity(intent);

    }
}