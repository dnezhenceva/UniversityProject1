package org.example.model;

public class Gruppa {
    private int id;
    private String name;

    public Gruppa(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Группа: " + name + " (id: " + id + ")";
    }
}
