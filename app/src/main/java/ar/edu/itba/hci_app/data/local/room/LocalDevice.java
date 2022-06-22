package ar.edu.itba.hci_app.data.local.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "Device", indices = {@Index("id")}, primaryKeys = {"id"})
public class LocalDevice {

    @NonNull
    @ColumnInfo(name = "id")
    public String id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "favorite")
    public Boolean favorite;
    @ColumnInfo(name = "image")
    public String image;
    @ColumnInfo(name = "room")
    public String room;
    @ColumnInfo(name = "typeId")
    public String typeId;

    public LocalDevice(String id, String name, String typeId, Boolean favorite, String image, String room) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.favorite = favorite;
        this.image = image;
        this.room = room;
        this.typeId = typeId;
    }
}