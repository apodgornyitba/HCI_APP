package ar.edu.itba.hci_app.data.remote.device.speaker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteSpeakerSong {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("album")
    @Expose
    private String album;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("progress")
    @Expose
    private String progress;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}