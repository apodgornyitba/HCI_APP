package ar.edu.itba.hci_app.model;

public class Room {

    private String id;
    private String name;
    private String size;
    private String color;

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Room(String name, String size, String color){
        this(null, name, size, color);
    }

    public Room(String id, String name, String size, String color) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.color = color;
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
