package com.example.math_trainer_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class Display_Game extends AppCompatActivity{
    private Button btn1, btn2, btn3, btn4; // sind die Buttons welche die Antwortmoeglichkeiten anzeigen
    private TextView display_task, display_answer, display_score, display_showTimer, display_hightscore;

    Code_Game code_game;
    Level level;

    private int count; //einfache Variable zum Hochzeahlen
    private String timeleft; // gibt an wie viel Zeit noch uebrig bleibt
    private String chosen_level; //gibt an welches das ausgewealte Level ist
    private boolean stopThread; //stopt den Zeit zeahler Thread


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_game);

        display_task = findViewById(R.id.textView);
        display_answer= findViewById(R.id.textView9);
        display_score = findViewById(R.id.textView3);
        display_hightscore = findViewById(R.id.hightscore);
        display_showTimer = findViewById(R.id.textViewTimer);

        ImageButton back_to_menu = findViewById(R.id.imageButton);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);

        code_game = new Code_Game();
        level = new Level();
        count = 0;
        stopThread = false;
        chosen_level = getIntent().getStringExtra("which_level"); //holt das gewealte Level aus Popup_Level

        back_to_menu.setOnClickListener(new View.OnClickListener() { //bringt einen zurueck ins Menue
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display_Game.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {  //button1
            @Override
            public void onClick(View v) {
                if(count != 0){ //holt sich das Ergebnniss befor game() neu ausgefürt wird um das ganze mit der Eingabe zu vergleichen muss am Anfang auf 0 sein,
                                //da beim ersten start ja noch keine ergebniss exestiert(game() wird ja erst danach aufgerufen).
                    display_answer.setText(code_game.check_answer(code_game.get_abw1())); //zeigt das ergebniss aus der Vorrunde
                }count = 1;
                if(!code_game.get_change()){    //dient dazu bei Falscher Antwort den Bildschirm zu Frezzen damit man das gerechnete nochmal einsehen kann und-
                    freeze_game();          //nicht sofort die naechste Aufgabe kommt

                }else{
                    try {
                        game();                 //fuehrt alles wichtige zur Anzeige und funktion aus
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() { //button2
            @Override
            public void onClick(View v) {
                if(count != 0){
                    display_answer.setText(code_game.check_answer(code_game.get_abw2()));
                }count = 1;

                if(!code_game.get_change()){
                    freeze_game();
                }else{
                    try {
                        game();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() { //button3
            @Override
            public void onClick(View v) {

                if(count != 0){
                    display_answer.setText(code_game.check_answer(code_game.get_abw3()));
                }count = 1;

                if(!code_game.get_change()){
                    freeze_game();
                }else{
                    try {
                        game();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() { //button4
            @Override
            public void onClick(View v) {

                if(count != 0){
                    display_answer.setText(code_game.check_answer(code_game.get_abw4()));
                }count = 1;

                if(!code_game.get_change()){
                    freeze_game();
                }else{
                    try {
                        game();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public String int_to_String(Integer value){
        return value.toString();
    }

    public void game() throws InterruptedException {  //fuert alles wichtige zur Funktion und Anzeige des Games durch

        level.all_levels(chosen_level);  //weahlt die stats von dem gewuenschte Level aus und definiert dementsprechend die Werte in all_levels

        //all_levels mit meheren Parametern wird nur wenn ein im Editor erstelltes Level gespielt wird aufgerufen. Sicherheitsabfrage in Level.java
        level.all_levels(chosen_level, getIntent().getIntExtra("editorZahl1", 0), getIntent().getIntExtra("editorZahl2", 0),
                getIntent().getIntExtra("editorAwb1", 0), getIntent().getIntExtra("editorAwb2", 0), getIntent().getStringExtra("editorOpperator"));


        code_game.execute(level.getZahl1(), level.getZahl2(), level.getAbweichen1(), level.getAbweichen2(), level.getOperator()); //uebergibt die zu dem Level gehoerenden werte

        display_answer.setText(" ");

        String show_display_string = (int) code_game.get_zahl1() + " " + code_game.get_operator() + " " + (int) code_game.get_zahl2();
        display_task.setText(show_display_string); //Anzeige der Rechenaufgabe



        if(code_game.get_operator().equals("/")){
            btn1.setText(String.valueOf(code_game.get_abw1()));
            btn2.setText(String.valueOf(code_game.get_abw2()));
            btn3.setText(String.valueOf(code_game.get_abw3()));
            btn4.setText(String.valueOf(code_game.get_abw4()));
        }else{              //damit bei Ergebnissen die nicht geteilt durch sind kein .0 hinter der Zahl beim anzeigen steht.
            btn1.setText(int_to_String((int) code_game.get_abw1()));
            btn2.setText(int_to_String((int) code_game.get_abw2()));
            btn3.setText(int_to_String((int) code_game.get_abw3()));
            btn4.setText(int_to_String((int) code_game.get_abw4()));
        }

        String show_score_string = "SCORE: "+ code_game.get_score().toString();
        display_score.setText(show_score_string);



        if(getIntent().getExtras().getBoolean("timerActiveState")){//ueberprueft ob der timer button gedrueckt wurde

            stop();                                     //Stopt den bestehenden Thread bevor ein neuer gestartet wird, damit maximal nur einer aktiv ist!
            TimeUnit.MILLISECONDS.sleep(20);    //Notwendig, erklearung im Schriftlichen Beleg unter "Thread ausführen"
            go();                                       //hebt die Abbruchbedinung wieder auf

            Thread thread = new Thread(runnable);
            thread.start();

            Context context = getApplicationContext();
            display_hightscore.setText("HIGHSCORE: " + code_game.safeHighScoreData(context, chosen_level)); //zeigt den Highscore an


        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    public synchronized boolean stop(){
        return stopThread = false;
    }
    public synchronized boolean go() {
        return stopThread = true;
    }

    public void freeze_game(){//wird bei einer Falschen Antwort aufgerufen um das Game anzuhalten damit falsches Ergebniss und richtige Lösung eingesehen werden können
        stop();                 //setzt stopThread auf false und heallt (wenn aktiv) den Timer thread an
        count = 0;
        code_game.set_change_true();    //change wird wieder auf true gesetzt damit funktion nicht bei richtigen Antworten aufgerufen wird
        String start = "START";
        btn1.setText(start);
        btn2.setText(start);
        btn3.setText(start);
        btn4.setText(start);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            for (int i = 10; i >= 0; i--) {
                timeleft = String.valueOf(i);
                if(i == 0){     //wird ausgefuert wenn die Zeit abgelaufen ist
                    timeleft = "0";
                    display_answer.setText(code_game.check_answer(null)); //gibt an check_answer() null zurueck wenn Zeit abgelaufen ist
                    freeze_game();  //game wird angehalten und richtiges Ergebniss wird angezeigt, Buttons werden wieder auf Start gesetzt
                }
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            display_showTimer.setText((timeleft)); //Anzeige des Countdowns
                        }
                    });
                    for(int j = 0; j <200; j++){ //erklearung im Schriftlichen Beleg unter "Thread ausführen"
                        if (!stopThread) {       //Abbruchbedinung
                            break;
                        }
                        Thread.sleep(5);
                    }if(!stopThread){
                        break;
                    }
                } catch (InterruptedException | RuntimeException e) {
                    e.printStackTrace();
                }

            }
        }
    };




}
