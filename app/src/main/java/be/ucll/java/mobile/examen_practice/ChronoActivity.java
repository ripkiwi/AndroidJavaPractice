package be.ucll.java.mobile.examen_practice;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChronoActivity extends AppCompatActivity {

    //Instantievariabelen
    long startTime;
    boolean chronoRunning;
    String chronoText;
    TextView txtChrono;
    Button btnStart;
    Button btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chrono);

        //We matchen hier onze variabelen met de id van de layout zodat we ermee kunnen werken.
        txtChrono = findViewById(R.id.txtChrono);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        //De gebruiker kan de chronometer niet stoppen voor deze gestart is.
        btnStop.setEnabled(false);

        //We voegen meteen een onClickListener toe en zetten de method buiten de OnCreate() voor zichtbaarheid.
        btnStart.setOnClickListener(view -> handleBtnStart());
        btnStop.setOnClickListener(view -> handleBtnStop());
    }

    //Deze methode runt wanneer we op de start knop drukken.
    private void handleBtnStart() {

        //De gebruiker mag de chronometer niet meer starten wanneer gestart,
        // zij kunnen deze alleen nog maar stoppen.
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);

        //We berekenen de starttijd
        startTime = SystemClock.elapsedRealtime();
        chronoRunning = true;

        //De chronometer loopt op een andere thread (multithreading) omdat
        // anders de ui zou vastlopen indien we meerdere acties zouden willen uitvoeren,
        // in dit geval de stopwatch display die we constant willen laten updaten.
        chronoOnThread();
    }

    //Deze methode runt wanneer we op de stop knop drukken.
    private void handleBtnStop() {

        //De gebruiker kan de chronometer niet meer opnieuw stoppen
        // maar wel terug starten.
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);

        //De chrono stopt, wanneer we deze variabele op false zetten zal de thread ook stoppen chronoOnThread()
        chronoRunning = false;
    }

    private void chronoOnThread() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                //Deze code wordt uitgevoerd zolang de gebruiker niet op btnStop drukt.
                while(chronoRunning){
                    try {

                        //We berekenen de duratie door de tijd van nu te vergelijken met de starttijd.
                        long duration = SystemClock.elapsedRealtime() - startTime;

                        //Formattering van duratie naar waardes waar we makkelijker mee kunnen werken.
                        int timeInMilliseconds = ((int) duration) % 1000;
                        int timeInSeconds = (((int) duration) / 1000) % 60;
                        int timeInMinutes = ((int) duration / 1000) / 60;
                        String formattedMilliseconds = String.format("%02d", (timeInMilliseconds / 10));
                        String formattedSeconds = String.format("%02d", timeInSeconds);
                        String formattedMinutes = String.format("%02d", timeInMinutes);

                        //Hier gooien we de voorafgaande waardes allemaal samen in een string om deze makkelijk te gebruiken.
                        chronoText = formattedMinutes + ":" + formattedSeconds + "." + formattedMilliseconds;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                //Dit zal constant worden uitgevoerd op de ui thread om de display constant te updaten.
                                txtChrono.setText(chronoText);
                            }
                        });
                        Thread.sleep(60);
                    }catch(InterruptedException e){

                        //Try & Catch moet rond een Thread.sleep() methode zitten
                        // zodat we de InterruptedException kunnen handlen.
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }
}