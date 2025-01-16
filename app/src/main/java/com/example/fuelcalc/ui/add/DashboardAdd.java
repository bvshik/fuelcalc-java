package com.example.fuelcalc.ui.add;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fuelcalc.DatabaseHelper;
import com.example.fuelcalc.MainActivity;
import com.example.fuelcalc.R;
import com.example.fuelcalc.databinding.FragmentAddBinding;
import com.example.fuelcalc.ui.vehicles.VehiclesFragment;

public class DashboardAdd extends Fragment {

    private EditText lengthValue, refuelValue;
    private Button button;
    CalendarView calendarView;
    String selectedDate;

    private FragmentAddBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddViewModel addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);

        binding = FragmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        addViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        lengthValue = root.findViewById(R.id.LengthValue);
        refuelValue = root.findViewById(R.id.RefuelValue);

        button = root.findViewById(R.id.button);

        long currentDate = System.currentTimeMillis();
        selectedDate = android.text.format.DateFormat.format("yyyy-MM-dd", currentDate).toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    try {
                        DatabaseHelper myDB = new DatabaseHelper(getActivity());
                        float length = Float.parseFloat(lengthValue.getText().toString().trim());
                        float refuel = Float.parseFloat(refuelValue.getText().toString().trim());

                        myDB.addRefuelData((int)length, (int)refuel, selectedDate, VehiclesFragment.selectedCar);

                        lengthValue.setText("");
                        refuelValue.setText("");

                        Toast.makeText(getActivity(), "Poprawnie dodano dane.", Toast.LENGTH_SHORT).show();
                    } catch (NumberFormatException e) {
                        Toast.makeText(getActivity(), "Niepoprawny format.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Błąd. Spróbuj ponownie.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        calendarView = root.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
            }
        });

        return root;
    }

    private boolean validateInput() {
        if (TextUtils.isEmpty(lengthValue.getText().toString().trim())) {
            lengthValue.setError("Wartość nie może być pusta.");
            return false;
        }

        if (TextUtils.isEmpty(refuelValue.getText().toString().trim())) {
            refuelValue.setError("Wartość nie może być pusta.");
            return false;
        }

        try {
            float length = Float.parseFloat(lengthValue.getText().toString().trim());
            float refuel = Float.parseFloat(refuelValue.getText().toString().trim());

            if (length <= 0) {
                lengthValue.setError("Wartość musi być większa niż 0.");
                return false;
            }

            if (refuel <= 0) {
                refuelValue.setError("Wartość musi być większa niż 0.");
                return false;
            }

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Wprowadź poprawne liczby.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (selectedDate == null) {
            Toast.makeText(getActivity(), "Wybierz poprawną datę.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}