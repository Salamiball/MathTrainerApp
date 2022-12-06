package com.example.math_trainer_app;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Code_Game{


    private static Float erg;             //Ergebniss   //'Float' anstelle von 'float' wird bewusst genutzt um Werte auf 'null' setzen zu koennen oder nicht definierte floats vergleichen zu koennen.
    private Integer zahl1, zahl2;        //RechenZahl 1, RechenZahl 2
    private float abw1, abw2, abw3;     //Abweichung 1 (steht fuer die alternativ Anworten neben der Loesung da)
    private float abw4;                //ist das bearbeitete Ergebniss siehte: "antwort_moeglichkeiten()"
    private int score;                //wird genuzt um den aktuellen score zu speichern
    private boolean change;          //notwendige Variable um bei Falscher Antwort den Bildschrim zu Freezen
    private boolean readFile;       //notwendig um Anzahl derzum Dateizugriffe zu verringern
    private String opp;            //extra String zum Speichern des Operators (+,-,*,/) um ihn dann als Rückgabewert zur verfügung zu stellen
    private HashMap<String, Integer> safeContent;

    public float get_zahl1() {
        return zahl1;
    }
    public float get_zahl2() {
        return zahl2;
    }
    public String get_operator() {
        return opp;
    }

    public Float get_erg() {
        return erg;
    }
    public float get_abw1() {
        return abw1;
    }
    public float get_abw2() {
        return abw2;
    }
    public float get_abw3() {
        return abw3;
    }
    public float get_abw4() {
        return abw4;
    }

    public Integer get_score() {
        return score;
    }

    public Boolean get_change() {
        return change;
    }
    public void set_change_true() {
        change = true;
    }


    public Code_Game(){
        score = 0;
        change = true;
        readFile  = true;
    }

    public void execute(Integer num1, Integer num2, Integer abweichen1, Integer abweichen2, String operator){ //wird in Display_Game mit entsprechenden Parameter aufgerufen
        aufgabe(num1, num2, operator);
        antwort_moeglichkeiten(abweichen1, abweichen2);
    }

    public void aufgabe(Integer num1, Integer num2, String operator) {
        Random r = new Random();
        opp = operator;

        zahl1 = r.nextInt((num2 - num1) + 1) + num1;
        zahl2 = r.nextInt((num2 - num1) + 1) + num1;
        //Block zum Abfragen wie gerechnet werden soll
        switch (operator) {
            case "+":
                erg = (float) zahl1 + zahl2;
                break;
            case "-":
                erg = (float) zahl1 - zahl2;
                break;
            case "*":
                erg = (float) zahl1 * zahl2;
                break;
            case "/":
                boolean b = true;
                boolean booleanNachkommastelle2 = true;
                int nachkommastelle1 = 0;
                int nachkommastelle2 = 0;
                float ergtemp;

                while (b) {
                    booleanNachkommastelle2 = true;
                    zahl1 = r.nextInt((num2 - num1) + 1) + num1; //erstellt eine random Zahl im intervall num1 bis num2
                    zahl2 = r.nextInt((num2 - num1) + 1) + num1; //erstellt eine zweite random Zahl im intervall num1 bis num2
                    ergtemp = (zahl1.floatValue() / zahl2.floatValue());  //temporeares Ergebniss
                    ergtemp = (float) (Math.round(ergtemp * Math.pow(10, 2)) / Math.pow(10, 2)); // rundet das temporeare Ergebniss auf 2 Nachkommastellen

                    String[] erg2array = Float.toString(ergtemp).split("\\."); //splittet das temporeare Ergebniss am komma um die Nachkommastelle(n) zu bekommen
                    int nachkommaWert = Integer.parseInt(erg2array[1]);        //speichert alle Stellen nach dem Komma in Varibale
                    nachkommastelle1 = Integer.parseInt(erg2array[1].substring(0, 1)); //speichert die erste Nachkommastelle
                    try {
                        nachkommastelle2 = Integer.parseInt(erg2array[1].substring(1, 2)); //versucht die zweite Nachkommastelle zu speichern
                    } catch (IndexOutOfBoundsException e) { //exception wird ausgeloest wenn keine zweite Nachkommastelle exestiert z.B. bei 10.5
                        booleanNachkommastelle2 = false;
                    }

                    if (((zahl1 % zahl2) == 0 || nachkommaWert == 25 || nachkommaWert == 5 || nachkommaWert == 75) && (nachkommastelle1 != 0 || !booleanNachkommastelle2)) {
                        erg = ergtemp;
                        b = false;
                    }
                }
                break;
            default:
                System.out.println("Error ungueltiges Zeichen");
                break;
        }
    }





    public void antwort_moeglichkeiten(Integer abweichen1, Integer abweichen2) {
        Random r2 = new Random();
        float bonus = 0;
        if(get_operator().equals("/")){
            int rand = r2.nextInt(4);
            if(rand == 0){
                bonus = 0.25f;
            }else if(rand == 1){
                bonus = 0.5f;
            }else if(rand == 2){
                bonus = 0.75f;
            }       //fuer den fall das rand = 3 ist soll der abweichen Wert bleiben wie er ist (Null)
        }
        do {        //do while Schleife sorgt dafür, dass keine der Abweichungen 0, das Ergebniss ist oder doppelt vorkommt
            abw1 = (r2.nextInt(abweichen2 - abweichen1) + abweichen1) + erg;            //bonus wird bewust weggelassen (erklearung in Beleg) //erstellt 3 random Zahlen zwischen abweichen1 und abweichen2 + dem Ergebnis
            abw2 = (r2.nextInt(abweichen2 - abweichen1) + abweichen1) + erg+ bonus;     //wird also eine Zahl zwischen -3 und 4 erstellt z.B. 2 und das Ergebnis ist 5 kommt als-
            abw3 = (r2.nextInt(abweichen2 - abweichen1) + abweichen1) + erg+ bonus;     // moegliche antwortmogelichekeit eine 7 raus

       } while (abw1 == abw2 || abw2==abw3 || abw1==abw3                //solange ein abweichen den Wert eines anderen abweichens besitzt
                || abw1==erg || abw2==erg || abw3==erg);                //solange ein abweichen das Ergebnis ist

        int r = r2.nextInt(4); //eine Random Zahl 0,1,2 oder 3
        float temp; //Temporerer float zum zwischenspeichern
        abw4 = get_erg(); //abweichen 4 ist das Ergebniss

        //if verzweigung dient dazu das Ergbnis zufuellig auf einen der 4 Antowortmoeglichkeiten buttons zu setzen, damit die Loesung z.B. nicht immer auf Button 4 ist.
        if (r == 0) {
            temp = abw1;
            abw1 = abw4;    //vertauscht abweichen1 mit abweichen4;
            abw4 = temp;
        }
        if (r == 1) {
            temp = abw2;
            abw2 = abw4;
            abw4 = temp;
        }
        if (r == 2) {
            temp = abw3;
            abw3 = abw4;
            abw4 = temp;
        } //if r == 3: nichts passiert das Ergebniss bleibt auf seiner Position

    }

    public String check_answer(Float answer) { //Ueberprueft, ob die Antwortworten richtig sind //'Float' muss als Klassenobjekt definiert werden wegen 'null' vergleich
        if (get_erg().equals(answer)) { //equals muss verwendet werden, da zwei objete miteinander verglichen werden.
            score += 1;
            return "RICHTIG";
        } else {
            String antwort = "FALSCH";
            score = 0;
            change = false;
            if(answer == null){ //Antwort wird in Display_Game() bei ablauf der Zeit null gesetzt
                antwort = "TIMEOUT";
            }
            if (get_operator().equals("/")) {   //damit bei Ergebnissen die nicht geteilt durch sind kein .0 hinter der Zahl beim anzeigen steht.
                return antwort + "\n DAS RICHTIGE ERGEBNISS IST: " + get_erg();
            }else{
                return antwort +"\n DAS RICHTIGE ERGEBNISS IST: " + get_erg().intValue();
            }
        }
    }

    public String safeHighScoreData(Context context, String whichlevel){

        File path = context.getFilesDir();
        File file = new File(path, "highscoreData.txt");
        if(!file.exists()){
            createDefaultHighscoreFile(file); //falls nicht vorhanden erstellt eine neuen Datei mit benoetigtem Inhalt
        }

        try{
            if(readFile){ //block wird nur bei start aufgerufen oder wenn sich der Hightscore aendert, dient dazu die Dateizurgiffe zu reduzieren
                readFile = false;
                safeContent = new HashMap<String, Integer>();
                BufferedReader buffreader = new BufferedReader(new FileReader(file));
                String zeile;
                String[] parts;

                while ((zeile = buffreader.readLine()) != null) { //gesamter Datei Inhalt wird eingelesen und mit hilfe von Hashmap gespeichert
                    parts = zeile.split(":");           //String wird fuer Einteilung in key und value fuer Hashmap zerlegt
                    safeContent.put(parts[0], Integer.valueOf(parts[1]));
                }
                buffreader.close();
            }

            try{
                if(get_score() > safeContent.get(whichlevel)) { //wenn ein neuer Highscore erreicht wurde {
                    readFile=true;
                    safeContent.put(whichlevel, safeContent.get(whichlevel) + 1); //anhand des ausgewealten Levels wird der Highscore bei einem neuen Top ergebniss erhoeht

                    BufferedWriter buffwriter = new BufferedWriter(new FileWriter(file));
                    Iterator iterator = safeContent.entrySet().iterator();
                    while(iterator.hasNext()){
                        Entry entry = (Entry) iterator.next();
                        buffwriter.write(entry.getKey().toString()+ ":" + entry.getValue()); //hightscore wird mit aktuallisiertem Wert in Datei geschrieben
                        buffwriter.write("\n");
                    }
                    buffwriter.close();
                }
            }catch (NullPointerException e) {
                Log.e("login activity", "whichlevel==null: " + e.toString());
            }


        }catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());

        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return String.valueOf(safeContent.get(whichlevel));

    }
    public void createDefaultHighscoreFile(File file) {
        try{
            BufferedWriter buffcreator = new BufferedWriter(new FileWriter(file));
            String opperation="";
            for(int i = 0; i < 4; i++) {    // 4 durchgage wegen 4 verschiedene rechenoperationen
                if(i == 0) {
                    opperation = "add";
                }if(i == 1) {
                    opperation = "sub";
                }if(i == 2) {
                    opperation = "mul";
                }if(i == 3) {
                    opperation = "div";
                }
                for(int j = 1; j < 6; j++){ // 5 durchgange wegen 5 level pro Rechenoperation
                    buffcreator.write(opperation + j + ":0");
                    buffcreator.write("\n");
                }
            }

            buffcreator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
