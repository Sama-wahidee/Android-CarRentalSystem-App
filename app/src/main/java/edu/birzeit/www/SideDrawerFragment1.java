package edu.birzeit.www;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

public class SideDrawerFragment1 extends DialogFragment {

    private EditText searchInput;
    private Button searchButton;
    private List<Car> carList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);

        searchInput = view.findViewById(R.id.searchh);
        searchButton = view.findViewById(R.id.searchb);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchInput.getText().toString().trim();
                performSearch(query);
            }
        });

        return view;
    }

    private void performSearch(String query) {
        List<Car> filteredList = new ArrayList<>();
        for (Car car : carList) {
            if (car.getModel().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(car);
            }
        }

        // Pass the filtered list back to MainActivity2
        MainActivity2 activity = (MainActivity2) getActivity();
        if (activity != null) {
            activity.updateCarList(filteredList);
            dismiss(); // Close the search fragment
        }
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }
}
