package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    interface EditCityDialogListener {
        void onCityEdited(City updatedCity);
    }

    private EditCityDialogListener listener;

    private City city; // must be non-null for edit


    public EditCityFragment(City city) {
        this.city = city;
    }

    // Proper empty constructor
    public EditCityFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        if (city == null) {

            throw new IllegalStateException("EditCityFragment requires a non-null City");
        }

        // Prefill
        editCityName.setText(city.getName());
        editProvinceName.setText(city.getProvince());

        return new AlertDialog.Builder(requireContext())
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newName = editCityName.getText().toString().trim();
                    String newProvince = editProvinceName.getText().toString().trim();

                    city.setName(newName);
                    city.setProvince(newProvince);
                    listener.onCityEdited(city);

                })
                .create();
    }
}