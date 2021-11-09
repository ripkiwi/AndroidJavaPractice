package be.ucll.java.mobile.examen_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import be.ucll.java.mobile.examen_practice.database.LocalDatabase;
import be.ucll.java.mobile.examen_practice.database.NotesDao;
import be.ucll.java.mobile.examen_practice.model.Note;

public class StorageActivity extends AppCompatActivity {

    //Instantievariabelen
    TextView txtInput;
    TextView txtOutput;
    Button btnSave;
    Button btnLoad;
    RadioButton rdFile;
    RadioButton rdPrefs;
    RadioButton rdDatabase;
    NotesDao dao;
    List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        //We matchen zo de dao variabele die we hebben aangemaakt
        // met de werkelijke dao beschikbaar in de applicatie.
        dao = LocalDatabase.getInstance(this).getNotesDao();

        //We matchen onze variabelen met de corresponderende layout id's.
        txtInput = findViewById(R.id.txtInput);
        txtOutput = findViewById(R.id.txtOutput);
        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);
        rdFile = findViewById(R.id.rdFile);
        rdPrefs = findViewById(R.id.rdPrefs);
        rdDatabase = findViewById(R.id.rdDatabase);

        //We voegen een onClickListener toe en zetten deze buiten de OnCreate()
        // voor extra zichtbaarheid in de code.
        btnSave.setOnClickListener(view -> handleBtnSave());
        btnLoad.setOnClickListener(view -> handleBtnLoad());

    }

    private void handleBtnSave() {

        //Eerst zien we welke RadioButton gecheckt is.
        if(rdPrefs.isChecked()){

            //Deze code zal de tekst uit txtInput opslaan naar preferences.
            //Context.MODE_PRIVATE voor veiligheid.
            SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            //We steken nu de tekst uit txtInput in de preference
            // met als naam 'PrefContent'.
            editor.putString("PrefContent", txtInput.getText().toString());
            editor.apply();

        }else if(rdFile.isChecked()){

            //Deze code zal de tekst uitschrijven naar een bestand in /data/.
            try {

                //We maken een OutputStreamWriter aan om zo de tekst
                // van txtInput uit te schrijven naar een bestand
                // in dit geval 'file_content.txt', te vinden in de /data/ folder van android.
                //Na het uitschrijven van de tekst sluiten we de OutputStreamWriter opnieuw
                OutputStreamWriter osw = new OutputStreamWriter(this.openFileOutput("file_content.txt", Context.MODE_PRIVATE));
                osw.write(txtInput.getText().toString());
                osw.close();

            }
            catch (IOException e) {

                //We moeten de IOException kunnen opvangen als we een OutputStreamWriter gebruiken.
                e.printStackTrace();

            }
        }else if(rdDatabase.isChecked()){

            //Deze code zal de tekst uitschrijven naar de database in de vorm van een Note.
            // (zie model/Note en alle bestanden binnen /database package)
            String s = txtInput.getText().toString();

            //We trimmen de string die we uit txtInput hebben gehaald,
            // we maken er een note van en steken deze in de database.
            Note n = new Note(s.trim());
            long id = dao.insertNote(n);

        }
    }

    private void handleBtnLoad() {
        if(rdPrefs.isChecked()){

            //Hier zullen de tekst binnen preferences terug uitschrijven naar txtOutput.
            //De 2e string in de prefs.getString() is wat getoond wordt indien er niets was opgeslagen.
            SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
            txtOutput.setText(prefs.getString("PrefContent", "No saved preference found."));

        }else if(rdFile.isChecked()){

            //We lezen de tekst binnen het bestand en schrijven het dan uit.
            try{
                //We proberen een InputStream te openen voor 'file_content.txt'.
                //Indien het werkt (=NotNull) voeren we de rest van de code uit,
                // anders schrijven we uit dat er geen uitgeschreven tekst op bestand is.
                InputStream is = this.openFileInput("file_content.txt");
                if(is != null){

                    //Hier wat ingewikkelde code, documentatie te vinden op internet.
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String readString = "";
                    StringBuilder sb = new StringBuilder();

                    //De StringBuilder zal een waardige String maken van de InputStream output.
                    while((readString = br.readLine()) != null){
                        sb.append(readString).append("\n");
                    }

                    //We sluiten de InputStream en schrijven de tekst uit.
                    is.close();
                    txtOutput.setText(sb.toString());

                }else{

                    //Leeg bestand, wel bestand gevonden maar geen tekst.
                    txtOutput.setText("No text found within file.");

                }
            }catch (FileNotFoundException e){

                //De InputStream vind geen bestand, we geven deze informatie mee aan de gebruiker.
                txtOutput.setText("No file saved.");
                e.printStackTrace();

            }catch (IOException e){

                //Dit kan ook altijd gedaan worden, wat overzichtelijker in het debug-proces.
                //Log.e("Exception: ", e.toString());
                //Maar we zullen de e.printStackTrace() gewoon gebruiken.
                e.printStackTrace();

            }
        }else if(rdDatabase.isChecked()){

            //We halen de tekst uit de database en schrijven het uit.
            //Eerst maken we een lege string aan en halen we alle notes uit de db.
            String result = "";
            notes = dao.getAllNotes();

            //Dit zorgt ervoor dat de records van nieuw naar oud gaat,
            // for-loop kan aangepast worden om het omgekeerde te weergeven.
            for(int i = 0; i < notes.size(); i++) {
                result += notes.get(i).getTitle() + "\n";
            }

            //En we schrijven de output natuurlijk uit naar txtOutput.
            txtOutput.setText(result);

        }
    }
}