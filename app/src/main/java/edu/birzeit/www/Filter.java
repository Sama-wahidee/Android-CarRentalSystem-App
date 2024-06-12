package edu.birzeit.www;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class
Filter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter);
        setOnClickListenerForAllTextViews();
    }

    private void setOnClickListenerForAllTextViews() {
        TextView colorTextView = findViewById(R.id.Color); // Change to your TextView ID
        TextView brandTextView = findViewById(R.id.Brand); // Change to your TextView ID
        TextView transmissionTextView = findViewById(R.id.Transmission); // Change to your TextView ID
        TextView seatsTextView = findViewById(R.id.Seats); // Change to your TextView ID
        TextView fuelTextView = findViewById(R.id.Fuel); // Change to your TextView ID
        TextView priceTextView = findViewById(R.id.Price); // Change to your TextView ID

        // Get all TextViews by their IDs
        TextView[] textViewIds = {
                colorTextView,
                brandTextView,
                transmissionTextView,
                seatsTextView,
                fuelTextView,
                priceTextView
        };

        // Iterate over each TextView and set OnClickListener
        for (TextView textView : textViewIds) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the corresponding LinearLayout ID based on the TextView clicked
                    int linearLayoutId = getCorrespondingLinearLayoutId(textView.getId());
                    LinearLayout layout = findViewById(linearLayoutId);
                    toggleChoices(layout); // Toggle the visibility of the corresponding LinearLayout
                }
            });
        }
    }

    private int getCorrespondingLinearLayoutId(int textViewId) {
        if (textViewId == R.id.Color) {
            return R.id.layoutColorChoices;
        } else if (textViewId == R.id.Brand) {
            return R.id.layoutBrandChoices;
        } else if (textViewId == R.id.Transmission) {
            return R.id.layoutTransmissionChoices;
        } else if (textViewId == R.id.Seats) {
            return R.id.layoutSeatsChoices;
        } else if (textViewId == R.id.Fuel) {
            return R.id.layoutFuelChoices;
        } else if (textViewId == R.id.Price) {
            return R.id.layoutPriceChoices;
        }
        throw new IllegalArgumentException("Unknown TextView ID: " + textViewId);
    }



    public void toggleChoices(LinearLayout layout) {
        if (layout.getVisibility() == View.VISIBLE) {
            layout.setVisibility(View.GONE);
        } else {
            layout.setVisibility(View.VISIBLE);
        }
    }
}
