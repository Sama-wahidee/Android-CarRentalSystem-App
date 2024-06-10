package edu.birzeit.www;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<CardModel> cardList;
    private Context context;
    private boolean isClickable = false;

    public CardAdapter(Context context, List<CardModel> cardList) {
        this.context = context;
        this.cardList = cardList;
    }
    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardModel card = cardList.get(position);
        CardView cardView = holder.cardView;
        holder.imageView.setImageResource(card.getImageResId());
        holder.textViewD.setText(card.getTitle());
        holder.textViewV.setText(card.getValue());

      if(isClickable){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("Transmission".equals(card.getTitle())) {
                    showTransmissionDialog(holder, position);
                } else if ("Fuel Type".equals(card.getTitle())) {
                    showFuelTypeDialog(holder, position);
                } else {
                    // For other titles, show a generic dialog with title and input field
                    showGenericInputDialog(holder, position);
                }
            }
        });
    }}

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewD;
        TextView textViewV;
        CardView cardView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewD = itemView.findViewById(R.id.detailsTextView);
            textViewV = itemView.findViewById(R.id.valueTextView);
            cardView = (CardView) itemView;
        }
    }

    private void showTransmissionDialog(CardViewHolder holder, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_tansmission, null);
        Spinner spinner = dialogView.findViewById(R.id.transmissionSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.transmission_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        builder.setView(dialogView)
                .setTitle("Select Transmission")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedTransmission = spinner.getSelectedItem().toString();
                        cardList.get(position).setValue(selectedTransmission);
                        holder.textViewV.setText(selectedTransmission);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showFuelTypeDialog(CardViewHolder holder, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_fuel_type, null);
        Spinner spinner = dialogView.findViewById(R.id.fuelTypeSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.fuel_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        builder.setView(dialogView)
                .setTitle("Select Fuel Type")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedFuelType = spinner.getSelectedItem().toString();
                        cardList.get(position).setValue(selectedFuelType);
                        holder.textViewV.setText(selectedFuelType);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showGenericInputDialog(CardViewHolder holder, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_input, null);
        EditText editText = dialogView.findViewById(R.id.inputEditText);

        builder.setView(dialogView)
                .setTitle(cardList.get(position).getTitle())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newValue = editText.getText().toString();
                        cardList.get(position).setValue(newValue);
                        holder.textViewV.setText(newValue);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}