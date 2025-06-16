package org.example.model;

public class Predmet {
    private int id;
    private String name;
    private int kafedraId;

    public Predmet(int id, String name, int kafedraId) {
        this.id = id;
        this.name = name;
        this.kafedraId = kafedraId;
    }

    @Override
    public String toString() {
        return "Predmet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", kafedraId=" + kafedraId +
                '}';
    }
}
