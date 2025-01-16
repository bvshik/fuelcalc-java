package com.example.fuelcalc.ui.stats;

import static com.example.fuelcalc.ui.vehicles.VehiclesFragment.selectedCar;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fuelcalc.DatabaseHelper;
import com.example.fuelcalc.R;
import com.example.fuelcalc.databinding.FragmentHomeBinding;

public class StatsFragment extends Fragment {
    private TextView totalRefuelView, totalMoneyView, totalKmView, averageConsumptionView, carSelectedView;
    private DatabaseHelper db;
    private static final double LITER_PRICE = 6.05;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StatsViewModel statsViewModel =
                new ViewModelProvider(this).get(StatsViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        totalRefuelView = root.findViewById(R.id.total_refuel);
        totalMoneyView = root.findViewById(R.id.total_money);
        totalKmView = root.findViewById(R.id.total_km);
        averageConsumptionView = root.findViewById(R.id.average_consumption);
        carSelectedView = root.findViewById(R.id.carSelected);

        carSelectedView.setText("Pojazd #" + selectedCar);

        db = new DatabaseHelper(requireContext());

        calculateAndDisplayStats();

        return root;
    }

    private void calculateAndDisplayStats() {
        Cursor cursor = db.readAllData();


        int totalRefuel = 0;
        int totalKm = 0;

        while (cursor.moveToNext()) {
            int carId = cursor.getInt(cursor.getColumnIndexOrThrow("car_id"));

            if (carId == selectedCar) {
                totalRefuel += cursor.getInt(cursor.getColumnIndexOrThrow("refuel_value"));
                totalKm += cursor.getInt(cursor.getColumnIndexOrThrow("trip_length"));
            }
        }

        double totalMoney = totalRefuel * LITER_PRICE;
        double averageConsumption = totalKm > 0 ? (totalRefuel * 100.0) / totalKm : 0;

        // Display statistics with formatted strings
        totalRefuelView.setText(String.format("%d litrów", totalRefuel));
        totalMoneyView.setText(String.format("%.2f zł", totalMoney));
        totalKmView.setText(String.format("%d km", totalKm));
        averageConsumptionView.setText(String.format("%.2f L/100 km", averageConsumption));

        cursor.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(db != null){
            db.close();
        }
        binding = null;
    }
}