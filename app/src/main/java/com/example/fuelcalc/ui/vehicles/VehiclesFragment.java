package com.example.fuelcalc.ui.vehicles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fuelcalc.DatabaseHelper;
import com.example.fuelcalc.R;
import com.example.fuelcalc.databinding.FragmentCarsBinding;

public class VehiclesFragment extends Fragment {

    public static int selectedCar = 1;
    private Button car1, car2, car3;
    private FragmentCarsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        VehisclesViewModel vehisclesViewModel =
                new ViewModelProvider(this).get(VehisclesViewModel.class);

        binding = FragmentCarsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        vehisclesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        car1 = root.findViewById(R.id.Car1);
        car2 = root.findViewById(R.id.Car2);
        car3 = root.findViewById(R.id.Car3);

        car1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCar = 1;
                Toast.makeText(getActivity(), "Wybrano pojazd #1", Toast.LENGTH_SHORT).show();
            }
        });

        car2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCar = 2;
                Toast.makeText(getActivity(), "Wybrano pojazd #2", Toast.LENGTH_SHORT).show();
            }
        });

        car3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCar = 3;
                Toast.makeText(getActivity(), "Wybrano pojazd #3", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}