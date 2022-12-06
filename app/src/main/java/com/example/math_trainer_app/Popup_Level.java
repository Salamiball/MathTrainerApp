package com.example.math_trainer_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.Objects;


public class Popup_Level extends DialogFragment {
    private Intent intent;
    private boolean timerActiveState;

    public Popup_Level(){
      //standartConstrukor ist für Dialoge(popup) in Androidstudio  mit Construktor notwendig
    }
    public Popup_Level(boolean timerActiveState){
       this.timerActiveState = timerActiveState;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_popup_level, null);
        builder.setView(view);

        Button btn1 = view.findViewById(R.id.buttonPopLevel1);
        Button btn2 = view.findViewById(R.id.buttonPopLevel2);
        Button btn3 = view.findViewById(R.id.buttonPopLevel3);
        Button btn4 = view.findViewById(R.id.buttonPopLevel4);
        Button btn5 = view.findViewById(R.id.buttonPopLevel5);



        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(getActivity(), Display_Game.class);
                intent.putExtra("which_level", getTag() + "1"); //getTag() holten den Wert aus MainActivity raus um zuzuordnen mit welchen Operator gerechnet werden soll
                intent.putExtra("timerActiveState", timerActiveState); //uebergibt die Info ob Timer aktiv sein soll oder nicht
                startActivity(intent); //startet Level1
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(getActivity(), Display_Game.class);
                intent.putExtra("which_level", getTag() + "2");
                intent.putExtra("timerActiveState", timerActiveState);
                startActivity(intent); //startet Level2
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(getActivity(), Display_Game.class);
                intent.putExtra("which_level", getTag() + "3");
                intent.putExtra("timerActiveState", timerActiveState);
                startActivity(intent); //startet Level3
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(getActivity(), Display_Game.class);
                intent.putExtra("which_level", getTag() + "4");
                intent.putExtra("timerActiveState", timerActiveState);
                startActivity(intent); //startet Level4
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(getActivity(), Display_Game.class);
                intent.putExtra("which_level", getTag() + "5");
                intent.putExtra("timerActiveState", timerActiveState);
                startActivity(intent); //startet Level5
            }
        });

        return builder.create();
    }


}
