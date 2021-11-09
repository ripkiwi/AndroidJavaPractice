package be.ucll.java.mobile.examen_practice.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import be.ucll.java.mobile.examen_practice.model.Note;

// DAO = Data Access Object ofwel interface met lijstje van ondersteunde CRUDS operaties
@Dao
public interface NotesDao {

    /* --- Alle operaties dat men kan uitvoeren op 1 note. --- */

    //CREATE (Crud)
    @Insert
    long insertNote(Note note);

    //READ (cRud)
    @Query("SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.COLUMN_NAME_ID + " = :id LIMIT 1")
    Note getNote(long id);

    //UPDATE (crUd)
    @Update
    void updateNote(Note repos);

    //DELETE (cruD)
    @Delete
    void deleteNote(Note note);

    /* --- Operaties die men kan uitvoeren op een lijst van notes. --- */

    //SEARCH alle notes. Return wordt gesorteerd op aanmaakdatum descending. Laatste note is dus aan boven van de lijst.
    @Query("SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + Constants.COLUMN_NAME_ID + " DESC")
    List<Note> getAllNotes();

    //DELETE een lijst van notes.
    @Delete
    void deleteNotes(Note... note);

    //DELETE alle notes.
    @Query("DELETE FROM " + Constants.TABLE_NAME)
    void deleteAll();

}
