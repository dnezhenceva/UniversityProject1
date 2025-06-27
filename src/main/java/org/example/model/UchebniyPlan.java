package org.example.model;

public class UchebniyPlan {
    private int id;
    private int predmetId;
    private int semestr;
    private boolean obyazatelniy;
    private String predmetName;

    public UchebniyPlan(int id, int predmetId, int semestr, boolean obyazatelniy, String predmetName) {
        this.id = id;
        this.predmetId = predmetId;
        this.semestr = semestr;
        this.obyazatelniy = obyazatelniy;
        this.predmetName = predmetName;
    }

    public int getId() { return id; }
    public int getPredmetId() { return predmetId; }
    public int getSemestr() { return semestr; }
    public boolean isObyazatelniy() { return obyazatelniy; }
    public String getPredmetName() { return predmetName; }

    @Override
    public String toString() {
        return String.format("Предмет: %s | Семестр: %d | %s",
                predmetName, semestr, obyazatelniy ? "Обязательный" : "Факультативный");
    }
}
