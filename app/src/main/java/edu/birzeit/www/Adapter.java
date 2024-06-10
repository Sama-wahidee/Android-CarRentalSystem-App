package edu.birzeit.www;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final recyclerinterface recyclerinterface;
    private List<Car> cars;
    private Context context;





    public boolean isAdmin=login.isAdmin;

    public Adapter(   Context context,List<Car> cars , recyclerinterface recyclerinterface) {
        this.cars = cars;
        this.recyclerinterface= recyclerinterface;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.costum_view, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Car car = cars.get(position);
        CardView cardView = holder.cardView;

        ImageView imageView = cardView.findViewById(R.id.car_image);
        Glide.with(context).load(car.getImageUrl()).into(imageView);

        TextView nameTxt = cardView.findViewById(R.id.car_name);
        nameTxt.setText(car.getModel());

        TextView seatTxt = cardView.findViewById(R.id.seat_count);
        seatTxt.setText(String.valueOf(car.getNumberOfSeats()+" PASSENGERS"));

        TextView speedTxt = cardView.findViewById(R.id.speed);
        speedTxt.setText(car.getTopSpeed()+" km/h");

        TextView priceTxt = cardView.findViewById(R.id.price_per_day);
        priceTxt.setText(String.valueOf(car.getRentPrice()+"/DAY")); // Set price per day correctly

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(isAdmin == true){
                 intent = new Intent(context, adminActivity.class);}
                else{
                     intent = new Intent(context, userActivty.class);
                }
                intent.putExtra("model", car.getModel());
                intent.putExtra("imageUrl", car.getImageUrl());
                intent.putExtra("numberOfSeats", car.getNumberOfSeats());
                intent.putExtra("topSpeed", car.getTopSpeed());
                intent.putExtra("rentPrice", car.getRentPrice());
                intent.putExtra("year", car.getYear());
                intent.putExtra("description", car.getDescription());
                intent.putExtra("vin", car.getVinNumber());
                intent.putExtra("color", car.getColor());
                intent.putExtra("fuel_type", car.getFuelType());
                intent.putExtra("transmission", car.getTransmission());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }


}

