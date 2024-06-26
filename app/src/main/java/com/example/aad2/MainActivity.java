package com.example.aad2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easydialog.DialogManager;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private MaterialButton alert_btn;
    private MaterialButton confirm_btn;
    private MaterialButton input_btn;
    private MaterialButton list_btn;
    private MaterialButton multiChoice_btn;
    private MaterialButton datePicker_btn;
    private MaterialButton timePicker_btn;
    private MaterialButton customProfile_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findview();

        alert_btn.setOnClickListener(v ->
                DialogManager.showAlert(this, "Alert", "This is an alert dialog", com.example.easydialog.R.drawable.ic_alert)
        );

        confirm_btn.setOnClickListener(v ->
                DialogManager.showConfirmation(this, "Confirm", "Are you sure?", com.example.easydialog.R.drawable.ic_confirm, new DialogManager.ConfirmationListener() {
                    @Override
                    public void onConfirmed() {
                        Log.d("pppp", "Confirmed");
                    }

                    @Override
                    public void onCancelled() {
                        Log.d("pppp", "Cancelled");
                    }
                })
        );

        input_btn.setOnClickListener(v ->
                DialogManager.showInputDialog(this, "Input", com.example.easydialog.R.drawable.ic_input, input -> {
                    Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
                })
        );

        String[] items = {"Item 1", "Item 2", "Item 3"};
        list_btn.setOnClickListener(v ->
                DialogManager.showListDialog(this, "Select an Item", items, (dialog, which) -> {
                    String selectedItem = items[which];
                    Log.d("MainActivity", "Selected: " + selectedItem);
                })
        );

        multiChoice_btn.setOnClickListener(v ->
                DialogManager.showMultiChoiceDialog(this, "Select Items", new String[]{"Item 1", "Item 2", "Item 3"}, new boolean[]{false, false, false}, selectedItems -> {
                    for (int i = 0; i < selectedItems.length; i++) {
                        Log.d("MainActivity", "Item " + i + ": " + selectedItems[i]);
                    }
                })
        );

        datePicker_btn.setOnClickListener(v ->
                DialogManager.showDatePickerDialog(this, (year, month, day) -> {
                    Log.d("pppp", "Selected Date: " + day + "/" + (month + 1) + "/" + year);
                })
        );

        timePicker_btn.setOnClickListener(v ->
                DialogManager.showTimePickerDialog(this, (hour, minute) -> {
                    Log.d("pppp", "Selected Time: " + hour + ":" + minute);
                })
        );


        customProfile_btn.setOnClickListener(v ->
                DialogManager.showCustomProfileDialog(this, (name, email, phone, agreedToTerms) -> {
                    Log.d("pppp", "Name: " + name);
                    Log.d("pppp", "Email: " + email);
                    Log.d("pppp", "Phone: " + phone);
                    Log.d("pppp", "Agreed to Terms: " + agreedToTerms);
                })
        );
    }

    private void findview() {
        alert_btn = findViewById(R.id.alert_btn);
        confirm_btn = findViewById(R.id.confirm_btn);
        input_btn = findViewById(R.id.input_btn);
        list_btn = findViewById(R.id.list_btn);
        multiChoice_btn = findViewById(R.id.multiChoice_btn);
        datePicker_btn = findViewById(R.id.datePicker_btn);
        timePicker_btn = findViewById(R.id.timePicker_btn);
        customProfile_btn = findViewById(R.id.customProfile_btn);
    }
}

