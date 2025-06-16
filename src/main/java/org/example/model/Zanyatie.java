package org.example.model;

public class Zanyatie {
    private int id;
    private int prepodId;
    private int gruppaId;
    private int predmetId;
    private int chasy;
    private String uchebniyGod;

    public Zanyatie(int id, int prepodId, int gruppaId, int predmetId, int chasy, int anInt, String uchebniyGod) {
        this.id = id;
        this.prepodId = prepodId;
        this.gruppaId = gruppaId;
        this.predmetId = predmetId;
        this.chasy = chasy;
        this.uchebniyGod = uchebniyGod;
    }

    @Override
    public String toString() {
        return "Zanyatie{" +
                "id=" + id +
                ", prepodId=" + prepodId +
                ", gruppaId=" + gruppaId +
                ", predmetId=" + predmetId +
                ", chasy=" + chasy +
                ", uchebniyGod='" + uchebniyGod + '\'' +
                '}';
    }
}
