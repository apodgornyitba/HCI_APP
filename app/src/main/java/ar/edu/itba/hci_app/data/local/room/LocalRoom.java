package ar.edu.itba.hci_app.data.local.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "Room", indices = {@Index("id")}, primaryKeys = {"id"})
public class LocalRoom {

    @NonNull
    @ColumnInfo(name = "id")
    public String id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "image")
    public String image;

    public LocalRoom(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}