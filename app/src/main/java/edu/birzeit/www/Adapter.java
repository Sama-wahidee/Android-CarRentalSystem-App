package edu.birzeit.www;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final recyclerinterface recyclerinterface;
    private List<Car> cars;

    public Adapter(List<Car> cars , recyclerinterface recyclerinterface) {
        this.cars = cars;
        this.recyclerinterface= recyclerinterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.costum_view, parent, false);
        return new ViewHolder(view, recyclerinterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = cars.get(position);
        holder.carName.setText(car.getName());
        holder.carImage.setImageResource(car.getImageUrl());
        holder.seatCount.setText(String.valueOf(car.getSeatCount()));
        holder.speed.setText(String.valueOf(car.getSpeed()));
        holder.carModel.setText(String.valueOf(car.getModel()));
        holder.pricePerDay.setText(String.valueOf(car.getPricePerDay())+"$/Day");
        // Set other data to views
    }


    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView pricePerDay;
        TextView speed;
        TextView carName;
        ImageView carImage;
        TextView seatCount;
        TextView carModel;

        public ViewHolder(@NonNull View itemView , recyclerinterface recyclerinterface) {
            super(itemView);
            carName = itemView.findViewById(R.id.car_name);
            carImage = itemView.findViewById(R.id.car_image);
            seatCount = itemView.findViewById(R.id.seat_count);
            speed = itemView.findViewById(R.id.speed);
            pricePerDay = itemView.findViewById(R.id.price_per_day);
            carModel= itemView.findViewById(R.id.car_model);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerinterface != null){
                        int pos=getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerinterface.onitemclick(pos);
                        }

                    }
                }
            });

            // Initialize other views
        }

        @Override
        public void onClick(View v) {

        }
    }
}
