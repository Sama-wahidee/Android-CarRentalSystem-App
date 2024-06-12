package edu.birzeit.www;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {


    private List<Orders> orders;
    private Context context;



    public OrderAdapter(Context context, List<Orders> orders) {
        this.orders = orders;

        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_card, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Orders order = orders.get(position);
        CardView cardView = holder.cardView;

        ImageView imageView = cardView.findViewById(R.id.car_imgorder);
        Glide.with(context).load(order.getImageUrl()).into(imageView);

        TextView orderNumTxt = cardView.findViewById(R.id.ordernum);
        orderNumTxt.setText("Order ID: " + order.getOrderId());

        TextView emailTxt = cardView.findViewById(R.id.email);
        emailTxt.setText("• Email: " + order.getEmail());

        TextView usernameTxt = cardView.findViewById(R.id.username);
        usernameTxt.setText("• User Name: " + order.getUserName());

        TextView vinNumberTxt = cardView.findViewById(R.id.vin_number);
        vinNumberTxt.setText("• VIN Number: " + order.getVinNumber());

        TextView rentCostTxt = cardView.findViewById(R.id.rent_cost);
        rentCostTxt.setText("• Rent Cost: " + order.getRentCost());

        TextView phoneNumberTxt = cardView.findViewById(R.id.phone_number);
        phoneNumberTxt.setText("• Phone Number: " + order.getPhone());

        TextView startDateTxt = cardView.findViewById(R.id.start_date);
        startDateTxt.setText("Start Date: " + order.getStartDate());

        TextView endDateTxt = cardView.findViewById(R.id.end_date);
        endDateTxt.setText("End Date: " + order.getEndDate());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void updateOrders(List<Orders> newOrderList) {
        this.orders = newOrderList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
