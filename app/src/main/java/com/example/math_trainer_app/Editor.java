package com.example.math_trainer_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Editor extends DialogFragment {
    private EditText zahlenBereich1, zahlenBereich2, abweichungsBereich1, abweichungsBereich2;
    private RadioGroup radioGroup;
    private RadioButton selectedRadioButton;
    private View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_editor, null);
        builder.setView(view);

        radioGroup= view.findViewById(R.id.radioGroup);



        ImageButton exit = view.findViewById(R.id.imageButton2);
        CheckBox timer = view.findViewById(R.id.checkBox);

        Button spielen = view.findViewById(R.id.button5);
        Button speichern = view.findViewById(R.id.button);

        RadioButton add = view.findViewById(R.id.plus);
        RadioButton sub = view.findViewById(R.id.minus);
        RadioButton mul = view.findViewById(R.id.mal);
        RadioButton div = view.findViewById(R.id.durch);

        zahlenBereich1 = view.findViewById(R.id.editTextNumber);
        zahlenBereich2 = view.findViewById(R.id.editTextNumber2);
        abweichungsBereich1 = view.findViewById(R.id.editTextNumber3);
        abweichungsBereich2 = view.findViewById(R.id.editTextNumber4);

        try {
            setEditorStats(zahlenBereich1, zahlenBereich2, abweichungsBereich1, abweichungsBereich2, add, sub, mul, div, timer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        exit.setOnClickListener(new View.OnClickListener() { //schliesst den Dialog
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        spielen.setOnClickListener(new View.OnClickListener() { //fuert das im Editor erstellte Level aus
            @Override
            public void onClick(View v) {
                String zahl1 = zahlenBereich1.getText().toString();
                String zahl2 = zahlenBereich2.getText().toString();
                String awb1 = abweichungsBereich1.getText().toString();
                String awb2 = abweichungsBereich2.getText().toString();

                int maxNumber = 99999999; //hoeste Zahl die eingegeben werden darf

                if(zahl1.equals("") || zahl2.equals("") || awb1.equals("") || awb2.equals("")) { //kleines Popup falls die Zahlenbereiche nicht angegeben wurden
                    Snackbar snackbar = null;
                    snackbar = Snackbar
                            .make(view, "Zahlenbereiche dürfen nicht leer sein!", snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() { //erstellt einen ok button
                                @Override
                                public void onClick(View view) {
                                    //schliesst die Anzeige bei ok button druck
                                }
                            });
                    snackbar.show();
                }else if((Integer.parseInt(awb2) - Integer.parseInt(awb1)) < 3){ //kleines Popup zur Absicherungs das der Abweiungsbereich groß genug ist
                        Snackbar snackbar = null;
                        snackbar = Snackbar
                                .make(view, "Diferenz zwischen Abweichungsbereich zu klein", snackbar.LENGTH_LONG)
                                .setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //schliesst die Anzeige bei ok button druck
                                    }
                                });
                        snackbar.show();

                }else if((Integer.parseInt(zahl1) > Integer.parseInt(zahl2)) || (Integer.parseInt(awb1) > Integer.parseInt(awb2))){ //kleines Popup falls Zahl1 groeßer als Zahl2 ist
                    Snackbar snackbar = null;
                    snackbar = Snackbar
                            .make(view, "Zahl 1 darf nicht größer als Zahl 2 sein", snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //schliesst die Anzeige bei ok button druck
                                }
                            });
                    snackbar.show();

                }else if((Integer.parseInt(zahl1) > maxNumber)  || (Integer.parseInt(zahl2) > maxNumber)  || (Integer.parseInt(awb1) > maxNumber) || (Integer.parseInt(awb2) > maxNumber)){ //falls Zahl1 groeßer als Zahl2 ist
                    Snackbar snackbar = null;
                    snackbar = Snackbar
                            .make(view, "Zahl ist zu groß!", snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //schliesst die Anzeige bei ok button druck
                                }
                            });
                    snackbar.show();
                }else{
                    //startet das Level
                    selectedRadioButton = view.findViewById(radioGroup.getCheckedRadioButtonId()); //holt sich den angewealten Radiobutton
                    String selectedRbText = selectedRadioButton.getText().toString();

                    Intent intent = new Intent(getActivity(), Display_Game.class);

                    //uebergibt relevante Werte an Display_Game
                    intent.putExtra("which_level", "editorLevel"); //editorLevel ist in Level.java enthalten
                    intent.putExtra("timerActiveState", timer.isChecked());
                    intent.putExtra("editorZahl1", Integer.parseInt(String.valueOf(zahlenBereich1.getText())));
                    intent.putExtra("editorZahl2", Integer.parseInt(String.valueOf(zahlenBereich2.getText())));
                    intent.putExtra("editorAwb1", Integer.parseInt(String.valueOf(abweichungsBereich1.getText())));
                    intent.putExtra("editorAwb2", Integer.parseInt(String.valueOf(abweichungsBereich2.getText())));
                    intent.putExtra("editorOpperator", selectedRbText);

                    startActivity(intent); //Activity wird zu Display_Game geweachselt

                }
            }

        });

        speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Speichert die Einstellungen in Datei

                try {
                    BufferedWriter buffwriter = new BufferedWriter(new FileWriter(getFile()));
                    buffwriter.write("zahlenbereich1:" + zahlenBereich1.getText().toString() + "\n"); //Leerzeichen nach Doppelpunkt muss sein, da sonst bei leeren
                    buffwriter.write("zahlenbereich2:" + zahlenBereich2.getText().toString() + "\n"); //Zahlen oder Abweichungsbereich beim Splitten in setEditorStats
                    buffwriter.write("abweichungsbereich1:" + abweichungsBereich1.getText().toString() + "\n"); //ein out of bounce error ausgeloest wird wenn man auf das
                    buffwriter.write("abweichungsbereich2:" + abweichungsBereich2.getText().toString() + "\n"); //Element nach dem Doppelpunkt zugreifen moechte
                    buffwriter.write("timer:" + timer.isChecked() + "\n");


                    selectedRadioButton = view.findViewById(radioGroup.getCheckedRadioButtonId());
                    String opp = selectedRadioButton.getText().toString(); //Speichert den ausgewealten RadioButton in String
                    buffwriter.write("operator:"+opp + "\n");
                    buffwriter.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

        return builder.create();
    }


    public File getFile(){
        File path = requireContext().getFilesDir();
        File file = new File(path, "EditorLevelStats.txt");
        return file;
    }
    public void defaultEditor(){
        try { //erstellt Datei mit default Werten
            BufferedWriter buffcreator = new BufferedWriter(new FileWriter(getFile()));
            buffcreator.write("zahlenbereich1:\n");
            buffcreator.write("zahlenbereich2:\n");
            buffcreator.write("abweichungsbereich1:\n");
            buffcreator.write("abweichungsbereich2:\n");
            buffcreator.write("operator:+\n");
            buffcreator.write("timer:false\n");
            buffcreator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void setEditorStats(EditText zahlen1, EditText zahlen2, EditText abw1, EditText abw2,
                               RadioButton add, RadioButton sub, RadioButton mul, RadioButton div, CheckBox timer) throws IOException {
        //fuellt die Eingabefelder im Editor mit den Gespeicherten Werten in der Datei
        if(!getFile().exists()){
            defaultEditor();
        }

        BufferedReader buffreader = new BufferedReader(new FileReader(getFile()));
        String zeile;
        String[] parts;

        while ((zeile = buffreader.readLine()) != null) {   //Datei wird Zeile fuer Zeile eingelesen und durch eindeutige Zuweisung das gespeicherte Level wieder voreingestellt
            parts = zeile.split(":");
            try{
                switch (parts[0]) {
                    case "zahlenbereich1":
                        zahlen1.setText(parts[1]);
                        break;
                    case "zahlenbereich2":
                        zahlen2.setText(parts[1]);
                        break;
                    case "abweichungsbereich1":
                        abw1.setText(parts[1]);
                        break;
                    case "abweichungsbereich2":
                        abw2.setText(parts[1]);
                        break;
                    case "operator":
                        switch (parts[1]) {
                            case  "+":
                                add.setChecked(true);
                                break;
                            case  "-":
                                sub.setChecked(true);
                                break;
                            case  "*":
                                mul.setChecked(true);
                                break;
                            case  "/":
                                div.setChecked(true);
                                break;
                        }
                    case "timer":
                        if(parts[1].equals("true")){
                            timer.setChecked(true);
                        }else if(parts[1].equals("false")){
                            timer.setChecked(false);
                        }
                }
            }catch (ArrayIndexOutOfBoundsException ignored){
                //fuer den Fall das beim splitten kein Wert hinter dem Doppelpunkt stehen sollte
            }

        }
        buffreader.close();
    }

}
