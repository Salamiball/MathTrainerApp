package com.example.math_trainer_app;


public class Level{

    private int zahl1;              //zahl1 und zahl2 bilden das intervall in dem eine random Zahl zum Rechnen fuer die Aufgabe erstellt wird
    private int zahl2;              //z.B. fuer zahl1=1, zahl2=10 wird in Code_game dann zwei Zahlen in dem Intervall 1-10 erstellt welche dann als Aufgabe dienen.
    private int abweichen1;         //abweichen1 und abweichen2 bilden das intervall in dem Random Zahlen für die zusaezlichen Antwortmoeglichkeiten gebildet werden.
    private int abweichen2;         //z.B. abweichen1 =-3 und abweichen2 = 3 dann sind die nicht Ergebniss antworten in dem Radius -3 bis 3 vom ergebniss entfernt
    private String operator;       //ist der Opperator mit dem gerechnet wird

    public int getZahl1(){
        return zahl1;
    }
    public int getZahl2(){
        return zahl2;
    }
    public int getAbweichen1(){
        return abweichen1;
    }
    public int getAbweichen2(){
        return abweichen2;
    }
    public String getOperator(){
        return operator;
    }

    public void all_levels(String which_level){ //hier koennen die einzelnen Level beliebig von Hand angepasst werden
/////////////////Addition///////////////////////////////////////////////////
        if (which_level.equals("add1")) {
            zahl1 = 1; zahl2=10; abweichen1 =-3; abweichen2 = 3; operator = "+";
        }
        if (which_level.equals("add2")) {
            zahl1 = 25; zahl2=50; abweichen1 =-5; abweichen2 = 5; operator = "+";
        }
        if (which_level.equals("add3")) {
            zahl1 = 75; zahl2=150; abweichen1 =-10; abweichen2 = 10; operator = "+";
        }
        if (which_level.equals("add4")) {
            zahl1 = 200; zahl2=500; abweichen1 =-25; abweichen2 = 25; operator = "+";
        }
        if (which_level.equals("add5")) {
            zahl1 = 1000; zahl2=7000; abweichen1 =-100; abweichen2 = 100; operator = "+";
        }

/////////////////Subtraktion///////////////////////////////////////////////////
        if (which_level.equals("sub1")) {
            zahl1 = 1; zahl2=10; abweichen1 =-3; abweichen2 = 3; operator = "-";
        }
        if (which_level.equals("sub2")) {
            zahl1 = 20; zahl2=40; abweichen1 =-5; abweichen2 = 5; operator = "-";
        }
        if (which_level.equals("sub3")) {
            zahl1 = 60; zahl2=130; abweichen1 =-10; abweichen2 = 10; operator = "-";
        }
        if (which_level.equals("sub4")) {
            zahl1 = 150; zahl2=350; abweichen1 =-17; abweichen2 = 16; operator = "-";
        }
        if (which_level.equals("sub5")) {
            zahl1 = 500; zahl2=1000; abweichen1 =-60; abweichen2 = 50; operator = "-";
        }

/////////////////Multiplikation///////////////////////////////////////////////////
        if (which_level.equals("mul1")) {
            zahl1 = 1; zahl2=10; abweichen1 =-4; abweichen2 = 4; operator = "*";
        }
        if (which_level.equals("mul2")) {
            zahl1 = 5; zahl2=20; abweichen1 =-7; abweichen2 = 7; operator = "*";
        }
        if (which_level.equals("mul3")) {
            zahl1 = 15; zahl2=50; abweichen1 =-20; abweichen2 = 20; operator = "*";
        }
        if (which_level.equals("mul4")) {
            zahl1 = 50; zahl2=100; abweichen1 =-50; abweichen2 = 50; operator = "*";
        }
        if (which_level.equals("mul5")) {
            zahl1 = 100; zahl2=500; abweichen1 =-100; abweichen2 = 100; operator = "*";
        }

/////////////////Division///////////////////////////////////////////////////
        if (which_level.equals("div1")) {
            zahl1 = 1; zahl2=20; abweichen1 =-1; abweichen2 = 3; operator = "/";
        }
        if (which_level.equals("div2")) {
            zahl1 = 2; zahl2=60; abweichen1 =-2; abweichen2 = 5; operator = "/";
        }
        if (which_level.equals("div3")) {
            zahl1 = 3; zahl2=150; abweichen1 =-3; abweichen2 = 5; operator = "/";
        }
        if (which_level.equals("div4")) {
            zahl1 = 4; zahl2=500; abweichen1 =-3; abweichen2 = 6; operator = "/";
        }
        if (which_level.equals("div5")) {
            zahl1 = 5; zahl2=1000; abweichen1 =-5; abweichen2 = 10; operator = "/";
        }

    }
    public void all_levels(String which_level, Integer edZahl1, Integer edZahl2, Integer edAwb1, Integer edAwb2, String edOpperator){ //edZahl1 = EditorLevelZahl1
        if (which_level.equals("editorLevel")) {
            zahl1 = edZahl1; zahl2 = edZahl2; abweichen1 = edAwb1; abweichen2 = edAwb2; operator = edOpperator;
        }

    }



}