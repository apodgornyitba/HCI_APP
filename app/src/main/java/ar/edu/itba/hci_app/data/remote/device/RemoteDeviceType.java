package ar.edu.itba.hci_app.data.remote.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteDeviceType {

    @SerializedName("typeId")
    @Expose
    private String typeId;

    public String getTypeId() {
        return typeId;
    }
    public void setTypeId(String typeId){
        this.typeId = typeId;
    }
}
