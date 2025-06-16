package org.example.model;

public class Gruppa {
    private int id;
    private String name;

    public Gruppa(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Gruppa{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
