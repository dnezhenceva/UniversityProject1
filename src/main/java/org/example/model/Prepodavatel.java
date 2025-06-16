package org.example.model;

public class Prepodavatel {
    private int id;
    private String fio;
    private String dolzhnost;
    private String uchenayaStepen;
    private int stazh;
    private int kafedraId;

    public Prepodavatel(int id, String fio, String dolzhnost, String uchenayaStepen, int stazh, int kafedraId) {
        this.id = id;
        this.fio = fio;
        this.dolzhnost = dolzhnost;
        this.uchenayaStepen = uchenayaStepen;
        this.stazh = stazh;
        this.kafedraId = kafedraId;
    }

    @Override
    public String toString() {
        return "Prepodavatel{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", dolzhnost='" + dolzhnost + '\'' +
                ", uchenayaStepen='" + uchenayaStepen + '\'' +
                ", stazh=" + stazh +
                ", kafedraId=" + kafedraId +
                '}';
    }
}
