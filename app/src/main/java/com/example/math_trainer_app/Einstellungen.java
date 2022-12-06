package com.example.math_trainer_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;

public class Einstellungen extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_einstellungen, null);
        builder.setView(view);

        ImageButton exit= view.findViewById(R.id.exit);
        Button deleteHighscore = view.findViewById(R.id.del_higtscore);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss(); //beendet den Dialog und kehrt zurück ins Menue
            }
        });

        deleteHighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //oeffnet Popup_Delete_Highscore()
                Popup_Delete_Highscore del = new Popup_Delete_Highscore();
                del.show(fragmentManager, "deleteHighscore");
            }
        });

        return builder.create();
    }
}
