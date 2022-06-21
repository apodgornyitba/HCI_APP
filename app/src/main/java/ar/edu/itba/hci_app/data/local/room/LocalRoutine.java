package ar.edu.itba.hci_app.data.local.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "Routine", indices = {@Index("id")}, primaryKeys = {"id"})
public class LocalRoutine {

    @NonNull
    @ColumnInfo(name = "id")
    public String id;
    @ColumnInfo(name = "name")
    public String name;

    public LocalRoutine(String id, String name) {
        this.id = id;
        this.name = name;
    }
}