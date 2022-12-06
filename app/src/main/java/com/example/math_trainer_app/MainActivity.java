

package com.example.math_trainer_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private boolean timerActiveState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add = findViewById(R.id.add);
        Button sub = findViewById(R.id.sub);
        Button mul = findViewById(R.id.mul);
        Button div = findViewById(R.id.div);

        ToggleButton timer = findViewById(R.id.toggleButton1);
        Button exit = findViewById(R.id.button9);
        Button einstellungen = findViewById(R.id.button7);
        Button editor = findViewById(R.id.button8);

        add.setOnClickListener(new View.OnClickListener() { //add = Addition
            @Override
            public void onClick(View v) { //startet bei Knopfdruck Popup_Level mit dem Argument "add"
                Popup_Level pop =  new Popup_Level(timerActiveState);
                pop.show(getSupportFragmentManager(), "add");

            }
        });

        sub.setOnClickListener(new View.OnClickListener() { //sub = Subtraktion
            @Override
            public void onClick(View v) {
                Popup_Level pop =  new Popup_Level(timerActiveState);
                pop.show(getSupportFragmentManager(), "sub");
            }
        });

        mul.setOnClickListener(new View.OnClickListener() { //mul = Multiplikation
            @Override
            public void onClick(View v) {
                Popup_Level pop =  new Popup_Level(timerActiveState);
                pop.show(getSupportFragmentManager(), "mul");
            }
        });

        div.setOnClickListener(new View.OnClickListener() { //div = Division
            @Override
            public void onClick(View v) {
                Popup_Level pop =  new Popup_Level(timerActiveState);
                pop.show(getSupportFragmentManager(), "div");

            }
        });


        timer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { //gibt den Status weiter ob Timer button gedrueckt wurde
                timerActiveState = isChecked;
            }
        });

        exit.setOnClickListener(new View.OnClickListener() { //beendet die App
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);

            }
        });

        einstellungen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Einstellungen werden geoeffnet
                Einstellungen ein = new Einstellungen();
                ein.show(getSupportFragmentManager(), "einstellungen"); //tag wird nicht verwendet muss aber angegeben werden

            }
        });

        editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Editor wird geoeffnet
                Editor ed = new Editor();
                ed.show(getSupportFragmentManager(), "editor");
            }
        });
    }




}