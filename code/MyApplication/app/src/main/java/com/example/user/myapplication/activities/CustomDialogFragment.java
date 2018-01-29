package com.example.user.myapplication.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.user.myapplication.R;
/**
 * Created by sefu on 5/27/2017.
 */

public class CustomDialogFragment extends DialogFragment {
    private final String TAG = "AUC_CUSTOM";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Create the custom layout using the LayoutInflater class
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_dialog_layout, null);

        // Build the dialog
        builder.setTitle("Registration")
                .setMessage( "Please Wait as we register you...")
                .setIcon(R.drawable.logo)
                .setView(v);

        return builder.create();
    }

}
