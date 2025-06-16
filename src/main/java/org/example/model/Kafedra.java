package org.example.model;

public class Kafedra {
    private int id;
    private String name;
    private String infoONagruzke;

    public Kafedra(int id, String name, String infoONagruzke) {
        this.id = id;
        this.name = name;
        this.infoONagruzke = infoONagruzke;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInfoONagruzke() {
        return infoONagruzke;
    }

    @Override
    public String toString() {
        return "Kafedra{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", infoONagruzke='" + infoONagruzke + '\'' +
                '}';
    }
}
