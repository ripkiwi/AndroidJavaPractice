package be.ucll.java.mobile.examen_practice.database;

public interface Constants {

    //Database, tabel en tabel kolommen.
    String DB_NAME = "notes.db";

    String TABLE_NAME = "note";

    String COLUMN_NAME_ID = "note_id";
    String COLUMN_NAME_TITLE = "title";

    //Fragment naar Fragment navigatie.
    String ID = "id";
    String OPERATION = "operation";

    //Klassieke CRUD operaties.
    String OPERATION_CREATE = "CREATE";
    String OPERATION_READ = "READ";
    String OPERATION_UPDATE = "UPDATE";
    String OPERATION_DELETE = "DELETE";
    String OPERATION_SEARCH = "SEARCH";
}
