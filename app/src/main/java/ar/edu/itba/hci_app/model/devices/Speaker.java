package ar.edu.itba.hci_app.model.devices;

import androidx.room.ColumnInfo;

public class Speaker {

    private String id;
    private String name;
    private String typeId;

    private Boolean favorite;
    private String image;
    private String room;

    private String status;
    private int volume;
    private String genre;
    private String title;
    private String artist;
    private String album;
    private String duration;
    private String progress;

    public Speaker(String name, String typeId,
                   Boolean favorite, String image, String room,
                   String status, int volume, String genre,
                   String title, String artist, String album, String duration, String progress
    ) {
        this(null, name, typeId,
                favorite, image, room,
                status, volume, genre, title, artist, album, duration, progress);
    }

    public Speaker(String id, String name, String typeId,
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null)
            return false;

        if (getClass() != o.getClass())
            return false;

        return this.getId().equals(((Speaker) o).getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getDuration() {
        return duration;
    }

    public String getProgress() {
        return progress;
    }
}
