package edu.birzeit.www;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

public class SideDrawerFragment extends DialogFragment {
    private MainActivity2 activity;

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity2) {
            activity = (MainActivity2) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null; // Clean up the reference to avoid memory leaks
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_filter, container, false);
        setOnClickListenerForAllTextViews(view);
        Button applyFilterButton = view.findViewById(R.id.button_apply_filter);
        Button clearFilterButton = view.findViewById(R.id.button_clear_filter);
        RangeSeekBar rangeSeekBar = view.findViewById(R.id.priseseekbar);

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Number min_Value= bar.getSelectedMinValue();
                Number max_Value= bar.getSelectedMaxValue();

                TextView minTextView = getView().findViewById(R.id.min_value_text);
                TextView maxTextView = getView().findViewById(R.id.max_value_text);

                minTextView.setText(String.valueOf(min_Value));
                maxTextView.setText(String.valueOf(max_Value));
            }
        });

        // Set up the click listener for the Apply Filter button
        applyFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyFilter();
            }
        });

        // Set up the click listener for the Clear Filter button
        clearFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilterForm();
            }
        });

        return view;
    }

    private void setOnClickListenerForAllTextViews(View view) {
        // Find TextViews by ID
        TextView colorTextView = view.findViewById(R.id.Color);
        TextView brandTextView = view.findViewById(R.id.Brand);
        TextView transmissionTextView = view.findViewById(R.id.Transmission);
        TextView seatsTextView = view.findViewById(R.id.Seats);
        TextView fuelTextView = view.findViewById(R.id.Fuel);
        TextView priceTextView = view.findViewById(R.id.Price);

        // Set OnClickListener for each TextView
        colorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTextViewClick(R.id.Color);
            }
        });
        brandTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTextViewClick(R.id.Brand);
            }
        });
        transmissionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTextViewClick(R.id.Transmission);
            }
        });
        seatsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTextViewClick(R.id.Seats);
            }
        });
        fuelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTextViewClick(R.id.Fuel);
            }
        });
        priceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTextViewClick(R.id.Price);
            }
        });
    }

    private void handleTextViewClick(int textViewId) {
        int linearLayoutId = getCorrespondingLinearLayoutId(textViewId);
        LinearLayout layout = getView().findViewById(linearLayoutId);
        toggleChoices(layout);
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

//    private void applyFilter() {
//        // Close the filter dialog
//        dismiss();
//
//        // Initialize filter flags
//        boolean filterByColor = false;
//        boolean filterByBrand = false;
//        boolean filterByTransmission = false;
//        boolean filterBySeats = false;
//        boolean filterByFuel = false;
//        boolean filterByPrice = false;
//
//        // Initialize selected filter criteria lists
//        List<String> selectedColors = new ArrayList<>();
//        List<String> selectedBrands = new ArrayList<>();
//        List<String> selectedTransmissions = new ArrayList<>();
//        List<Integer> selectedSeats = new ArrayList<>();
//        List<String> selectedFuelTypes = new ArrayList<>();
//        int minPrice = 0;
//        int maxPrice = Integer.MAX_VALUE;
//
//        // Get references to filter views
//        ScrollView filterLayout = getView().findViewById(R.id.filterLayout);
//
//        // Get brand filter choices
//        LinearLayout layoutBrandChoices = filterLayout.findViewById(R.id.layoutBrandChoices);
//        if (layoutBrandChoices.getVisibility() == View.VISIBLE) {
//            filterByBrand = true;
//            CheckBox[] brandCheckBoxes = {
//                    layoutBrandChoices.findViewById(R.id.checkbox_brand1),
//                    layoutBrandChoices.findViewById(R.id.checkbox_brand2),
//                    layoutBrandChoices.findViewById(R.id.checkbox_brand3),
//                    layoutBrandChoices.findViewById(R.id.checkbox_brand4),
//                    layoutBrandChoices.findViewById(R.id.checkbox_brand5),
//                    layoutBrandChoices.findViewById(R.id.checkbox_brand6),
//                    layoutBrandChoices.findViewById(R.id.checkbox_brand7),
//                    layoutBrandChoices.findViewById(R.id.checkbox_brand8),
//                    layoutBrandChoices.findViewById(R.id.checkbox_brand9),
//                    layoutBrandChoices.findViewById(R.id.checkbox_brand10)
//            };
//            for (CheckBox checkBox : brandCheckBoxes) {
//                if (checkBox.isChecked()) {
//                    selectedBrands.add(checkBox.getText().toString());
//                }
//            }
//        }
//
//        // Get fuel filter choices
//        LinearLayout layoutFuelChoices = filterLayout.findViewById(R.id.layoutFuelChoices);
//        if (layoutFuelChoices.getVisibility() == View.VISIBLE) {
//            filterByFuel = true;
//            CheckBox[] fuelCheckBoxes = {
//                    layoutFuelChoices.findViewById(R.id.checkboxFuel1),
//                    layoutFuelChoices.findViewById(R.id.checkboxFuel2),
//                    layoutFuelChoices.findViewById(R.id.checkboxFuel3),
//                    layoutFuelChoices.findViewById(R.id.checkboxFuel4)
//            };
//            for (CheckBox checkBox : fuelCheckBoxes) {
//                if (checkBox.isChecked()) {
//                    selectedFuelTypes.add(checkBox.getText().toString());
//                }
//            }
//        }
//
//        // Get transmission filter choices
//        LinearLayout layoutTransmissionChoices = filterLayout.findViewById(R.id.layoutTransmissionChoices);
//        if (layoutTransmissionChoices.getVisibility() == View.VISIBLE) {
//            filterByTransmission = true;
//            CheckBox[] transmissionCheckBoxes = {
//                    layoutTransmissionChoices.findViewById(R.id.checkboxTransmission1),
//                    layoutTransmissionChoices.findViewById(R.id.checkboxTransmission2)
//            };
//            for (CheckBox checkBox : transmissionCheckBoxes) {
//                if (checkBox.isChecked()) {
//                    selectedTransmissions.add(checkBox.getText().toString());
//                }
//            }
//        }
//
//        // Get seats filter choices
//        LinearLayout layoutSeatsChoices = filterLayout.findViewById(R.id.layoutSeatsChoices);
//        if (layoutSeatsChoices.getVisibility() == View.VISIBLE) {
//            filterBySeats = true;
//            CheckBox[] seatsCheckBoxes = {
//                    layoutSeatsChoices.findViewById(R.id.checkboxSeat1),
//                    layoutSeatsChoices.findViewById(R.id.checkboxSeat2),
//                    layoutSeatsChoices.findViewById(R.id.checkboxSeat3),
//                    layoutSeatsChoices.findViewById(R.id.checkboxSeat4),
//                    layoutSeatsChoices.findViewById(R.id.checkboxSeat5)
//            };
//            for (CheckBox checkBox : seatsCheckBoxes) {
//                if (checkBox.isChecked()) {
//                    selectedSeats.add(Integer.parseInt(checkBox.getText().toString().split(" ")[0]));
//                }
//            }
//        }
//
//        // Get color filter choices
//        LinearLayout layoutColorChoices = filterLayout.findViewById(R.id.layoutColorChoices);
//        if (layoutColorChoices.getVisibility() == View.VISIBLE) {
//            filterByColor = true;
//            CheckBox[] colorCheckBoxes = {
//                    layoutColorChoices.findViewById(R.id.checkbox_red),
//                    layoutColorChoices.findViewById(R.id.checkbox_green),
//                    layoutColorChoices.findViewById(R.id.checkbox_blue),
//                    layoutColorChoices.findViewById(R.id.checkbox_white),
//                    layoutColorChoices.findViewById(R.id.checkbox_silver),
//                    layoutColorChoices.findViewById(R.id.checkbox_black)
//            };
//            for (CheckBox checkBox : colorCheckBoxes) {
//                if (checkBox.isChecked()) {
//                    selectedColors.add(checkBox.getText().toString().trim());
//                }
//            }
//        }
//
//        // Get price filter choices
//        LinearLayout layoutPriceChoices = filterLayout.findViewById(R.id.layoutPriceChoices);
//        if (layoutPriceChoices.getVisibility() == View.VISIBLE) {
//            filterByPrice = true;
//            RangeSeekBar rangeSeekBar = layoutPriceChoices.findViewById(R.id.priseseekbar);
//            minPrice = rangeSeekBar.getSelectedMinValue().intValue();
//            maxPrice = rangeSeekBar.getSelectedMaxValue().intValue();
//        }
//
//        // Filter cars based on selected criteria
//        List<Car> filteredCars = new ArrayList<>();
//        for (Car car : activity.cars) {
//            String[] parts = car.getModel().split(" ");
//            String brand = parts[0];
//            String model = parts[1];
//            if ((!filterByColor || selectedColors.contains(car.getColor())) &&
//                    (!filterByBrand || selectedBrands.contains(brand)) &&
//                    (!filterByTransmission || selectedTransmissions.contains(car.getTransmission())) &&
//                    (!filterBySeats || selectedSeats.contains(car.getNumberOfSeats())) &&
//                    (!filterByFuel || selectedFuelTypes.contains(car.getFuelType())) &&
//                    (!filterByPrice || (car.getRentPrice() >= minPrice && car.getRentPrice() <= maxPrice))) {
//                // Add the car if it matches all criteria
//                filteredCars.add(car);
//            }
//        }
//
//        // Update RecyclerView in MainActivity2 with filtered cars
//        if (activity != null) {
//            activity.updateCarList(filteredCars);
//        }
//    }

    private void applyFilter() {
        // Close the filter dialog
        dismiss();

        // Initialize filter flags
        boolean filterByColor = false;
        boolean filterByBrand = false;
        boolean filterByTransmission = false;
        boolean filterBySeats = false;
        boolean filterByFuel = false;
        boolean filterByPrice = false;

        // Initialize selected filter criteria lists
        List<String> selectedColors = new ArrayList<>();
        List<String> selectedBrands = new ArrayList<>();
        List<String> selectedTransmissions = new ArrayList<>();
        List<Integer> selectedSeats = new ArrayList<>();
        List<String> selectedFuelTypes = new ArrayList<>();
        int minPrice = 0;
        int maxPrice = Integer.MAX_VALUE;

        // Get references to filter views
        ScrollView filterLayout = getView().findViewById(R.id.filterLayout);

        // Get brand filter choices
        LinearLayout layoutBrandChoices = filterLayout.findViewById(R.id.layoutBrandChoices);
        if (layoutBrandChoices.getVisibility() == View.VISIBLE) {
            filterByBrand = true;
            CheckBox[] brandCheckBoxes = {
                    layoutBrandChoices.findViewById(R.id.checkbox_brand1),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand2),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand3),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand4),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand5),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand6),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand7),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand8),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand9),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand10)
            };
            for (CheckBox checkBox : brandCheckBoxes) {
                if (checkBox.isChecked()) {
                    selectedBrands.add(checkBox.getText().toString());
                }
            }
        }

        // Get fuel filter choices
        LinearLayout layoutFuelChoices = filterLayout.findViewById(R.id.layoutFuelChoices);
        if (layoutFuelChoices.getVisibility() == View.VISIBLE) {
            filterByFuel = true;
            CheckBox[] fuelCheckBoxes = {
                    layoutFuelChoices.findViewById(R.id.checkboxFuel1),
                    layoutFuelChoices.findViewById(R.id.checkboxFuel2),
                    layoutFuelChoices.findViewById(R.id.checkboxFuel3),
                    layoutFuelChoices.findViewById(R.id.checkboxFuel4)
            };
            for (CheckBox checkBox : fuelCheckBoxes) {
                if (checkBox.isChecked()) {
                    selectedFuelTypes.add(checkBox.getText().toString());
                }
            }
        }

        // Get transmission filter choices
        LinearLayout layoutTransmissionChoices = filterLayout.findViewById(R.id.layoutTransmissionChoices);
        if (layoutTransmissionChoices.getVisibility() == View.VISIBLE) {
            filterByTransmission = true;
            CheckBox[] transmissionCheckBoxes = {
                    layoutTransmissionChoices.findViewById(R.id.checkboxTransmission1),
                    layoutTransmissionChoices.findViewById(R.id.checkboxTransmission2)
            };
            for (CheckBox checkBox : transmissionCheckBoxes) {
                if (checkBox.isChecked()) {
                    selectedTransmissions.add(checkBox.getText().toString());
                }
            }
        }

        // Get seats filter choices
        LinearLayout layoutSeatsChoices = filterLayout.findViewById(R.id.layoutSeatsChoices);
        if (layoutSeatsChoices.getVisibility() == View.VISIBLE) {
            filterBySeats = true;
            CheckBox[] seatsCheckBoxes = {
                    layoutSeatsChoices.findViewById(R.id.checkboxSeat1),
                    layoutSeatsChoices.findViewById(R.id.checkboxSeat2),
                    layoutSeatsChoices.findViewById(R.id.checkboxSeat3),
                    layoutSeatsChoices.findViewById(R.id.checkboxSeat4),
                    layoutSeatsChoices.findViewById(R.id.checkboxSeat5)
            };
            for (CheckBox checkBox : seatsCheckBoxes) {
                if (checkBox.isChecked()) {
                    selectedSeats.add(Integer.parseInt(checkBox.getText().toString().split(" ")[0]));
                }
            }
        }

        // Get color filter choices
        LinearLayout layoutColorChoices = filterLayout.findViewById(R.id.layoutColorChoices);
        if (layoutColorChoices.getVisibility() == View.VISIBLE) {
            filterByColor = true;
            CheckBox[] colorCheckBoxes = {
                    layoutColorChoices.findViewById(R.id.checkbox_red),
                    layoutColorChoices.findViewById(R.id.checkbox_green),
                    layoutColorChoices.findViewById(R.id.checkbox_blue),
                    layoutColorChoices.findViewById(R.id.checkbox_white),
                    layoutColorChoices.findViewById(R.id.checkbox_silver),
                    layoutColorChoices.findViewById(R.id.checkbox_black)
            };
            for (CheckBox checkBox : colorCheckBoxes) {
                if (checkBox.isChecked()) {
                    selectedColors.add(checkBox.getText().toString().trim());
                }
            }
        }

        // Get price filter choices
        LinearLayout layoutPriceChoices = filterLayout.findViewById(R.id.layoutPriceChoices);
        if (layoutPriceChoices.getVisibility() == View.VISIBLE) {
            filterByPrice = true;
            RangeSeekBar rangeSeekBar = layoutPriceChoices.findViewById(R.id.priseseekbar);
            minPrice = rangeSeekBar.getSelectedMinValue().intValue();
            maxPrice = rangeSeekBar.getSelectedMaxValue().intValue();
        }

        // Filter cars based on selected criteria
        List<Car> filteredCars = new ArrayList<>();
        for (Car car : activity.cars) {
            String[] parts = car.getModel().split(" ");
            if (parts.length < 2) {
                continue; // Skip cars with invalid model format
            }
            String brand = parts[0];
            String model = parts[1];
            if ((!filterByColor || selectedColors.contains(car.getColor())) &&
                    (!filterByBrand || selectedBrands.contains(brand)) &&
                    (!filterByTransmission || selectedTransmissions.contains(car.getTransmission())) &&
                    (!filterBySeats || selectedSeats.contains(car.getNumberOfSeats())) &&
                    (!filterByFuel || selectedFuelTypes.contains(car.getFuelType())) &&
                    (!filterByPrice || (car.getRentPrice() >= minPrice && car.getRentPrice() <= maxPrice))) {
                // Add the car if it matches all criteria
                filteredCars.add(car);
            }
        }

        // Update RecyclerView in MainActivity2 with filtered cars
        if (activity != null) {
            activity.updateCarList(filteredCars);
        }
    }


    private void clearFilterForm() {
        if (getView() != null) {
            // Clear brand choices
            LinearLayout layoutBrandChoices = getView().findViewById(R.id.layoutBrandChoices);
            CheckBox[] brandCheckBoxes = {
                    layoutBrandChoices.findViewById(R.id.checkbox_brand1),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand2),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand3),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand4),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand5),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand6),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand7),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand8),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand9),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand10),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand11),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand12),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand13),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand14),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand15),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand16),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand17),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand18),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand19),
                    layoutBrandChoices.findViewById(R.id.checkbox_brand20)
            };
            for (CheckBox checkBox : brandCheckBoxes) {
                checkBox.setChecked(false);
            }

            // Clear fuel choices
            LinearLayout layoutFuelChoices = getView().findViewById(R.id.layoutFuelChoices);
            CheckBox[] fuelCheckBoxes = {
                    layoutFuelChoices.findViewById(R.id.checkboxFuel1),
                    layoutFuelChoices.findViewById(R.id.checkboxFuel2),
                    layoutFuelChoices.findViewById(R.id.checkboxFuel3),
                    layoutFuelChoices.findViewById(R.id.checkboxFuel4)
            };
            for (CheckBox checkBox : fuelCheckBoxes) {
                checkBox.setChecked(false);
            }

            // Clear transmission choices
            LinearLayout layoutTransmissionChoices = getView().findViewById(R.id.layoutTransmissionChoices);
            CheckBox[] transmissionCheckBoxes = {
                    layoutTransmissionChoices.findViewById(R.id.checkboxTransmission1),
                    layoutTransmissionChoices.findViewById(R.id.checkboxTransmission2)
            };
            for (CheckBox checkBox : transmissionCheckBoxes) {
                checkBox.setChecked(false);
            }

            // Clear seats choices
            LinearLayout layoutSeatsChoices = getView().findViewById(R.id.layoutSeatsChoices);
            CheckBox[] seatsCheckBoxes = {
                    layoutSeatsChoices.findViewById(R.id.checkboxSeat1),
                    layoutSeatsChoices.findViewById(R.id.checkboxSeat2),
                    layoutSeatsChoices.findViewById(R.id.checkboxSeat3),
                    layoutSeatsChoices.findViewById(R.id.checkboxSeat4),
                    layoutSeatsChoices.findViewById(R.id.checkboxSeat5)
            };
            for (CheckBox checkBox : seatsCheckBoxes) {
                checkBox.setChecked(false);
            }

            // Clear color choices
            LinearLayout layoutColorChoices = getView().findViewById(R.id.layoutColorChoices);
            CheckBox[] colorCheckBoxes = {
                    layoutColorChoices.findViewById(R.id.checkbox_red),
                    layoutColorChoices.findViewById(R.id.checkbox_green),
                    layoutColorChoices.findViewById(R.id.checkbox_blue),
                    layoutColorChoices.findViewById(R.id.checkbox_white),
                    layoutColorChoices.findViewById(R.id.checkbox_silver),
                    layoutColorChoices.findViewById(R.id.checkbox_black)
            };
            for (CheckBox checkBox : colorCheckBoxes) {
                checkBox.setChecked(false);
            }

            // Clear price choices
            LinearLayout layoutPriceChoices = getView().findViewById(R.id.layoutPriceChoices);
            RangeSeekBar rangeSeekBar = layoutPriceChoices.findViewById(R.id.priseseekbar);
            rangeSeekBar.setSelectedMinValue(rangeSeekBar.getAbsoluteMinValue());
            rangeSeekBar.setSelectedMaxValue(rangeSeekBar.getAbsoluteMaxValue());
        }
    }

}