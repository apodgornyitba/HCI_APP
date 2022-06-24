package ar.edu.itba.hci_app.data.remote.device.speaker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteSpeakerState {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("volume")
    @Expose
    private int volume;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("song")
    @Expose
    private RemoteSpeakerSong song;

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

    public RemoteSpeakerSong getSong() {
        return song;
    }

    public void setSong(RemoteSpeakerSong song) {
        this.song = song;
    }
}