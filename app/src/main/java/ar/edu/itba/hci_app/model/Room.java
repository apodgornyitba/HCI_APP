package ar.edu.itba.hci_app.model;

public class Room {

    private String id;
    private String name;
    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Room(String name, String image) {
        this(null, name, image);
    }

    public Room(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null)
            return false;

        if (getClass() != o.getClass())
            return false;

        return this.getId().equals(((Room) o).getId());
    }
}
