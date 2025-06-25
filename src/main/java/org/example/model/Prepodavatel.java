package org.example.model;

public class Prepodavatel {
    private int id;
    private String fio;
    private String dolzhnost;
    private String uchenayaStepen;
    private int stazh;

    public Prepodavatel(int id, String fio, String dolzhnost, String uchenayaStepen, int stazh) {
        this.id = id;
        this.fio = fio;
        this.dolzhnost = dolzhnost;
        this.uchenayaStepen = uchenayaStepen;
        this.stazh = stazh;
    }

    public int getId() { return id; }
    public String getFio() { return fio; }
    public String getDolzhnost() { return dolzhnost; }
    public String getUchenayaStepen() { return uchenayaStepen; }
    public int getStazh() { return stazh; }

    @Override
    public String toString() {
        return fio + ", " + dolzhnost + ", " + uchenayaStepen + ", стаж: " + stazh;
    }
}
