package ar.edu.itba.hci_app.data.remote.device.speaker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ar.edu.itba.hci_app.data.remote.device.RemoteDeviceMeta;
import ar.edu.itba.hci_app.data.remote.device.RemoteDeviceType;

public class RemoteSpeaker {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private RemoteDeviceType type;
    @SerializedName("state")
    @Expose
    private RemoteSpeakerState state;
    @SerializedName("meta")
    @Expose
    private RemoteDeviceMeta meta;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RemoteDeviceType getType() {
        return type;
    }

    public RemoteSpeakerState getState() {
        return state;
    }

    public RemoteDeviceMeta getMeta() {
        return meta;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(RemoteDeviceType type) {
        this.type = type;
    }

    public void setState(RemoteSpeakerState state) {
        this.state = state;
    }

    public void setMeta(RemoteDeviceMeta meta) {
        this.meta = meta;
    }
}