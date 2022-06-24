package ar.edu.itba.hci_app.model;

public class Device {

    private String id;
    private String name;
    private Boolean favorite;
    private String image;
    private String room;
    private String status;
    private String typeId;

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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getStatus(){return status;}

    public void setStatus(String status){this.status = status;}

    public Device(String name, String typeId, Boolean favorite, String image, String room, String status) {
        this(null, name, typeId,favorite, image, room, status);
    }

    public Device(String id, String name, String typeId, Boolean favorite, String image, String room, String status) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.favorite = favorite;
        this.image = image;
        this.room = room;
        this.typeId = typeId;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null)
            return false;

        if (getClass() != o.getClass())
            return false;

        return this.getId().equals(((Device) o).getId());
    }
}
