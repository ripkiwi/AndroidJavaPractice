package be.ucll.java.mobile.examen_practice.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import be.ucll.java.mobile.examen_practice.model.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    //Het Data Access Object. Een interface dat de lijst aangeeft van ondersteunde CRUDS operaties.
    public abstract NotesDao getNotesDao();

    //De gekoppelde SQLite Database instantie specifiek voor deze App met een unieke naam.
    private static LocalDatabase db;

    public static LocalDatabase getInstance(Context context) {
        if (null == db) {
            db = Room.databaseBuilder(context, LocalDatabase.class, Constants.DB_NAME)
                    .allowMainThreadQueries() // DONT do this in real applications !!!
                    .build();
            /*
              TEACHERS NOTE:
              Hierboven wordt gebruik gemaakt van .allowMainThreadQueries()
              Dat is BAD practise. Alle operaties op databanken kunnen lang duren
              en horen dus in een aparte Thread uitgevoerd te worden. Vooral de searches.

              Maar voor de eenvoud van de kennismaking met Room doen we het hier toch even zo.
             */
        }
        return db;
    }

    private static RoomDatabase.Callback localDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            //Hier kan je statements toevoegen die automatisch uitgevoerd moeten
            // worden telkens als de databank wordt geopend.
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            //Hier kan je statements toevoegen die EENMALIG uitgevoerd moeten worden
            // de allereerste keer nadat (een versie van) de databank werd aangemaakt.
        }
    };

}