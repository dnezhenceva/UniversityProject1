package org.example.model;

public class Predmet {
    private int id;
    private String name;

    public Predmet(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }


    @Override
    public String toString() {
        return "Предмет: " + name + " (id: " + id + ")";
    }
}
