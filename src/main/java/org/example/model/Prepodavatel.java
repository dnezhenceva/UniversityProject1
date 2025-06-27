package org.example.model;

public class Prepodavatel {
    private int id;
    private String familiya;
    private String imya;
    private String otchestvo;
    private String dolzhnost;
    private String uchenayaStepen;
    private int stazh;

    public Prepodavatel(int id, String familiya, String imya, String otchestvo, String dolzhnost, String uchenayaStepen, int stazh) {
        this.id = id;
        this.familiya = familiya;
        this.imya = imya;
        this.otchestvo = otchestvo;
        this.dolzhnost = dolzhnost;
        this.uchenayaStepen = uchenayaStepen;
        this.stazh = stazh;
    }

    public int getId() { return id; }
    public String getFamiliya() { return familiya; }
    public String getImya() { return imya; }
    public String getOtchestvo() { return otchestvo; }
//    public String getDolzhnost() { return dolzhnost; }
//    public String getUchenayaStepen() { return uchenayaStepen; }
//    public int getStazh() { return stazh; }

    public String getFio() {
        return familiya + " " + imya + " " + otchestvo;
    }

    @Override
    public String toString() {
        return getFio() + ", " + dolzhnost + ", " + uchenayaStepen + ", стаж: " + stazh;
    }
}
