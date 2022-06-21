package ar.edu.itba.hci_app.data.remote.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteDeviceMeta {

    @SerializedName("favorite")
    @Expose
    private String favorite;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("room")
    @Expose
    private String room;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getFavorite() {
        return favorite.equals("true");
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite.toString().toLowerCase();
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
