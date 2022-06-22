package ar.edu.itba.hci_app.data.local.room.speaker;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "Speaker", indices = {@Index("id")}, primaryKeys = {"id"})
public class LocalSpeaker {

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

    @ColumnInfo(name = "status")
    public String status;
    @ColumnInfo(name = "volume")
    public int volume;
    @ColumnInfo(name = "genre")
    public String genre;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "artist")
    public String artist;
    @ColumnInfo(name = "album")
    public String album;
    @ColumnInfo(name = "duration")
    public String duration;
    @ColumnInfo(name = "progress")
    public String progress;

    public LocalSpeaker(String id, String name, String typeId,
                        Boolean favorite, String image, String room,
                        String status, int volume, String genre,
                        String title, String artist, String album, String duration, String progress
    ) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;

        this.favorite = favorite;
        this.image = image;
        this.room = room;

        this.status = status;
        this.volume = volume;
        this.genre = genre;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.progress = progress;
    }
}