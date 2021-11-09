package be.ucll.java.mobile.examen_practice.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import be.ucll.java.mobile.examen_practice.database.Constants;

@Entity(tableName = Constants.TABLE_NAME)
public class Note implements Serializable {

    //We laten de primary key autogeneren.
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.COLUMN_NAME_ID)
    private Long id;

    @ColumnInfo(name = Constants.COLUMN_NAME_TITLE)
    private String title;

    //Impliciet zorgen we ervoor dat beide velden ingevuld moeten zijn. (NonNull)
    //Deze constructor wordt gebruikt bij de Create.
    public Note(@NonNull String title) {
        this.title = title;
    }

    //Deze constructor wordt gebruikt bij de Update.
    @Ignore
    public Note(@NonNull Long id, @NonNull String title) {
        this.id = id;
        this.title = title;
    }

    //Getters en setters automatisch gegenereerd.
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
