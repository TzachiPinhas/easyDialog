package com.example.easydialog;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Calendar;

public class DialogManager {
    public interface ConfirmationListener {
        void onConfirmed();

        void onCancelled();
    }

    public interface InputListener {
        void onInput(String input);
    }

    public interface MultiChoiceListener {
        void onItemsSelected(boolean[] selectedItems);
    }

    public interface DateSetListener {
        void onDateSet(int year, int month, int day);
    }

    public interface ProfileSubmitListener {
        void onProfileSubmit(String name, String email, String phone, boolean agreedToTerms);
    }


    public static void showAlert(Context context, String title, String message, int iconResId) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(iconResId)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    public static void showConfirmation(Context context, String title, String message, int iconResId, ConfirmationListener listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(iconResId)
                .setPositiveButton("Yes", (dialog, which) -> listener.onConfirmed())
                .setNegativeButton("No", (dialog, which) -> listener.onCancelled())
                .show();
    }

    public static void showInputDialog(Context context, String title, int iconResId, InputListener listener) {
        EditText input = new EditText(context);
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(input)
                .setIcon(iconResId)
                .setPositiveButton("OK", (dialog, which) -> listener.onInput(input.getText().toString()))
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    public static void showListDialog(Context context, String title, String[] items, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setItems(items, listener)
                .show();
    }

    public static void showMultiChoiceDialog(Context context, String title, String[] items, boolean[] checkedItems, MultiChoiceListener listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedItems[which] = isChecked;
                    }
                })
                .setPositiveButton("OK", (dialog, which) -> listener.onItemsSelected(checkedItems))
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }


    public static void showDatePickerDialog(Context context, DateSetListener listener) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year1, month1, dayOfMonth) -> {
            listener.onDateSet(year1, month1, dayOfMonth);
        }, year, month, day);
        datePickerDialog.show();
    }

    public interface TimeSetListener {
        void onTimeSet(int hourOfDay, int minute);
    }

    public static void showTimePickerDialog(Context context, TimeSetListener listener) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view, hourOfDay, minute1) -> {
            listener.onTimeSet(hourOfDay, minute1);
        }, hour, minute, true);
        timePickerDialog.show();
    }



    public static void showCustomProfileDialog(Context context, ProfileSubmitListener listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View customView = inflater.inflate(R.layout.custom_profile_dialog, null);

        EditText nameInput = customView.findViewById(R.id.nameInput);
        EditText emailInput = customView.findViewById(R.id.emailInput);
        EditText phoneInput = customView.findViewById(R.id.phoneInput);
        CheckBox termsCheckBox = customView.findViewById(R.id.termsCheckBox);
        Button submitButton = customView.findViewById(R.id.submitButton);
        Button cancelButton = customView.findViewById(R.id.cancelButton);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(customView)
                .create();

        submitButton.setOnClickListener(v -> {
            boolean valid = true;

            String name = nameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String phone = phoneInput.getText().toString().trim();
            boolean agreedToTerms = termsCheckBox.isChecked();

            if (name.isEmpty()) {
                nameInput.setError("Name is required");
                valid = false;
            }

            if (email.isEmpty()) {
                emailInput.setError("Email is required");
                valid = false;
            }

            if (phone.isEmpty()) {
                phoneInput.setError("Phone number is required");
                valid = false;
            }

            if (!agreedToTerms) {
                termsCheckBox.setError("You must agree to the terms and conditions");
                valid = false;
            }

            if (valid) {
                listener.onProfileSubmit(name, email, phone, agreedToTerms);
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}