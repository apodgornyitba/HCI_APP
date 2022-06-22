package ar.edu.itba.hci_app.data.remote.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ar.edu.itba.hci_app.data.remote.room.RemoteRoomMeta;

public class RemoteDevice {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("meta")
    @Expose
    private RemoteDeviceMeta meta;
    @SerializedName("state")
    @Expose
    private RemoteDeviceState state;

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

    public RemoteDeviceMeta getMeta() {
        return meta;
    }

    public void setMeta(RemoteDeviceMeta meta) {
        this.meta = meta;
    }

    public RemoteDeviceState getState() {
        return state;
    }

    public void setState(RemoteDeviceState state) {
        this.state = state;
    }
}