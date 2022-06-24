package ar.edu.itba.hci_app.data.remote.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteDeviceType {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("powerUsage")
    @Expose
    private String powerUsage;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPowerUsage() {
        return powerUsage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPowerUsage(String powerUsage) {
        this.powerUsage = powerUsage;
    }
}
