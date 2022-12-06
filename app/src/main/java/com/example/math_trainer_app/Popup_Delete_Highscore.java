package com.example.math_trainer_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.util.Objects;

public class Popup_Delete_Highscore extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Möchtest du wirklich alle Highscore Daten Löschen und zurück auf 0 setzten?")
                .setPositiveButton("JA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        File path = requireContext().getFilesDir();
                        File file = new File(path, "highscoreData.txt");
                        Code_Game cd = new Code_Game();
                        cd.createDefaultHighscoreFile(file); //ueberschreibt die highscore Datei in dem die Datei komplett neu erstellt wird, als weare sich noch nicht vorhanden
                    }
                })
                .setNegativeButton("NEIN", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //bei nein wird dialog geschlossen
                    }
                });

        return builder.create();
    }
}
